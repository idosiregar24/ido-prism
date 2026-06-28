package com.example.ido_prism.DigitalDesa

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import android.widget.ArrayAdapter
import com.example.ido_prism.Proyek.DataProyek
import com.example.ido_prism.databinding.ActivityDigitalDesaBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DigitalDesaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDigitalDesaBinding
    private var photoUri: Uri? = null
    private var selectedProyekKode: String? = null

    private val proyekList = listOf(
        DataProyek("PRJ0001", "Pembangunan Air Bersih Bontang", 2023, "Bontang", "Rp 1.25M", "APBD", 0.0, 0.0, ""),
        DataProyek("PRJ0002", "Pengadaan Peralatan Cimahi", 2023, "Cimahi", "Rp 914jt", "Hibah", 0.0, 0.0, ""),
        DataProyek("PRJ0003", "Pembangunan Drainase Yogyakarta", 2024, "Yogyakarta", "Rp 750jt", "APBN", 0.0, 0.0, ""),
        DataProyek("PRJ0004", "Pembangunan Jalan Batu", 2024, "Batu", "Rp 2.1M", "APBD", 0.0, 0.0, ""),
        DataProyek("PRJ0005", "Renovasi Jembatan Desa", 2023, "Desa Makmur", "Rp 500jt", "Dana Desa", 0.0, 0.0, ""),
        DataProyek("PRJ0006", "Pembangunan Balai Desa", 2024, "Sukamaju", "Rp 850jt", "APBD", 0.0, 0.0, ""),
        DataProyek("PRJ0007", "Pengadaan Bibit Unggul", 2023, "Kelompok Tani", "Rp 150jt", "Kementan", 0.0, 0.0, "")
    )

    // Launcher untuk Kamera (Intent + FileProvider)
    private val takePhotoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            binding.ivPreview.visibility = View.VISIBLE
            binding.ivPreview.setImageURI(photoUri)
            Toast.makeText(this, "Foto berhasil disimpan ke galeri desa", Toast.LENGTH_SHORT).show()
        }
    }

    // Launcher untuk Izin Kamera & Storage
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (permissions.all { it.value }) {
            // Izin diberikan
        } else {
            Toast.makeText(this, "Izin kamera diperlukan untuk fitur ini", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDigitalDesaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        checkPermissions()
        setupProyekDropdown()

        // 1. Fitur Ambil Foto
        binding.btnTakePhoto.setOnClickListener {
            openCamera()
        }

        // 2. Fitur Generate QR Code (ZXing)
        binding.btnGenerateQr.setOnClickListener {
            if (selectedProyekKode != null) {
                generateQrCode(selectedProyekKode!!)
            } else {
                Toast.makeText(this, "Pilih proyek terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }

        // 3. Fitur Scan QR (Navigate ke Scanner)
        binding.btnOpenScanner.setOnClickListener {
            val intent = Intent(this, QrScannerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupProyekDropdown() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, proyekList.map { it.nama })
        binding.autoCompleteProyek.setAdapter(adapter)
        binding.autoCompleteProyek.setOnItemClickListener { _, _, position, _ ->
            selectedProyekKode = proyekList[position].kode
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun checkPermissions() {
        val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permissions.any { ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED }) {
            requestPermissionLauncher.launch(permissions)
        }
    }

    private fun openCamera() {
        val photoFile = createImageFile()
        photoUri = FileProvider.getUriForFile(
            this,
            "${packageName}.fileprovider",
            photoFile
        )

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        }
        takePhotoLauncher.launch(intent)
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("DESA_${timeStamp}_", ".jpg", storageDir)
    }

    private fun generateQrCode(text: String) {
        try {
            val barcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap = barcodeEncoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 400, 400)
            binding.ivGeneratedQr.setImageBitmap(bitmap)
            binding.ivGeneratedQr.visibility = View.VISIBLE
            Toast.makeText(this, "QR Code Berhasil Dibuat", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

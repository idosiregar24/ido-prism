package com.example.ido_prism.TugasPertemuan2

import com.example.ido_prism.R
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ido_prism.databinding.ActivityGeometriBinding

class GeometricActivity : AppCompatActivity() {

    companion object {
        const val TAG = "IdoPrism"
    }

    // ── Segitiga ──────────────────────────────────────────
    private lateinit var etAlas: EditText
    private lateinit var etTinggiSegitiga: EditText
    private lateinit var btnHitungSegitiga: Button
    private lateinit var tvHasilSegitiga: TextView

    // ── Balok ─────────────────────────────────────────────
    private lateinit var etPanjang: EditText
    private lateinit var etLebar: EditText
    private lateinit var etTinggiBalok: EditText
    private lateinit var btnHitungBalok: Button
    private lateinit var tvHasilBalok: TextView

    private lateinit var binding: ActivityGeometriBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGeometriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar Setup
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Kalkulator Geometri"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbar.navigationIcon?.setTint(resources.getColor(android.R.color.white, theme))

        val judulDariMain = intent.getStringExtra("EXTRA_JUDUL")
        val deskripsiDariMain = intent.getStringExtra("EXTRA_DESKRIPSI")

        if (judulDariMain != null) {
            binding.tvHeaderTitle.text = judulDariMain
        }
        if (deskripsiDariMain != null) {
            binding.tvHeaderSubtitle.text = deskripsiDariMain
        }

        initViews()
        setupListeners()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    // Menghubungkan variabel Kotlin ke komponen XML berdasarkan ID
    private fun initViews() {
        // Segitiga — ID sesuai activity_main.xml
        etAlas            = findViewById(R.id.etAlas)
        etTinggiSegitiga  = findViewById(R.id.etTinggiSegitiga)
        btnHitungSegitiga = findViewById(R.id.btnHitungSegitiga)
        tvHasilSegitiga   = findViewById(R.id.tvHasilSegitiga)

        // Balok — ID sesuai activity_main.xml
        etPanjang      = findViewById(R.id.etPanjang)
        etLebar        = findViewById(R.id.etLebar)
        etTinggiBalok  = findViewById(R.id.etTinggiBalok)
        btnHitungBalok = findViewById(R.id.btnHitungBalok)
        tvHasilBalok   = findViewById(R.id.tvHasilBalok)
    }

    // Memasang listener ke masing-masing tombol
    private fun setupListeners() {
        btnHitungSegitiga.setOnClickListener {
            Log.d(TAG, "Tombol Hitung Segitiga ditekan")
            hitungSegitiga()
        }

        btnHitungBalok.setOnClickListener {
            Log.d(TAG, "Tombol Hitung Balok ditekan")
            hitungBalok()
        }
    }

    // ── Fungsi hitung Luas Segitiga ───────────────────────
    // Rumus: L = 0.5 × alas × tinggi
    private fun hitungSegitiga() {
        val inputAlas   = etAlas.text.toString().trim()
        val inputTinggi = etTinggiSegitiga.text.toString().trim()

        // Validasi: cek input kosong
        if (inputAlas.isEmpty() || inputTinggi.isEmpty()) {
            Log.d(TAG, "hitungSegitiga: input kosong — alas='$inputAlas', tinggi='$inputTinggi'")
            tvHasilSegitiga.text = "⚠ Alas dan tinggi tidak boleh kosong!"
            return
        }

        // Validasi: cek format angka
        val alas   = inputAlas.toDoubleOrNull()
        val tinggi = inputTinggi.toDoubleOrNull()

        if (alas == null || tinggi == null) {
            Log.d(TAG, "hitungSegitiga: format tidak valid — alas='$inputAlas', tinggi='$inputTinggi'")
            tvHasilSegitiga.text = "⚠ Masukkan angka yang valid!"
            return
        }

        // Hitung dan tampilkan hasil ke tvHasilSegitiga
        val hasil = 0.5 * alas * tinggi
        Log.d(TAG, "hitungSegitiga: alas=$alas, tinggi=$tinggi, hasil=$hasil")
        tvHasilSegitiga.text = formatHasil(hasil)
    }

    // ── Fungsi hitung Volume Balok ────────────────────────
    // Rumus: V = panjang × lebar × tinggi
    private fun hitungBalok() {
        val inputPanjang = etPanjang.text.toString().trim()
        val inputLebar   = etLebar.text.toString().trim()
        val inputTinggi  = etTinggiBalok.text.toString().trim()

        // Validasi: cek input kosong
        if (inputPanjang.isEmpty() || inputLebar.isEmpty() || inputTinggi.isEmpty()) {
            Log.d(TAG, "hitungBalok: input kosong — p='$inputPanjang', l='$inputLebar', t='$inputTinggi'")
            tvHasilBalok.text = "⚠ Semua field tidak boleh kosong!"
            return
        }

        // Validasi: cek format angka
        val panjang = inputPanjang.toDoubleOrNull()
        val lebar   = inputLebar.toDoubleOrNull()
        val tinggi  = inputTinggi.toDoubleOrNull()

        if (panjang == null || lebar == null || tinggi == null) {
            Log.d(TAG, "hitungBalok: format tidak valid — p='$inputPanjang', l='$inputLebar', t='$inputTinggi'")
            tvHasilBalok.text = "⚠ Masukkan angka yang valid!"
            return
        }

        // Hitung dan tampilkan hasil ke tvHasilBalok
        val hasil = panjang * lebar * tinggi
        Log.d(TAG, "hitungBalok: panjang=$panjang, lebar=$lebar, tinggi=$tinggi, hasil=$hasil")
        tvHasilBalok.text = formatHasil(hasil)
    }

    // ── Format angka hasil ────────────────────────────────
    // Kalau hasilnya bulat (misal 25.0) → tampil "25"
    // Kalau ada desimal (misal 12.5) → tampil "12.5"
    private fun formatHasil(value: Double): String {
        return if (value % 1.0 == 0.0) {
            value.toLong().toString()
        } else {
            "%.4g".format(value).trimEnd('0').trimEnd('.')
        }
    }
}

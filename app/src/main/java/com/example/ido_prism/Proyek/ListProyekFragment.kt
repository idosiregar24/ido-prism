package com.example.ido_prism.Proyek

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ido_prism.R
import com.example.ido_prism.databinding.FragmentListProyekBinding
import com.example.ido_prism.notification.NotificationHelper
import com.example.ido_prism.notification.ReminderManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class ListProyekFragment : Fragment() {
    private var _binding: FragmentListProyekBinding? = null
    private val binding get() = _binding!!

    private lateinit var notificationHelper: NotificationHelper
    private lateinit var reminderManager: ReminderManager
    private lateinit var proyekAdapter: ProyekAdapter

    private val fullProyekList = listOf(
        DataProyek(
            kode = "PRJ0001",
            nama = "Pembangunan Air Bersih Bontang",
            tahun = 2023,
            lokasi = "Bontang, Kalimantan Timur",
            anggaran = "Rp 1.250.000.000",
            sumberDana = "APBD",
            latitude = 0.137558,
            longitude = 117.501166,
            deskripsi = "Kegiatan dilakukan secara bertahap dengan mempertimbangkan kondisi geografis dan kebutuhan lapangan.",
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/93n9hEbfdeB9Lz3DPoRz1a4gp4tznF9HEj7TJh21.jpg",
            progress = 75,
            status = "Berjalan",
            kategori = "Sanitasi"
        ),
        DataProyek(
            kode = "PRJ0002",
            nama = "Pengadaan Peralatan Cimahi",
            tahun = 2023,
            lokasi = "Kelurahan Padangpanjang, Kecamatan Ville",
            anggaran = "Rp 914.192.986",
            sumberDana = "Hibah",
            latitude = 0.137558,
            longitude = 140.541166,
            deskripsi = "Kegiatan dilakukan secara bertahap dengan mempertimbangkan kondisi geografis dan kebutuhan lapangan.",
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/5iRy5R62CZrEKQdzpfY8TpS00u8dlCZdRRqpdF79.jpg",
            progress = 100,
            status = "Selesai",
            kategori = "Peralatan"
        ),
        DataProyek(
            kode = "PRJ0003",
            nama = "Pembangunan Drainase Yogyakarta",
            tahun = 2024,
            lokasi = "Kota Yogyakarta",
            anggaran = "Rp 750.000.000",
            sumberDana = "APBN",
            latitude = -7.79558,
            longitude = 110.36949,
            deskripsi = "Pembangunan sistem drainase untuk mengurangi risiko banjir di area perkotaan.",
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/2DhHCbK7AB8ggb70blPg9yeIH8QJ45ZswUl1k0Q1.png",
            progress = 40,
            status = "Berjalan",
            kategori = "Infrastruktur"
        ),
        DataProyek(
            kode = "PRJ0004",
            nama = "Pembangunan Jalan Batu",
            tahun = 2024,
            lokasi = "Batu, Jawa Timur",
            anggaran = "Rp 2.100.000.000",
            sumberDana = "APBD",
            latitude = -7.8704,
            longitude = 112.5264,
            deskripsi = "Peningkatan kualitas jalan akses menuju area wisata di Kota Batu.",
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/maNWjcbMCRqdAzGvvzB6lmzaluLGuY7MYtvZpv5R.jpg",
            progress = 15,
            status = "Tertunda",
            kategori = "Infrastruktur"
        ),
        DataProyek(
            kode = "PRJ0005",
            nama = "Renovasi Jembatan Desa",
            tahun = 2023,
            lokasi = "Desa Makmur",
            anggaran = "Rp 500.000.000",
            sumberDana = "Dana Desa",
            latitude = -6.1234,
            longitude = 106.1234,
            deskripsi = "Perbaikan struktur jembatan penyeberangan antar dusun.",
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/93n9hEbfdeB9Lz3DPoRz1a4gp4tznF9HEj7TJh21.jpg",
            progress = 90,
            status = "Berjalan",
            kategori = "Infrastruktur"
        ),
        DataProyek(
            kode = "PRJ0006",
            nama = "Pembangunan Balai Desa",
            tahun = 2024,
            lokasi = "Kecamatan Sukamaju",
            anggaran = "Rp 850.000.000",
            sumberDana = "APBD",
            latitude = -6.2345,
            longitude = 106.2345,
            deskripsi = "Pembangunan gedung pusat administrasi dan kegiatan kemasyarakatan.",
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/5iRy5R62CZrEKQdzpfY8TpS00u8dlCZdRRqpdF79.jpg",
            progress = 60,
            status = "Berjalan",
            kategori = "Infrastruktur"
        ),
        DataProyek(
            kode = "PRJ0007",
            nama = "Pengadaan Bibit Unggul",
            tahun = 2023,
            lokasi = "Kelompok Tani Harapan",
            anggaran = "Rp 150.000.000",
            sumberDana = "Kementerian Pertanian",
            latitude = -6.3456,
            longitude = 106.3456,
            deskripsi = "Penyediaan bibit padi varietas unggul untuk meningkatkan hasil panen.",
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/2DhHCbK7AB8ggb70blPg9yeIH8QJ45ZswUl1k0Q1.png",
            progress = 100,
            status = "Selesai",
            kategori = "Peralatan"
        )
    )

    private var filteredProyekList = fullProyekList.toList()

    // Launcher untuk meminta izin notifikasi (Android 13+)
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(requireContext(), "Izin notifikasi diberikan", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Izin ditolak. Notifikasi tidak akan muncul.", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListProyekBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notificationHelper = NotificationHelper(requireContext())
        reminderManager = ReminderManager(requireContext())
        
        // Meminta izin notifikasi saat fragment dibuka
        checkNotificationPermission()
        
        setupRecyclerView()
        setupChipFilter()
        
        binding.fabAddNote.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur Tambah Proyek/Catatan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupChipFilter() {
        binding.chipGroupFilter.setOnCheckedStateChangeListener { _, checkedIds ->
            val selectedChipId = checkedIds.firstOrNull()
            
            filteredProyekList = when (selectedChipId) {
                R.id.chipInfrastruktur -> fullProyekList.filter { it.kategori == "Infrastruktur" }
                R.id.chipSanitasi -> fullProyekList.filter { it.kategori == "Sanitasi" }
                R.id.chipPeralatan -> fullProyekList.filter { it.kategori == "Peralatan" }
                else -> fullProyekList
            }
            proyekAdapter.updateData(filteredProyekList)
        }
    }

    private fun setupRecyclerView() {
        binding.rvProyek.layoutManager = GridLayoutManager(context, 2)
        proyekAdapter = ProyekAdapter(filteredProyekList) { proyek ->
            // Contoh trigger notification langsung saat item diklik (Skenario: Proyek Terpilih)
            notificationHelper.showNotification(
                "Proyek Diakses",
                "Anda sedang melihat detail ${proyek.nama}",
                proyek.kode
            )
            showDetailProyek(proyek)
        }
        binding.rvProyek.adapter = proyekAdapter
    }

    private fun showDetailProyek(proyek: DataProyek) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet_detail_proyek, null)
        
        val tvTitle = view.findViewById<TextView>(R.id.tvTitleDetail)
        val tvFullDetail = view.findViewById<TextView>(R.id.tvFullDetail)
        val etMinutes = view.findViewById<TextInputEditText>(R.id.etReminderMinutes)
        val btnSetReminder = view.findViewById<MaterialButton>(R.id.btnSetReminder)
        val btnClose = view.findViewById<MaterialButton>(R.id.btnCloseDetail)
        
        tvTitle.text = proyek.nama
        
        val detailText = """
            Kode Proyek: ${proyek.kode}
            Tahun: ${proyek.tahun}
            Lokasi: ${proyek.lokasi}
            Anggaran: ${proyek.anggaran}
            Sumber Dana: ${proyek.sumberDana}
            Latitude: ${proyek.latitude}
            Longitude: ${proyek.longitude}
            
            Deskripsi:
            ${proyek.deskripsi}
        """.trimIndent()
        
        tvFullDetail.text = detailText

        btnSetReminder.setOnClickListener {
            val minutesStr = etMinutes.text.toString()
            if (minutesStr.isNotEmpty()) {
                val minutes = minutesStr.toInt()
                reminderManager.setReminder(minutes, proyek.kode, proyek.nama)
                Toast.makeText(requireContext(), "Reminder diatur $minutes menit dari sekarang", Toast.LENGTH_SHORT).show()
                bottomSheetDialog.dismiss()
            } else {
                etMinutes.error = "Masukkan menit"
            }
        }
        
        btnClose.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

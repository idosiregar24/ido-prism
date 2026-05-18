package com.example.ido_prism.Proyek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ido_prism.R
import com.example.ido_prism.databinding.FragmentListProyekBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton

class ListProyekFragment : Fragment() {
    private var _binding: FragmentListProyekBinding? = null
    private val binding get() = _binding!!

    private val proyekList = listOf(
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
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/93n9hEbfdeB9Lz3DPoRz1a4gp4tznF9HEj7TJh21.jpg"
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
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/5iRy5R62CZrEKQdzpfY8TpS00u8dlCZdRRqpdF79.jpg"
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
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/2DhHCbK7AB8ggb70blPg9yeIH8QJ45ZswUl1k0Q1.png"
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
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/maNWjcbMCRqdAzGvvzB6lmzaluLGuY7MYtvZpv5R.jpg"
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
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/93n9hEbfdeB9Lz3DPoRz1a4gp4tznF9HEj7TJh21.jpg"
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
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/5iRy5R62CZrEKQdzpfY8TpS00u8dlCZdRRqpdF79.jpg"
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
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/2DhHCbK7AB8ggb70blPg9yeIH8QJ45ZswUl1k0Q1.png"
        ),
        DataProyek(
            kode = "PRJ0008",
            nama = "Pemasangan Lampu Jalan",
            tahun = 2024,
            lokasi = "Jalan Protokol Desa",
            anggaran = "Rp 200.000.000",
            sumberDana = "APBD",
            latitude = -6.4567,
            longitude = 106.4567,
            deskripsi = "Penerangan jalan umum untuk keamanan dan kenyamanan warga di malam hari.",
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/maNWjcbMCRqdAzGvvzB6lmzaluLGuY7MYtvZpv5R.jpg"
        ),
        DataProyek(
            kode = "PRJ0009",
            nama = "Pembangunan Irigasi Sawah",
            tahun = 2023,
            lokasi = "Area Persawahan Selatan",
            anggaran = "Rp 400.000.000",
            sumberDana = "Dana Desa",
            latitude = -6.5678,
            longitude = 106.5678,
            deskripsi = "Pembangunan saluran air untuk menjamin pasokan air ke sawah warga.",
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/93n9hEbfdeB9Lz3DPoRz1a4gp4tznF9HEj7TJh21.jpg"
        ),
        DataProyek(
            kode = "PRJ0010",
            nama = "Renovasi Sekolah Dasar",
            tahun = 2024,
            lokasi = "SDN 01 Sukadamai",
            anggaran = "Rp 600.000.000",
            sumberDana = "APBN",
            latitude = -6.6789,
            longitude = 106.6789,
            deskripsi = "Perbaikan ruang kelas dan fasilitas sanitasi sekolah.",
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/5iRy5R62CZrEKQdzpfY8TpS00u8dlCZdRRqpdF79.jpg"
        ),
        DataProyek(
            kode = "PRJ0011",
            nama = "Pembangunan Poskamling",
            tahun = 2023,
            lokasi = "RW 05 Desa Sejahtera",
            anggaran = "Rp 30.000.000",
            sumberDana = "Swadaya Masyarakat",
            latitude = -6.7890,
            longitude = 106.7890,
            deskripsi = "Pembangunan pos keamanan lingkungan untuk ronda malam.",
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/2DhHCbK7AB8ggb70blPg9yeIH8QJ45ZswUl1k0Q1.png"
        ),
        DataProyek(
            kode = "PRJ0012",
            nama = "Pengadaan Alat Kesehatan",
            tahun = 2024,
            lokasi = "Puskesmas Pembantu",
            anggaran = "Rp 350.000.000",
            sumberDana = "Dinkes",
            latitude = -6.8901,
            longitude = 106.8901,
            deskripsi = "Penyediaan peralatan medis dasar untuk pelayanan kesehatan masyarakat.",
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/maNWjcbMCRqdAzGvvzB6lmzaluLGuY7MYtvZpv5R.jpg"
        ),
        DataProyek(
            kode = "PRJ0013",
            nama = "Pembangunan Lapangan Olahraga",
            tahun = 2023,
            lokasi = "Kompleks Olahraga Desa",
            anggaran = "Rp 700.000.000",
            sumberDana = "Dana Desa",
            latitude = -6.9012,
            longitude = 106.9012,
            deskripsi = "Pembangunan lapangan serbaguna untuk olahraga voli dan futsal.",
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/93n9hEbfdeB9Lz3DPoRz1a4gp4tznF9HEj7TJh21.jpg"
        ),
        DataProyek(
            kode = "PRJ0014",
            nama = "Penghijauan Hutan Desa",
            tahun = 2024,
            lokasi = "Lereng Gunung Salak",
            anggaran = "Rp 100.000.000",
            sumberDana = "KLHK",
            latitude = -7.0123,
            longitude = 107.0123,
            deskripsi = "Penanaman 10.000 pohon untuk konservasi lahan dan pencegahan longsor.",
            gambar = "https://proyekdesa.alwaysdata.net/storage/media/5iRy5R62CZrEKQdzpfY8TpS00u8dlCZdRRqpdF79.jpg"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListProyekBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvProyek.layoutManager = GridLayoutManager(context, 2)
        binding.rvProyek.adapter = ProyekAdapter(proyekList) { proyek ->
            showDetailProyek(proyek)
        }
    }

    private fun showDetailProyek(proyek: DataProyek) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet_detail_proyek, null)
        
        val tvTitle = view.findViewById<TextView>(R.id.tvTitleDetail)
        val tvFullDetail = view.findViewById<TextView>(R.id.tvFullDetail)
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

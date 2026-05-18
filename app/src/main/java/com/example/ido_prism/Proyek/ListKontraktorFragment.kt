package com.example.ido_prism.Proyek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ido_prism.databinding.FragmentListKontraktorBinding

class ListKontraktorFragment : Fragment() {
    private var _binding: FragmentListKontraktorBinding? = null
    private val binding get() = _binding!!

    private val kontraktorList = listOf(
        DataKontraktor(
            id = "K001",
            nama = "Budi Santoso",
            perusahaan = "PT. Bangun Jaya Utama",
            alamat = "Jakarta",
            spesialisasi = "Konstruksi Jalan",
            rating = 4.8f
        ),
        DataKontraktor(
            id = "K002",
            nama = "Siti Aminah",
            perusahaan = "CV. Tata Ruang Sejahtera",
            alamat = "Bandung",
            spesialisasi = "Arsitektur & Interior",
            rating = 4.5f
        ),
        DataKontraktor(
            id = "K003",
            nama = "Agus Prasetyo",
            perusahaan = "PT. Sumber Air Minum",
            alamat = "Surabaya",
            spesialisasi = "Instalasi Air Bersih",
            rating = 4.7f
        ),
        DataKontraktor(
            id = "K004",
            nama = "Indra Wijaya",
            perusahaan = "PT. Infrastruktur Madani",
            alamat = "Semarang",
            spesialisasi = "Sistem Drainase",
            rating = 4.6f
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListKontraktorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvKontraktor.layoutManager = GridLayoutManager(context, 1)
        binding.rvKontraktor.adapter = KontraktorAdapter(kontraktorList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.ido_prism.Aspirasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ido_prism.R
import com.example.ido_prism.data.AppDatabase
import com.example.ido_prism.data.entity.AspirasiEntity
import com.example.ido_prism.databinding.FragmentAspirasiBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FragmentAspirasi : Fragment(), AspirasiAdapter.OnAspirasiListener {

    private var _binding: FragmentAspirasiBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var db: AppDatabase
    private val aspirasiList = mutableListOf<AspirasiEntity>()
    private lateinit var adapter: AspirasiAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAspirasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        db = AppDatabase.getInstance(requireContext())
        setupRecyclerView()
        
        binding.fabAddAspirasi.setOnClickListener {
            showAddAspirasiDialog()
        }
        
        loadAspirasi()
    }

    private fun setupRecyclerView() {
        adapter = AspirasiAdapter(aspirasiList, this)
        binding.rvAspirasi.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAspirasi.adapter = adapter
    }

    private fun loadAspirasi() {
        lifecycleScope.launch {
            val data = db.aspirasiDao().getAll()
            aspirasiList.clear()
            aspirasiList.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }

    private fun showAddAspirasiDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_aspirasi, null)
        val etIsi = dialogView.findViewById<EditText>(R.id.etAspirasiContent)
        val autoCompleteKategori = dialogView.findViewById<AutoCompleteTextView>(R.id.autoCompleteKategori)
        
        val kategoriOptions = arrayOf("Infrastruktur", "Sosial", "Keamanan", "Kesehatan", "Lainnya")
        val adapterKategori = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, kategoriOptions)
        autoCompleteKategori.setAdapter(adapterKategori)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Sampaikan Aspirasi Anda")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val isi = etIsi.text.toString()
                val kategori = autoCompleteKategori.text.toString()
                
                if (isi.isNotEmpty() && kategori.isNotEmpty()) {
                    val date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
                    saveAspirasi(AspirasiEntity(kategori = kategori, isiAspirasi = isi, tanggal = date))
                } else {
                    Toast.makeText(requireContext(), "Lengkapi semua data", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun saveAspirasi(aspirasi: AspirasiEntity) {
        lifecycleScope.launch {
            db.aspirasiDao().insert(aspirasi)
            loadAspirasi()
            Toast.makeText(requireContext(), "Aspirasi disimpan sebagai Draft", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDelete(aspirasi: AspirasiEntity) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Hapus Aspirasi")
            .setMessage("Apakah Anda yakin ingin menghapus aspirasi ini?")
            .setPositiveButton("Hapus") { _, _ ->
                lifecycleScope.launch {
                    db.aspirasiDao().delete(aspirasi)
                    loadAspirasi()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
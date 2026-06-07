package com.example.ido_prism.Agenda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ido_prism.data.AppDatabase
import com.example.ido_prism.data.entity.AgendaEntity
import com.example.ido_prism.databinding.FragmentNoteBinding // Kita reuse layout note atau buat baru
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FragmentAgenda : Fragment(), AgendaAdapter.OnAgendaListener {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var db: AppDatabase
    private val agendaList = mutableListOf<AgendaEntity>()
    private lateinit var adapter: AgendaAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        db = AppDatabase.getInstance(requireContext())
        setupRecyclerView()
        
        binding.fabAddNote.setOnClickListener {
            showAddAgendaDialog()
        }
        
        loadAgenda()
    }

    private fun setupRecyclerView() {
        adapter = AgendaAdapter(agendaList, this)
        binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotes.adapter = adapter
    }

    private fun loadAgenda() {
        lifecycleScope.launch {
            val data = db.agendaDao().getAll()
            agendaList.clear()
            agendaList.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }

    private fun showAddAgendaDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(com.example.ido_prism.R.layout.dialog_add_agenda, null)
        val etTitle = dialogView.findViewById<EditText>(com.example.ido_prism.R.id.etAgendaTitle)
        val etLocation = dialogView.findViewById<EditText>(com.example.ido_prism.R.id.etAgendaLocation)
        val etDesc = dialogView.findViewById<EditText>(com.example.ido_prism.R.id.etAgendaDesc)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Tambah Agenda Baru")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val title = etTitle.text.toString()
                val location = etLocation.text.toString()
                val desc = etDesc.text.toString()
                
                if (title.isNotEmpty()) {
                    val date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
                    saveAgenda(AgendaEntity(namaKegiatan = title, lokasi = location, tanggal = date, deskripsi = desc))
                } else {
                    Toast.makeText(requireContext(), "Judul tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun saveAgenda(agenda: AgendaEntity) {
        lifecycleScope.launch {
            db.agendaDao().insert(agenda)
            loadAgenda()
        }
    }

    override fun onDelete(agenda: AgendaEntity) {
        lifecycleScope.launch {
            db.agendaDao().delete(agenda)
            loadAgenda()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
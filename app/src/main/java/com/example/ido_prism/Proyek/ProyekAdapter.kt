package com.example.ido_prism.Proyek

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.ido_prism.R
import com.example.ido_prism.databinding.ItemProyekBinding

class ProyekAdapter(
    private val listProyek: List<DataProyek>,
    private val onItemClick: (DataProyek) -> Unit
) : RecyclerView.Adapter<ProyekAdapter.ListViewHolder>() {

    class ListViewHolder(val binding: ItemProyekBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemProyekBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val proyek = listProyek[position]
        
        // Log untuk memantau pemuatan data saat scroll
        Log.d("ProyekAdapter", "Memuat data posisi $position: ${proyek.nama}")

        holder.binding.tvNamaProyek.text = proyek.nama
        
        holder.binding.ivProyek.load(proyek.gambar) {
            crossfade(true)
            placeholder(R.drawable.bg_proyek)
            error(R.drawable.bg_proyek)
        }
        
        holder.itemView.setOnClickListener {
            onItemClick(proyek)
        }
    }

    override fun getItemCount(): Int = listProyek.size
}
package com.example.ido_prism.Proyek

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.ido_prism.R
import com.example.ido_prism.databinding.ItemProyekBinding

class ProyekAdapter(
    private var listProyek: List<DataProyek>,
    private val onItemClick: (DataProyek) -> Unit
) : RecyclerView.Adapter<ProyekAdapter.ListViewHolder>() {

    class ListViewHolder(val binding: ItemProyekBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemProyekBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val proyek = listProyek[position]
        
        with(holder.binding) {
            tvNamaProyek.text = proyek.nama
            tvStatus.text = proyek.status
            progressProyek.progress = proyek.progress
            tvProgressText.text = "Progress: ${proyek.progress}%"
            
            ivProyek.load(proyek.gambar) {
                crossfade(true)
                placeholder(R.drawable.bg_proyek)
                error(R.drawable.bg_proyek)
            }
            
            // Customize status badge background based on status
            tvStatus.setBackgroundResource(when(proyek.status) {
                "Selesai" -> R.drawable.bg_status_badge_finished
                "Tertunda" -> R.drawable.bg_status_badge_delayed
                else -> R.drawable.bg_status_badge // Default: Berjalan
            })
        }
        
        holder.itemView.setOnClickListener {
            onItemClick(proyek)
        }
    }

    override fun getItemCount(): Int = listProyek.size

    fun updateData(newList: List<DataProyek>) {
        listProyek = newList
        notifyDataSetChanged()
    }
}
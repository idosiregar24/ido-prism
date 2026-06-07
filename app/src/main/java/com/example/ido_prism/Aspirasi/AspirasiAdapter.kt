package com.example.ido_prism.Aspirasi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ido_prism.data.entity.AspirasiEntity
import com.example.ido_prism.databinding.ItemAspirasiBinding

class AspirasiAdapter(
    private val list: List<AspirasiEntity>,
    private val listener: OnAspirasiListener
) : RecyclerView.Adapter<AspirasiAdapter.ViewHolder>() {

    interface OnAspirasiListener {
        fun onDelete(aspirasi: AspirasiEntity)
    }

    inner class ViewHolder(val binding: ItemAspirasiBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAspirasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.apply {
            tvKategori.text = "Kategori: ${item.kategori}"
            tvIsiAspirasi.text = item.isiAspirasi
            tvTanggal.text = item.tanggal
            tvStatus.text = item.status
            
            btnDeleteAspirasi.setOnClickListener {
                listener.onDelete(item)
            }
        }
    }

    override fun getItemCount(): Int = list.size
}
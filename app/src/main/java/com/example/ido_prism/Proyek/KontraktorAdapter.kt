package com.example.ido_prism.Proyek

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ido_prism.databinding.ItemKontraktorBinding

class KontraktorAdapter(private val listKontraktor: List<DataKontraktor>) :
    RecyclerView.Adapter<KontraktorAdapter.ListViewHolder>() {

    class ListViewHolder(val binding: ItemKontraktorBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemKontraktorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val kontraktor = listKontraktor[position]
        holder.binding.tvNamaKontraktor.text = kontraktor.nama
        holder.binding.tvPerusahaan.text = kontraktor.perusahaan
        holder.binding.tvRating.text = "⭐ ${kontraktor.rating}"
    }

    override fun getItemCount(): Int = listKontraktor.size
}
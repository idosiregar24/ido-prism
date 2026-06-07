package com.example.ido_prism.Agenda

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ido_prism.data.entity.AgendaEntity
import com.example.ido_prism.databinding.ItemAgendaBinding

class AgendaAdapter(
    private val list: List<AgendaEntity>,
    private val listener: OnAgendaListener
) : RecyclerView.Adapter<AgendaAdapter.ViewHolder>() {

    interface OnAgendaListener {
        fun onDelete(agenda: AgendaEntity)
    }

    inner class ViewHolder(val binding: ItemAgendaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAgendaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.apply {
            tvAgendaTitle.text = item.namaKegiatan
            tvAgendaDate.text = item.tanggal
            tvAgendaDesc.text = item.deskripsi
            
            btnDeleteAgenda.setOnClickListener {
                listener.onDelete(item)
            }
        }
    }

    override fun getItemCount(): Int = list.size
}
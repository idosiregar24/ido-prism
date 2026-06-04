package com.example.ido_prism.Note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ido_prism.data.entity.NoteEntity
import com.example.ido_prism.databinding.ItemNoteBinding
import com.google.android.material.snackbar.Snackbar

class NoteAdapter(
    private val notes: List<NoteEntity>
) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.binding.tvTitle.text = note.title
        holder.binding.tvContent.text = note.content

        holder.itemView.setOnClickListener {
            Snackbar.make(holder.itemView, "Clicked: ${note.title}", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = notes.size
}
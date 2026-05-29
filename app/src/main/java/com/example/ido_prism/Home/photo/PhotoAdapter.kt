package com.example.ido_prism.Home.photo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ido_prism.data.model.NewsItem
import com.example.ido_prism.databinding.ItemPhotoBinding

class PhotoAdapter(private val items: List<NewsItem>) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    inner class PhotoViewHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            tvAuthor.text = item.title
            tvDescription.text = item.body
            tvCategory.text = item.category
            tvDate.text = item.date
            
            Glide.with(holder.itemView.context)
                .load(item.imageUrl)
                .centerCrop()
                .into(imgPhoto)
        }
    }

    override fun getItemCount(): Int = items.size
}

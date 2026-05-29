package com.example.ido_prism.Home.photo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ido_prism.data.model.NewsItem
import com.example.ido_prism.databinding.ItemPhotoBinding

class PopularNewsAdapter(private val items: List<NewsItem>) : RecyclerView.Adapter<PopularNewsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // Adjust width for horizontal scroll
        binding.root.layoutParams.width = (parent.width * 0.8).toInt()
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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

package com.mechadev.indirim.aktuel.urunler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mechadev.indirim.aktuel.urunler.adapter.RecentlyAddedAdapter.ViewHolder
import com.mechadev.indirim.aktuel.urunler.databinding.ItemHomeBinding
import com.mechadev.indirim.aktuel.urunler.databinding.ItemImageviewBinding

class ImageAdapter(private val images: List<String>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(binding: ItemImageviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = binding.imageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(holder.imageView.context)
            .load(images[position])
            .into(holder.imageView)
    }

    override fun getItemCount() = images.size
}

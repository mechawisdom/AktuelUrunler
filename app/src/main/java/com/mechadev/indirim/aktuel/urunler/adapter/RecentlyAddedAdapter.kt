package com.mechadev.indirim.aktuel.urunler.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.like.LikeButton
import com.like.OnLikeListener
import com.mechadev.indirim.aktuel.urunler.databinding.ItemHomeBinding
import com.mechadev.indirim.aktuel.urunler.model.RecentlyAddedModel
import com.mechadev.indirim.aktuel.urunler.ui.detail.DetailActivity
import com.mechadev.indirim.aktuel.urunler.util.Constants.IMAGE_BASE_URL
import com.mechadev.indirim.aktuel.urunler.util.RevealAnimation
import com.mechadev.indirim.aktuel.urunler.util.SharedPreferencesHelper
import com.mechadev.indirim.aktuel.urunler.util.SharedPreferencesHelper.Companion.FAVORITES
import com.mechadev.indirim.aktuel.urunler.util.extensions.loadImage

class RecentlyAddedAdapter(
    val context: Context
) : RecyclerView.Adapter<RecentlyAddedAdapter.ViewHolder>() {
    private val sharedPreferencesHelper = SharedPreferencesHelper(context)
    private var recentlyAddedList: List<RecentlyAddedModel> = emptyList()

    var favoriteSet = sharedPreferencesHelper.getStringSet(FAVORITES)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return recentlyAddedList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recentlyAddedModel = recentlyAddedList[position]

        val isFavorited = favoriteSet.contains(recentlyAddedModel.id.toString())

        holder.binding.favoriteImageView.isLiked = isFavorited

        val imagePath =
            IMAGE_BASE_URL + recentlyAddedModel.imagePath + "/" + recentlyAddedModel.imagePath
        holder.binding.marketTextView.text =
            if (recentlyAddedModel.market.contains("Market", ignoreCase = true)) {
                recentlyAddedModel.market
            } else {
                "${recentlyAddedModel.market} Market"
            }

        holder.binding.titleTextView.text = recentlyAddedModel.title
        holder.binding.pageCountTextView.text = recentlyAddedModel.count.toString() + " Sayfa"

        holder.binding.posterImageView.loadImage(imagePath + "_first.jpg")


        holder.binding.favoriteImageView.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                val updatedSet =
                    favoriteSet.toMutableSet().apply { add(recentlyAddedModel.id.toString()) }
                sharedPreferencesHelper.saveStringSet(FAVORITES, updatedSet)
                Toast.makeText(context, "Kaydedildi", Toast.LENGTH_SHORT).show()
            }

            override fun unLiked(likeButton: LikeButton?) {
                val updatedSet =
                    favoriteSet.toMutableSet().apply { remove(recentlyAddedModel.id.toString()) }
                sharedPreferencesHelper.saveStringSet(FAVORITES, updatedSet)
                Toast.makeText(context, "Kaydedilenlerden silindi", Toast.LENGTH_SHORT).show()
            }
        })

        holder.itemView.setOnClickListener {
            val marketName =
                if (recentlyAddedModel.market.contains("Market", ignoreCase = true)) {
                    recentlyAddedModel.market
                } else {
                    "${recentlyAddedModel.market} Market"
                }


            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("id", recentlyAddedModel.id)
            intent.putExtra("page_size", recentlyAddedModel.count)
            intent.putExtra("market", marketName)
            intent.putExtra("image", imagePath + "_default.jpg")
            intent.putExtra("image_path", recentlyAddedModel.imagePath)
            val mWidth = context.resources.displayMetrics.widthPixels / 2
            val mHeight = context.resources.displayMetrics.heightPixels / 2

            intent.putExtra(RevealAnimation.EXTRA_CIRCULAR_REVEAL_X, mWidth)
            intent.putExtra(RevealAnimation.EXTRA_CIRCULAR_REVEAL_Y, mHeight)
            val options = ActivityOptions.makeCustomAnimation(context, 0, 0)
            context.startActivity(intent, options.toBundle())
        }
    }

    inner class ViewHolder(val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root)


    @SuppressLint("NotifyDataSetChanged")
    fun setAddedList(setList: List<RecentlyAddedModel>) {
        recentlyAddedList = setList
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSharedData() {
        val sharedPreferencesHelper = SharedPreferencesHelper(context)
        favoriteSet = sharedPreferencesHelper.getStringSet(FAVORITES)
        notifyDataSetChanged()
    }
}
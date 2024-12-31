package com.mechadev.indirim.aktuel.urunler.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mechadev.indirim.aktuel.urunler.databinding.ItemShoppingListBinding
import com.mechadev.indirim.aktuel.urunler.model.ShoppingItemModel

class ShoppingListAddItemAdapter(
    private val onItemClicked: (ShoppingItemModel) -> Unit
) :
    RecyclerView.Adapter<ShoppingListAddItemAdapter.ItemViewHolder>() {
    private var shoppingList: List<ShoppingItemModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemShoppingListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = shoppingList[position]
        holder.binding.itemTextView.text = item.content

        // Item tıklanırsa işlem yapılabilir (silme vs.)
        holder.itemView.setOnLongClickListener {
            onItemClicked(item)
            true
        }
    }

    override fun getItemCount(): Int = shoppingList.size

    inner class ItemViewHolder(val binding: ItemShoppingListBinding) :
        RecyclerView.ViewHolder(binding.root)


    @SuppressLint("NotifyDataSetChanged")
    fun setItemList(setList: List<ShoppingItemModel>) {
        shoppingList = setList
        notifyDataSetChanged()
    }
}

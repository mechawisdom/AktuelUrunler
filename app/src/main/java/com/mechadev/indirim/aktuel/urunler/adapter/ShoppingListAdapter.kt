package com.mechadev.indirim.aktuel.urunler.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mechadev.indirim.aktuel.urunler.databinding.ItemShoppingListLayoutBinding
import com.mechadev.indirim.aktuel.urunler.model.ShoppingItemModel
import com.mechadev.indirim.aktuel.urunler.model.ShoppingListModel
import com.mechadev.indirim.aktuel.urunler.ui.bottomsheet.ShoppingListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShoppingListAdapter(
    private val shoppingListViewModel: ShoppingListViewModel,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<ShoppingListAdapter.ShoppingViewHolder>() {

    private val items = mutableListOf<ShoppingListModel>()

    inner class ShoppingViewHolder(val binding: ItemShoppingListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        return ShoppingViewHolder(
            ItemShoppingListLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val currentItem = items[position]
        holder.binding.shoppingListTitle.text = currentItem.title
        holder.binding.shoppingListDate.text = currentItem.dateText

        val childAdapter = ShoppingListAddItemAdapter { shoppingItemModel: ShoppingItemModel ->
            println(shoppingItemModel)
        }
        holder.binding.shoppingListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = childAdapter
        }
        shoppingListViewModel.getItemsForList(currentItem.id).observe(
            lifecycleOwner
        ) { shoppingItems ->
            childAdapter.setItemList(shoppingItems)
        }

    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newItems: List<ShoppingListModel>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}

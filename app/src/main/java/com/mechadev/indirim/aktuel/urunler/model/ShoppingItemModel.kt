package com.mechadev.indirim.aktuel.urunler.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mechadev.indirim.aktuel.urunler.util.Constants.SHOPPING_ITEM_TABLE_NAME
import com.mechadev.indirim.aktuel.urunler.util.Constants.SHOPPING_LIST_TABLE_NAME

@Entity(tableName = SHOPPING_ITEM_TABLE_NAME)
data class ShoppingItemModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val shoppingListId: Int,
    val content: String,
    val isChecked: Boolean = false
)

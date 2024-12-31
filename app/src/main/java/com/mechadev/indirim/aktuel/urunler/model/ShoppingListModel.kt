package com.mechadev.indirim.aktuel.urunler.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mechadev.indirim.aktuel.urunler.util.Constants.SHOPPING_LIST_TABLE_NAME

@Entity(tableName = SHOPPING_LIST_TABLE_NAME)
data class ShoppingListModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val dateText: String,
    val createTime: Long,
    val updateTime: Long
)

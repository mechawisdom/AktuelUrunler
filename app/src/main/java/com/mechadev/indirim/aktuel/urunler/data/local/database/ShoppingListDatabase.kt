package com.mechadev.indirim.aktuel.urunler.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mechadev.indirim.aktuel.urunler.data.local.dao.ShoppingItemDao
import com.mechadev.indirim.aktuel.urunler.data.local.dao.ShoppingListDao
import com.mechadev.indirim.aktuel.urunler.model.ShoppingItemModel
import com.mechadev.indirim.aktuel.urunler.model.ShoppingListModel

@Database(
    entities = [ShoppingListModel::class, ShoppingItemModel::class],
    version = 1,
    exportSchema = false
)
abstract class ShoppingListDatabase : RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun shoppingItemDao(): ShoppingItemDao
}
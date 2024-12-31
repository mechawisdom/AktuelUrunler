package com.mechadev.indirim.aktuel.urunler.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mechadev.indirim.aktuel.urunler.model.RecentlyAddedModel
import com.mechadev.indirim.aktuel.urunler.model.ShoppingItemModel
import com.mechadev.indirim.aktuel.urunler.model.ShoppingListModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ShoppingItemModel)

    @Query("SELECT * FROM shopping_item WHERE shoppingListId = :shoppingListId ORDER BY id DESC")
    suspend fun getItemsByListId(shoppingListId: Int): List<ShoppingItemModel>

    @Query("SELECT * FROM shopping_item WHERE shoppingListId = :shoppingListId ORDER BY id DESC")
    fun getItemsByListIdFlow(shoppingListId: Int): Flow<List<ShoppingItemModel>>

    @Query("SELECT * FROM shopping_item WHERE shoppingListId = :shoppingListId ORDER BY id DESC")
    fun getItemsForShoppingList(shoppingListId: Int): LiveData<List<ShoppingItemModel>>

    @Update
    suspend fun updateItem(item: ShoppingItemModel)

    @Delete
    suspend fun deleteItem(item: ShoppingItemModel)
}
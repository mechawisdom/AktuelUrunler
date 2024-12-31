package com.mechadev.indirim.aktuel.urunler.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mechadev.indirim.aktuel.urunler.model.RecentlyAddedModel
import com.mechadev.indirim.aktuel.urunler.model.ShoppingListModel

@Dao
interface ShoppingListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(shoppingListModel: ShoppingListModel): Long

    @Query("SELECT * FROM shopping_list WHERE dateText = :dateText  LIMIT 1")
    suspend fun getListByDate(dateText: String): ShoppingListModel?

    @Query("SELECT * FROM shopping_list")
    suspend fun getAllLists(): List<ShoppingListModel>

    @Update
    suspend fun updateList(shoppingList: ShoppingListModel)

    @Delete
    suspend fun deleteList(list: ShoppingListModel)

    @Query("DELETE FROM shopping_list WHERE id = :id")
    suspend fun deleteItemById(id: Int)

    @Query("DELETE FROM shopping_list")
    suspend fun deleteAllItems()
}
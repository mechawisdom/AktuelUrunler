package com.mechadev.indirim.aktuel.urunler.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mechadev.indirim.aktuel.urunler.model.RecentlyAddedModel

@Dao
interface RecentlyAddedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentlyAddedItem(marketModel: RecentlyAddedModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentlyAddedItems(marketModels: List<RecentlyAddedModel>)

    @Query("SELECT * FROM recently_added")
    suspend fun getRecentlyAddedItems(): List<RecentlyAddedModel>

    @Query("DELETE FROM recently_added")
    suspend fun deleteAllItems()
}
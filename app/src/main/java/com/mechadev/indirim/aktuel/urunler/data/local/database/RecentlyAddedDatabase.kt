package com.mechadev.indirim.aktuel.urunler.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mechadev.indirim.aktuel.urunler.data.local.dao.RecentlyAddedDao
import com.mechadev.indirim.aktuel.urunler.model.RecentlyAddedModel

@Database(entities = [RecentlyAddedModel::class], version = 1, exportSchema = false)
abstract class RecentlyAddedDatabase : RoomDatabase() {
    abstract fun recentlyAddedDao(): RecentlyAddedDao
}
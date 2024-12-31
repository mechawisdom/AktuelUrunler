package com.mechadev.indirim.aktuel.urunler.di

import android.content.Context
import androidx.room.Room
import com.mechadev.indirim.aktuel.urunler.data.local.dao.RecentlyAddedDao
import com.mechadev.indirim.aktuel.urunler.data.local.dao.ShoppingItemDao
import com.mechadev.indirim.aktuel.urunler.data.local.dao.ShoppingListDao
import com.mechadev.indirim.aktuel.urunler.data.local.database.RecentlyAddedDatabase
import com.mechadev.indirim.aktuel.urunler.data.local.database.ShoppingListDatabase
import com.mechadev.indirim.aktuel.urunler.util.Constants.RECENTLY_ADDED_DATABASE_NAME
import com.mechadev.indirim.aktuel.urunler.util.Constants.SHOPPING_LIST_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRecentlyAddedDatabase(@ApplicationContext context: Context): RecentlyAddedDatabase {
        return Room.databaseBuilder(context, RecentlyAddedDatabase::class.java, RECENTLY_ADDED_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRecentlyAddedDao(recentlyAddedDatabase: RecentlyAddedDatabase): RecentlyAddedDao {
        return recentlyAddedDatabase.recentlyAddedDao()
    }


    @Provides
    @Singleton
    fun provideShoppingListDatabase(@ApplicationContext context: Context): ShoppingListDatabase {
        return Room.databaseBuilder(context, ShoppingListDatabase::class.java, SHOPPING_LIST_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideShoppingListDao(shoppingListDatabase: ShoppingListDatabase): ShoppingListDao {
        return shoppingListDatabase.shoppingListDao()
    }

    @Provides
    @Singleton
    fun provideShoppingItemDao(shoppingListDatabase: ShoppingListDatabase): ShoppingItemDao {
        return shoppingListDatabase.shoppingItemDao()
    }
}
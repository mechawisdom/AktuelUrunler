package com.mechadev.indirim.aktuel.urunler.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mechadev.indirim.aktuel.urunler.util.Constants.RECENTLY_ADDED_TABLE_NAME

@Entity(tableName = RECENTLY_ADDED_TABLE_NAME)
data class RecentlyAddedModel(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("market")
    val market: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("image_path")
    val imagePath: String
)
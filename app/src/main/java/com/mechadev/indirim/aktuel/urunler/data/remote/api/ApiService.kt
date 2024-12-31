package com.mechadev.indirim.aktuel.urunler.data.remote.api

import com.mechadev.indirim.aktuel.urunler.model.RecentlyAddedModel
import retrofit2.http.GET

interface ApiService {
    @GET("recently.php")
    suspend fun getRecentlyAdded(): List<RecentlyAddedModel>

    /*

    @GET("recently.php")
    suspend fun getRecentlyAdded(): Response<List<RecentlyAddedListItem>>
     */
}
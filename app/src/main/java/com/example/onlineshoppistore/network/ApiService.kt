package com.example.onlineshoppistore.network

import com.example.onlineshoppistore.data.Product
import com.example.onlineshoppistore.data.ResponseItem
import com.example.onlineshoppistore.data.ResponseItems
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Defines network API interface for Retrofit to fetch character data from the Rick and Morty API
interface ApiService {

    @GET("products/{product_Id}")
    suspend fun getProduct(
        @Path("product_Id") productId: String,
        @Query("Apikey") apiKey:String,
        // @Query("product_id") productParam: String,
        @Query("organization_id") orgId: String,
        @Query("Appid") appId: String
    ): Response<Product>

    @GET("products")
    fun getProducts(
        @Query("Apikey") apiKey:String,
        @Query("organization_id") organizationId: String,
        @Query("Appid") appId: String
    ): Call<ResponseItems>
}
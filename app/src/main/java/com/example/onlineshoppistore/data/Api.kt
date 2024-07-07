package com.example.onlineshoppistore.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitAPI {
    @GET("products/{product_Id}")
    suspend fun getProduct(
        @Path("product_Id") productId: String,
        @Query("Apikey") apiKey:String,
       // @Query("product_id") productParam: String,
        @Query("organization_id") orgId: String,
        @Query("Appid") appId: String
    ): Response<ResponseItem>

    @GET("products")
    fun getProducts(
        @Query("Apikey") apiKey:String,
        @Query("organization_id") organizationId: String,
        @Query("Appid") appId: String
    ): Call<ResponseItems>
}

class ApiService {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.timbu.cloud/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()



    suspend fun getProduct(productId: String): ProductState {
       val apiService: RetrofitAPI = retrofit.create(RetrofitAPI::class.java)
        try {
            val response = apiService.getProduct(
                productId = productId,
                apiKey = "239bc677748e4455970bc3a4b17253c920240704215822365525",
                orgId = "97799f8a30e04f1eb0eb6a3819735af0",
                appId = "BPBDMKHZK5FPVRD"
            )

            return if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    Log.d("APIService","$data")
                    ProductState.Success(data)
                } else {
                    ProductState.Error("Empty response")
                }
            } else {
                ProductState.Error("Failed to fetch product data")
            }
        } catch (e: Exception) {
            return ProductState.Error("Network error: ${e.message}")
        }
    }
    suspend fun getProducts(): Flow<ProductsState> {
       val apiService: RetrofitAPI = retrofit.create(RetrofitAPI::class.java)
        val responseFlow: MutableStateFlow<ProductsState> = MutableStateFlow(ProductsState.Loading)
        val request: Call<ResponseItems> = apiService.getProducts(
            apiKey = "239bc677748e4455970bc3a4b17253c920240704215822365525",
            organizationId = "97799f8a30e04f1eb0eb6a3819735af0",
            appId = "BPBDMKHZK5FPVRD"
        )
        request.enqueue(object : Callback<ResponseItems> {
            override fun onResponse(p0: Call<ResponseItems>, p1: Response<ResponseItems>) {
                Log.d("Response", "${p1.body()}")

                val responseItems: ResponseItems? = p1.body()
                var items: List<ResponseItem> = responseItems?.items ?: mutableListOf()
              if (items.isNotEmpty())  {
                  responseFlow.value = ProductsState.Success(items)
              }
            }

            override fun onFailure(p0: Call<ResponseItems>, p1: Throwable) {
                Log.d("API", "$p1")
                responseFlow.value = ProductsState.Error("Network Error: Ensure your internet is stable")
            }

        })

        return responseFlow

    }
}


package com.example.onlineshoppistore.network

import android.util.Log
import com.example.onlineshoppistore.data.ProductState
import com.example.onlineshoppistore.data.ProductsState
import com.example.onlineshoppistore.data.ResponseItem
import com.example.onlineshoppistore.data.ResponseItems
import com.example.onlineshoppistore.data.RetrofitAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiRepository(private val apiService: ApiService) {
    suspend fun getProduct(productId: String): ProductState {
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
            return ProductState.Error("${e.message}")
        }
    }
    // Fetches characters from the API and emits the state accordingly

    fun getProducts(): Flow<DataState<List<ResponseItem>>> {
        val responseFlow: MutableStateFlow<DataState<List<ResponseItem>>> = MutableStateFlow(DataState.loading())
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
                    responseFlow.value = DataState.success(items)
                }
            }

            override fun onFailure(p0: Call<ResponseItems>, p1: Throwable) {
                Log.d("API", "$p1")
                responseFlow.value = DataState.error(error = p1, message = "check your network")
            }

        })

        return responseFlow

    }
}
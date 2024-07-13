package com.example.onlineshoppistore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppistore.data.ResponseItem
import com.example.onlineshoppistore.network.ApiRepository
import com.example.onlineshoppistore.network.DataState
import com.example.onlineshoppistore.network.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {

    // StateFlow mechanism to observe characters data state from the UI layer
    private val _products: MutableStateFlow<DataState<List<ResponseItem>>> =
        MutableStateFlow(DataState.loading(data = null))
    val products = _products.asStateFlow()

    init {
        fetchProducts() // Initial data fetch
    }

    // Handles fetching characters from the repository and updating UI state accordingly
    fun fetchProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_products.value.status == Status.SUCCESS) {
                // There is already data, update states consist of fetching while showing the old data
                // Use copy for updating the status
                _products.value = _products.value.copy(status = Status.UPDATING)
                // Freezes 1 second in case of updating state
                delay(1000)
            }
            apiRepository.getProducts().collect {
                _products.value = it
            }
        }
    }
}
package com.example.onlineshoppistore.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppistore.data.ApiService
import com.example.onlineshoppistore.data.Photo
import com.example.onlineshoppistore.data.ProductState
import com.example.onlineshoppistore.data.ResponseItem
import com.example.onlineshoppistore.network.ApiRepository
import com.example.onlineshoppistore.network.DataState
import com.example.onlineshoppistore.network.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val apiRepository: ApiRepository
) :
    ViewModel() {
    private val _products: MutableStateFlow<DataState<List<ResponseItem>>> =
        MutableStateFlow(DataState.loading(data = null))
    val products = _products.asStateFlow()

    private var productPrice: String = ""
    private var productId: String = ""
    private val productStateFlow = MutableStateFlow<Product>(Product())
    val productState: StateFlow<Product> = productStateFlow

    private val productLoadingFlow = MutableStateFlow(true)
    val productloading: StateFlow<Boolean> = productLoadingFlow

    private val retryStateFlow = MutableStateFlow(true)
    val retryState: StateFlow<Boolean> = retryStateFlow

    private val productErrorFlow = MutableStateFlow(ErrorState())
    val productError: StateFlow<ErrorState> = productErrorFlow

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    data class ErrorState(val state: Boolean = false, val message: String = "")

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()

        data object Loading : UiEvent()
        data object Success : UiEvent()

    }


    init {

        savedStateHandle.get<String>("id")?.let { id ->
            Log.d("ProductId", id)
            if (id.isNotEmpty()) {
                productId = id
                fetchProduct(id)
                //  fetchProducts()
                /*viewModelScope.launch {

            val result = api.getProducts()
            result.collect{

                when(it){
                    is ProductsState.Error -> {
_eventFlow.emit(UiEvent.ShowSnackbar(it.errorMessage))
                        productLoadingFlow.value = false
                        productErrorFlow.value = ErrorState(state = true, message = it.errorMessage)
                    }
                    ProductsState.Loading -> {
_eventFlow.emit(UiEvent.Loading)
                        productLoadingFlow.value = true
                        productErrorFlow.value = ErrorState()
                    }
                    is ProductsState.Success -> {
productsStateFlow.value = it.data
                        _eventFlow.emit(UiEvent.Success)
                        productLoadingFlow.value = false
                        productErrorFlow.value = ErrorState()
                    }
                }
            }

        }*/
            }
        }
        savedStateHandle.get<String>("price").let { price ->
            if (price != null) {
                productPrice = price
            }
        }
fetchProducts()

    }

    fun fetchProduct(id: String = productId) {
        viewModelScope.launch {
            val result = apiRepository.getProduct(id/*"776a77353cd341db8cf8d07144b63b19"*/)
            Log.d("ViewModel", "${apiRepository.getProduct("776a77353cd341db8cf8d07144b63b19")}")

            when (result) {
                is ProductState.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar(result.errorMessage))
                    productLoadingFlow.value = false
                    productErrorFlow.value = ErrorState(state = true, message = result.errorMessage)
                }

                ProductState.Loading -> {
                    _eventFlow.emit(UiEvent.Loading)
                    productLoadingFlow.value = true
                    productErrorFlow.value = ErrorState()
                }

                is ProductState.Success -> {
                    productStateFlow.value = Product(

                        name = result.data.name, price = result.data.currentPrice.toString(),
                        description = result.data.description,
                        imageUrl = result.data.photos
                    )
                    _eventFlow.emit(UiEvent.Success)
                    productLoadingFlow.value = false
                    productErrorFlow.value = ErrorState()
                }
            }

        }
    }
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

    /*  fun fetchProducts() {

    viewModelScope.launch {

        val result = api.getProducts()
        result.collect{

            when(it){
                is ProductsState.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar(it.errorMessage))
                    productLoadingFlow.value = false
                    productErrorFlow.value = ErrorState(state = true, message = it.errorMessage)
                }
                ProductsState.Loading -> {
                    _eventFlow.emit(UiEvent.Loading)
                    productLoadingFlow.value = true
                    productErrorFlow.value = ErrorState()
                }
                is ProductsState.Success -> {
                    productsStateFlow.value = it.data
                    _eventFlow.emit(UiEvent.Success)
                    productLoadingFlow.value = false
                    productErrorFlow.value = ErrorState()
                }
            }
        }

    }
}*/
    data class Product(
        val name: String = "",
        val price: String = "",
        val description: String = "",
        val imageUrl: List<Photo> = emptyList(),

        )
}
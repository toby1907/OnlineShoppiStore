package com.example.onlineshoppistore.ui.detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppistore.data.Photo
import com.example.onlineshoppistore.data.ProductState
import com.example.onlineshoppistore.data.ResponseItem
import com.example.onlineshoppistore.data.datastore.FavoriteDataStore
import com.example.onlineshoppistore.data.datastore.FavoriteItem
import com.example.onlineshoppistore.data.room.CartItem
import com.example.onlineshoppistore.data.room.CartRepositoryImpl
import com.example.onlineshoppistore.data.room.InvalidGoalException
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
import kotlin.time.times

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val apiRepository: ApiRepository,
    private val cartRepository: CartRepositoryImpl,
    private val favoriteDataStore: FavoriteDataStore
) : ViewModel() {
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

    private val _favoriteItems:MutableStateFlow<List<FavoriteItem>>  = MutableStateFlow(emptyList())
    val favoriteItem = _favoriteItems.asStateFlow()

    private val _nameState = mutableStateOf(DetailScreenState())
    private val _descriptionState = mutableStateOf(DetailScreenState())
    private val _isAddToCart = mutableStateOf(DetailScreenState())
    private val _imageUrl = mutableStateOf(DetailScreenState())
    private val _quantity = mutableStateOf(DetailScreenState())
    private val _colorState = mutableStateOf(DetailScreenState())
    private val _sizeState = mutableStateOf(DetailScreenState())

    val quantityState: State<DetailScreenState> = _quantity
    val colorState:  State<DetailScreenState> = _colorState
    val sizeState:  State<DetailScreenState> = _sizeState




    data class ErrorState(val state: Boolean = false, val message: String = "")

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data object SavedCartItem  : UiEvent()

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
                        imageUrl = result.data.photos,
                        id = result.data.id
                    )

                    _nameState.value = _nameState.value.copy(name = result.data.name)
                    _imageUrl.value = _imageUrl.value.copy(imageUrl = result.data.photos[0].url)
                    _descriptionState.value = _descriptionState.value.copy( description = result.data.description)
                    _isAddToCart.value = _isAddToCart.value.copy(isAddedToCart = true)

                    _eventFlow.emit(UiEvent.Success)
                    productLoadingFlow.value = false
                    productErrorFlow.value = ErrorState()
                }
            }

        }
    }
    fun onEvent(event: DetailScreenEvent) {

        when (event) {
            is DetailScreenEvent.ChangeColor -> {
                _colorState.value = _colorState.value.copy(
                    color = event.color
                )
            }
            is DetailScreenEvent.ChangeFavorite -> {

            }
            is DetailScreenEvent.EnteredQuatity -> {
                _quantity.value = _quantity.value.copy(
                    quantity = event.value
                )
            }
            is DetailScreenEvent.EnteredSize -> {
                _sizeState.value = _sizeState.value.copy(
                    size = event.value
                )
            }
            DetailScreenEvent.SaveProduct -> {
                viewModelScope.launch {
                    try {

                       val cartItem = CartItem(
                           id = productId,
                           name =_nameState.value.name,
                           description = _descriptionState.value.description,
                           imageUrl = _imageUrl.value.imageUrl,
                           isAddedToCart = _isAddToCart.value.isAddedToCart,
                           color = colorState.value.color,
                           isOrderedCompleted = false,
                           price = productPrice,
                           quantity = quantityState.value.quantity,
                           size = sizeState.value.size,
                           itemTotalPrice = quantityState.value.quantity.toDouble() * productPrice.toDouble()
                           )
                        cartRepository.insertCartItem(cartItem)

                        _eventFlow.emit(UiEvent.SavedCartItem)
                        _eventFlow.emit(UiEvent.ShowSnackbar("Item saved!"))
                    } catch (e: InvalidGoalException) {

                        Log.d("CartItem", "could not save")
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save item"
                            )
                        )

                    }
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
        val id:String = "",
        )

    fun isProductFavorite(uniqueId: String): StateFlow<Boolean> {
        val isFavoriteFlow = MutableStateFlow(false)

        viewModelScope.launch {
            //    val favoriteItems = getFavoriteItems() // Replace with the actual function call
            favoriteDataStore.getFavoriteItemsFlow().collect{
               // _favoriteItems.value = it
                isFavoriteFlow.value = it.contains(FavoriteItem(uniqueId))
            }

        }

        return isFavoriteFlow
    }
    fun onChangeFavourite(change:Boolean,productId:String){

        viewModelScope.launch {
            if (change==true){
                favoriteDataStore.addFavorite(FavoriteItem(productId))
            }
            if (change==false){
                favoriteDataStore.removeFavorite(FavoriteItem(productId))
            }


        }


    }

}
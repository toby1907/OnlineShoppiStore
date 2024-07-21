package com.example.onlineshoppistore.ui.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppistore.data.ResponseItem
import com.example.onlineshoppistore.data.datastore.FavoriteDataStore
import com.example.onlineshoppistore.data.datastore.FavoriteItem
import com.example.onlineshoppistore.network.ApiRepository
import com.example.onlineshoppistore.network.DataState
import com.example.onlineshoppistore.network.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(private val apiRepository: ApiRepository,
                                            private val favoriteDataStore: FavoriteDataStore
) : ViewModel() {

    // StateFlow mechanism to observe characters data state from the UI layer
    private val _products: MutableStateFlow<DataState<List<ResponseItem>>> =
        MutableStateFlow(DataState.loading(data = null))
    val products = _products.asStateFlow()
    private val _favoriteItems: MutableStateFlow<List<FavoriteItem>> = MutableStateFlow(emptyList())
    val favoriteItem = _favoriteItems.asStateFlow()





    init {
        fetchProducts() // Initial data fetch
        getFavoriteItems()
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

    fun isProductFavorite(uniqueId: String): StateFlow<Boolean> {
        val isFavoriteFlow = MutableStateFlow(false)

        viewModelScope.launch {
            //    val favoriteItems = getFavoriteItems() // Replace with the actual function call
            favoriteDataStore.getFavoriteItemsFlow().collect{
                //   _favoriteItems.value = it
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
fun getFavoriteItems(){
    viewModelScope.launch {
        //    val favoriteItems = getFavoriteItems() // Replace with the actual function call
        favoriteDataStore.getFavoriteItemsFlow().collect{
              _favoriteItems.value = it
        //    isFavoriteFlow.value = it.contains(FavoriteItem(uniqueId))

        }

    }


}


}
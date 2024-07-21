package com.example.onlineshoppistore.ui.order

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppistore.data.datastore.FavoriteDataStore
import com.example.onlineshoppistore.data.room.CartItem
import com.example.onlineshoppistore.data.room.CartRepositoryImpl
import com.example.onlineshoppistore.network.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyOrdersViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val apiRepository: ApiRepository,
    private val cartRepository: CartRepositoryImpl,
    private val favoriteDataStore: FavoriteDataStore
) : ViewModel() {

    private val cartItemsFlow: MutableStateFlow<List<CartItem>> = MutableStateFlow(emptyList())
    val cartItems: StateFlow<List<CartItem>> = cartItemsFlow


    init {
        viewModelScope.launch {
            cartItemsFlow.value = cartRepository.getAllCartItems().filter { it.isOrderedCompleted }
        }
    }
    fun deleteItem( itemId:String){
        viewModelScope.launch {
            cartRepository.deleteCartItemById(itemId)
            cartItemsFlow.value = cartRepository.getAllCartItems()
        }

    }
    fun updateItem (cartItem: CartItem){
        viewModelScope.launch {
            cartRepository.updateCartItem(cartItem = cartItem)
            cartItemsFlow.value = cartRepository.getAllCartItems()
        }
    }

    fun markAllItemsAsOrderedCompleted(cartItems: List<CartItem>): List<CartItem> {
        return cartItemsFlow.value.map { it.copy(isOrderedCompleted = true) }
    }

    fun saveUpdatedCartItems() {
        viewModelScope.launch {
            val updatedValues = cartItemsFlow.value.map { it.copy(isOrderedCompleted = true)}

            cartRepository.updateCartItems(updatedValues)
        }
    }
}
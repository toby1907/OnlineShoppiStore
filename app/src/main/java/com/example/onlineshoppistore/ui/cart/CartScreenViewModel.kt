package com.example.onlineshoppistore.ui.cart

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
class CartScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val apiRepository: ApiRepository,
    private val cartRepository: CartRepositoryImpl,
    private val favoriteDataStore: FavoriteDataStore
) : ViewModel() {

    private val cartItemsFlow: MutableStateFlow<List<CartItem>> = MutableStateFlow(emptyList())
    val cartItems: StateFlow<List<CartItem>> = cartItemsFlow

    init {
        viewModelScope.launch {
           cartItemsFlow.value = cartRepository.getAllCartItems().filter {
               it.isAddedToCart
           }
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
    // Function to update the quantity of a cart item
    fun updateCartItemQuantity(itemId: String, newQuantity: Int) {
        viewModelScope.launch {
            // Fetch the existing cart item from the database
            val existingItem = cartRepository.getCartItemById(itemId)

            // Update the quantity
            val updatedItem = existingItem?.copy(quantity = newQuantity)

            // Save the updated item back to the database
            if (updatedItem != null) {
                cartRepository.updateCartItem(updatedItem)
            }
            cartItemsFlow.value = cartRepository.getAllCartItems().filter {
                it.isAddedToCart
            }
        }
    }
}
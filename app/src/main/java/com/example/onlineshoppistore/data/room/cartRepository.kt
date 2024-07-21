package com.example.onlineshoppistore.data.room

interface CartRepository {
    suspend fun getAllCartItems(): List<CartItem>
    suspend fun getCartItemById(itemId: String): CartItem?
    suspend fun updateCartItems(cartItems: List<CartItem>)
    suspend fun deleteCartItemById(itemId: String)
    suspend fun insertCartItem(cartItem: CartItem)
    suspend fun updateCartItem(cartItem: CartItem)
}
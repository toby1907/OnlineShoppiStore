package com.example.onlineshoppistore.data.room

class CartRepositoryImpl(private val cartDao: CartDao) : CartRepository {
    override suspend fun getAllCartItems(): List<CartItem> {
        return cartDao.getAllCartItems()
    }

    override suspend fun getCartItemById(itemId: String): CartItem? {
        return cartDao.getCartItemById(itemId)
    }

    override suspend fun updateCartItems(cartItems: List<CartItem>) {
        cartDao.updateCartItems(cartItems)
    }

    override suspend fun deleteCartItemById(itemId: String) {
        cartDao.deleteCartItemById(itemId)
    }

    override suspend fun insertCartItem(cartItem: CartItem) {
        cartDao.insertCartItem(cartItem)
    }

    override suspend fun updateCartItem(cartItem: CartItem) {
        cartDao.insertCartItem(cartItem)
    }
}
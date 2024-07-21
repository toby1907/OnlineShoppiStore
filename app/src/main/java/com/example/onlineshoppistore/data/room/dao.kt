package com.example.onlineshoppistore.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)

    @Query("SELECT * FROM cart_items")
    suspend fun getAllCartItems(): List<CartItem>
    @Query("SELECT * FROM cart_items WHERE id = :itemId")
    suspend fun getCartItemById(itemId: String): CartItem?
    @Update
    suspend fun updateCartItems(vararg cartItems: CartItem)
    @Query("DELETE FROM cart_items WHERE id = :itemId")
    suspend fun deleteCartItemById(itemId: String)

    @Update
    suspend fun updateCartItems(cartItems: List<CartItem>)
    @Update
    suspend fun updateCartItem(cartItems: CartItem)
}
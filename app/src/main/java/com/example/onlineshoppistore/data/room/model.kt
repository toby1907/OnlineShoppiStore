package com.example.onlineshoppistore.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val isAddedToCart: Boolean,
    val quantity: Int,
    val color: Int,
    val isOrderedCompleted: Boolean,
    val size : Int,
    val price : String,
    val itemTotalPrice : Double
)
class InvalidGoalException(message: String) : Exception(message)
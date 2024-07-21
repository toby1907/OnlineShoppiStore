package com.example.onlineshoppistore.ui.detail

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.PrimaryKey

sealed class DetailScreenEvent {
    data class EnteredSize(val value: Int): DetailScreenEvent()
    data class EnteredQuatity(val value: Int): DetailScreenEvent()
    data class ChangeColor(val color: Int) : DetailScreenEvent()
    data class ChangeFavorite(val change:Boolean,val productId:String) : DetailScreenEvent()
    object SaveProduct: DetailScreenEvent()
}


val intColor = Color(0xFF2A2A2A).toArgb()
val colorObject = Color(intColor)
data class DetailScreenState(
    val name: String = "",
    val userId: Int = 0,
    val description: String = "",
    val imageUrl: String = "",
    val isAddedToCart: Boolean = false,
    val quantity: Int = 1,
    val color: Int = Color(0xFFFFA500).toArgb(),
    val size: Int = 40,
    val price: Double = 0.0,
)
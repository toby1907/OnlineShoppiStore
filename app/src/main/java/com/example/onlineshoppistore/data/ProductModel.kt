package com.example.onlineshoppistore.data

import com.google.gson.annotations.SerializedName

data class Product(
    val name: String,
    val description: String,
    @SerializedName("unique_id") val uniqueId: String,
    @SerializedName("url_slug") val urlSlug: String,
    @SerializedName("is_available") val isAvailable: Boolean,
    @SerializedName("is_service") val isService: Boolean,
    val id: String,
    @SerializedName("organization_id") val organizationId: String,
    @SerializedName("product_image") val productImage: List<String>,
    @SerializedName("date_created") val dateCreated: String,
    @SerializedName("last_updated") val lastUpdated: String,
    @SerializedName("user_id") val userId: String,
    val photos: List<Photo>,
    @SerializedName("current_price") val currentPrice: Double,
    @SerializedName("is_deleted") val isDeleted: Boolean,
    @SerializedName("available_quantity") val availableQuantity: Double,
    val sellingPrice: Double?,
    val discountedPrice: Double?,
    val buyingPrice: Double?,
    val extraInfos: Map<String, Any>?
)
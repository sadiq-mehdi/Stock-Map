package com.example.stockmap.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id")
    val supabaseId: Long,
    val name: String,
    val sku: String,
    val barcode: String,
    val category: String,
    @SerializedName("current_stock")
    val currentStock: Int,
    @SerializedName("minimum_stock")
    val minimumStock: Int,
)
package com.example.stockmap.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UpdateStockDto(
    @SerializedName("current_stock")
    val currentStock: Int
)
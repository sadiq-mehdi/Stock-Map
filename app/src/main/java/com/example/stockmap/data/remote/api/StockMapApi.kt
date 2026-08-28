package com.example.stockmap.data.remote.api

import com.example.stockmap.data.remote.dto.ProductDto
import com.example.stockmap.data.remote.dto.UpdateStockDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query


interface StockMapApi {
    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    @PATCH("products")
    suspend fun updateStock(
        @Query("id") id: String,
        @Body body: UpdateStockDto
    )
}
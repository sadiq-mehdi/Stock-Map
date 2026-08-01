package com.example.stockmap.data.remote.api

import com.example.stockmap.data.remote.dto.ProductDto
import retrofit2.http.GET


interface StockMapApi {
    @GET("rest/v1/products")
    suspend fun getProducts(): List<ProductDto>
}
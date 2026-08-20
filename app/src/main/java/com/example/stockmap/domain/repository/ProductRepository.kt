package com.example.stockmap.domain.repository

import com.example.stockmap.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun syncProducts(): Result<Unit>

    fun getFilteredProducts(searchQuery: String, category: String?): Flow<List<Product>>

    suspend fun getProductByBarcode(barcode: String): Product?

    suspend fun updateBinAssignment(supabaseId: Long, binId: Int?): Result<Unit>

    suspend fun updateStock(supabaseId: Long, newStock: Int): Result<Unit>

    suspend fun getProductById(id: Int): Product?

    suspend fun getOccupiedBinIds(): List<Int>

}
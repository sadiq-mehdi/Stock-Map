package com.example.stockmap.data.repository

import com.example.stockmap.data.local.dao.ProductDao
import com.example.stockmap.data.local.entity.toDomain
import com.example.stockmap.data.local.entity.toEntity
import com.example.stockmap.data.remote.api.StockMapApi
import com.example.stockmap.data.remote.dto.UpdateStockDto
import com.example.stockmap.domain.model.Product
import com.example.stockmap.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val stockMapApi: StockMapApi,
    private val productDao: ProductDao
) : ProductRepository {
    override suspend fun syncProducts(): Result<Unit> {
        return try {

            val productEntities = stockMapApi.getProducts().map { it.toEntity() }

            productDao.insertProducts(productEntities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getFilteredProducts(
        searchQuery: String,
        category: String?
    ): Flow<List<Product>> {
        return productDao.getFilteredProducts(searchQuery, category)
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun getProductByBarcode(barcode: String): Product? {
        return productDao.getProductByBarcode(barcode = barcode)?.toDomain()
    }

    override suspend fun updateBinAssignment(
        supabaseId: Long,
        binId: Int?
    ): Result<Unit> {
        return try {
            val product = productDao.findIdBySupabaseId(supabaseId)
            if (product != null){
                productDao.binAssign(product.id, binId)
                Result.success(Unit)
            }
            else{
                Result.failure(Exception("Product not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateStock(
        supabaseId: Long,
        newStock: Int
    ): Result<Unit> {
        return try {
            val product = productDao.findIdBySupabaseId(supabaseId)
            if (product != null) {
                stockMapApi.updateStock("eq.$supabaseId", UpdateStockDto(newStock))

                productDao.updateStock(
                    product.id,
                    newStock,
                    lastUpdated = System.currentTimeMillis()
                )
                Result.success(Unit)
            } else {
                Result.failure(Exception("Product not found"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProductById(id: Int): Product? {
        return productDao.getProductById(id)?.toDomain()
    }

    override suspend fun getOccupiedBinIds(): List<Int> {
        return productDao.getOccupiedBinIds().filterNotNull()
    }

    override fun getCategories(): Flow<List<String>> {
        return productDao.getCategories()
    }

    override fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts().map { entity -> entity.map { it.toDomain() } }
    }


}
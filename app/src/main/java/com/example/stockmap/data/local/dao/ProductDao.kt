package com.example.stockmap.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stockmap.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM ProductEntity WHERE barcode = :barcode")
    suspend fun getProductByBarcode(barcode: String): ProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("Update ProductEntity SET currentStock = :currentStock, lastUpdated = :lastUpdated WHERE id = :id")
    suspend fun updateStock(id: Int, currentStock: Int, lastUpdated: Long)

    @Query("Update ProductEntity SET binId = :binId WHERE id = :id")
    suspend fun binAssign(id: Int, binId: Int?)

    @Query("SELECT * FROM ProductEntity WHERE (name LIKE '%' || :searchQuery || '%') AND (:category IS NULL OR category = :category OR (:category = 'unassigned' AND binId IS NULL))")
    fun getFilteredProducts(searchQuery: String, category: String?): Flow<List<ProductEntity>>

    @Query("SELECT * FROM ProductEntity WHERE supabaseId = :supabaseId")
    suspend fun findIdBySupabaseId(supabaseId: Long?): ProductEntity?

    @Query("SELECT * FROM ProductEntity WHERE id = :id")
    suspend fun getProductById(id: Int): ProductEntity?

    @Query("SELECT binId FROM ProductEntity WHERE binId IS NOT NULL")
    suspend fun getOccupiedBinIds(): List<Int?>

    @Query("SELECT DISTINCT category FROM ProductEntity")
    fun getCategories(): Flow<List<String>>

    @Query("SELECT * FROM ProductEntity")
    fun getAllProducts(): Flow<List<ProductEntity>>

}
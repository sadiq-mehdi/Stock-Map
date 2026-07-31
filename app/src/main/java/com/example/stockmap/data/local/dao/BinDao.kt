package com.example.stockmap.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.stockmap.data.local.entity.BinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BinDao {

    @Insert
    suspend fun insertBins(bins: List<BinEntity>)

    @Query("SELECT * FROM BinEntity")
    fun getAllBins(): Flow<List<BinEntity>>

    @Query("DELETE FROM BinEntity")
    suspend fun deleteBins()

}
package com.example.stockmap.domain.repository

import com.example.stockmap.domain.model.Bin
import kotlinx.coroutines.flow.Flow

interface BinRepository {

    suspend fun insertBins(bins: List<Bin>)

    suspend fun deleteBins()

    fun getAllBins(): Flow<List<Bin>>

}
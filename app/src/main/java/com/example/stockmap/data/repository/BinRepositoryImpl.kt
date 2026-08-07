package com.example.stockmap.data.repository

import com.example.stockmap.data.local.dao.BinDao
import com.example.stockmap.data.local.entity.toDomain
import com.example.stockmap.data.local.entity.toEntity
import com.example.stockmap.domain.model.Bin
import com.example.stockmap.domain.repository.BinRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BinRepositoryImpl @Inject constructor(private val binDao: BinDao): BinRepository {
    override suspend fun insertBins(bins: List<Bin>) {
        binDao.insertBins(bins.map { it.toEntity() })
    }

    override suspend fun deleteBins() {
        binDao.deleteBins()
    }

    override fun getAllBins(): Flow<List<Bin>> {

        return binDao.getAllBins().map { entities -> entities.map { it.toDomain() } }
    }
}
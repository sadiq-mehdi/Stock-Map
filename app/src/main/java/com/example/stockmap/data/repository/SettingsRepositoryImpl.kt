package com.example.stockmap.data.repository

import com.example.stockmap.data.local.datastore.WarehousePreferences
import com.example.stockmap.domain.repository.SettingsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl @Inject constructor(private val warehousePreferences: WarehousePreferences) : SettingsRepository {
    override fun getWarehouseName(): Flow<String> {
        return warehousePreferences.name
    }
}
package com.example.stockmap.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getWarehouseName(): Flow<String>
}
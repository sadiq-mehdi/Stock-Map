package com.example.stockmap.domain.model

data class Product(
    val supabaseId: Long,
    val id: Int = 0,
    val name: String,
    val sku: String,
    val barcode: String,
    val category: String,
    val currentStock: Int,
    val minimumStock: Int,
    val lastUpdated: Long,
    val binId: Int?
)
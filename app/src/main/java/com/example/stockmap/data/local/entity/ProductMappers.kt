package com.example.stockmap.data.local.entity

import com.example.stockmap.data.remote.dto.ProductDto
import com.example.stockmap.domain.model.Product

fun ProductDto.toEntity(): ProductEntity{
    return ProductEntity(
        supabaseId = supabaseId,
        name = name,
        category = category,
        sku = sku,
        minimumStock = minimumStock,
        currentStock = currentStock,
        barcode = barcode,
        id = 0,
        binId = null,
        lastUpdated = System.currentTimeMillis()
    )
}

fun ProductEntity.toDomain(): Product{
    return Product(
        supabaseId = supabaseId,
        name = name,
        category = category,
        sku = sku,
        minimumStock = minimumStock,
        currentStock = currentStock,
        barcode = barcode,
        id = id,
        binId = binId,
        lastUpdated = lastUpdated
    )
}
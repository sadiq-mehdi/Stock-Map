package com.example.stockmap.data.local.entity

import com.example.stockmap.domain.model.Bin

fun Bin.toEntity(): BinEntity{
    return BinEntity(
        id = id,
        row = row,
        shelf = shelf,
        binNumber = binNumber,
        label = label
    )
}

fun BinEntity.toDomain(): Bin{
    return Bin(
        id = id,
        row = row,
        shelf = shelf,
        binNumber = binNumber,
        label = label
    )
}
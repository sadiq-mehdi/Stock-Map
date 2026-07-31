package com.example.stockmap.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["label"], unique = true)])
data class BinEntity(
    val row: String,
    val shelf: Int,
    val binNumber: Int,
    val label: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
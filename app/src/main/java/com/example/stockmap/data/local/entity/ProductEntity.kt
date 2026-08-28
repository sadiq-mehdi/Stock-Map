package com.example.stockmap.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = BinEntity::class,
            parentColumns = ["id"],
            childColumns = ["binId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index(value = ["supabaseId"], unique = true), Index(
        value = ["binId"],
        unique = true
    )]
)
data class ProductEntity(
    val supabaseId: Long,
    val name: String,
    val sku: String,
    val barcode: String,
    val category: String,
    val currentStock: Int,
    val minimumStock: Int,
    val lastUpdated: Long,
    val binId: Int?,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0

)
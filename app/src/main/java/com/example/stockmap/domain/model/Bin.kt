package com.example.stockmap.domain.model

data class Bin(
    val id: Int = 0,
    val row: String,
    val shelf: Int,
    val binNumber: Int,
    val label: String
)
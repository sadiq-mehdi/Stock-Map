package com.example.stockmap.ui.binmap

import com.example.stockmap.domain.model.Bin
import com.example.stockmap.domain.model.Product

data class BinMapUiState(
    val bins: List<Bin> = emptyList(),
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
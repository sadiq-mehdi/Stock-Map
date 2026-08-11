package com.example.stockmap.ui.stocklist

import com.example.stockmap.domain.model.Product

data class StockListUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
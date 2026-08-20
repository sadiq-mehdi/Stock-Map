package com.example.stockmap.ui.productdetail

import com.example.stockmap.domain.model.Bin
import com.example.stockmap.domain.model.Product

data class ProductDetailUiState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isDialog: Boolean = false,
    val isBottomSheet: Boolean = false,
    val bins: List<Bin> = emptyList(),
    val occupiedBinIds: List<Int> = emptyList()
)
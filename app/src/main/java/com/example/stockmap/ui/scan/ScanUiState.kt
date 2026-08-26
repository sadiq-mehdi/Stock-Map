package com.example.stockmap.ui.scan

import com.example.stockmap.domain.model.Product

data class ScanUiState(
    val foundProduct: Product? = null,
    val error: String? = null
)
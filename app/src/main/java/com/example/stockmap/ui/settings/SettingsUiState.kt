package com.example.stockmap.ui.settings

data class SettingsUiState(
    val warehouseName: String = "",
    val rows: Int = 0,
    val shelves: Int = 0,
    val bins: Int = 0,
)
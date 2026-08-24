package com.example.stockmap.ui.binmap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmap.domain.repository.BinRepository
import com.example.stockmap.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BinMapViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val binRepository: BinRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BinMapUiState())
    val state: StateFlow<BinMapUiState> = _state

    init {
        viewModelScope.launch {
            productRepository.getAllProducts().collect { products ->
                _state.value = _state.value.copy(products = products)
            }
        }
        viewModelScope.launch {
            binRepository.getAllBins().collect { bins ->
                _state.value = _state.value.copy(bins = bins)
            }
        }
    }
}
package com.example.stockmap.ui.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmap.domain.model.Product
import com.example.stockmap.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(ScanUiState())
    val state: StateFlow<ScanUiState> = _state

    fun findProductByBarcode(barcode: String) {

        viewModelScope.launch {
            val product = productRepository.getProductByBarcode(barcode)
            if (product == null) {
                _state.value = _state.value.copy(error = "Product Not Found")
            } else {
                _state.value = _state.value.copy(foundProduct = product)
            }
        }

    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }

    fun clearFoundProduct() {
        _state.value = _state.value.copy(foundProduct = null)
    }

}
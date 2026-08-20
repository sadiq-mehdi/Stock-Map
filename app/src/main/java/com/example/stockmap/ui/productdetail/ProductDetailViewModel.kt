package com.example.stockmap.ui.productdetail

import androidx.lifecycle.SavedStateHandle
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
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val binRepository: BinRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _productDetailUiState = MutableStateFlow(ProductDetailUiState())
    val productDetailUiState: StateFlow<ProductDetailUiState> = _productDetailUiState

    private val productId: Int = checkNotNull(savedStateHandle["productId"])

    init {
        viewModelScope.launch {
            val product = productRepository.getProductById(productId)
            val bins = binRepository.getAllBins()
            _productDetailUiState.value = _productDetailUiState.value.copy(
                product = product,
                bins = bins.first()
            )
            val occupiedBinIds = productRepository.getOccupiedBinIds()
            _productDetailUiState.value = _productDetailUiState.value.copy(occupiedBinIds = occupiedBinIds)
        }
    }

    fun onAdjustStockClick() {
        _productDetailUiState.value = _productDetailUiState.value.copy(isDialog = true)
    }

    fun onAssignBinClick() {
        _productDetailUiState.value = _productDetailUiState.value.copy(isBottomSheet = true)
    }

    fun onDismissDialog() {
        _productDetailUiState.value = _productDetailUiState.value.copy(isDialog = false)
    }

    fun onDismissBottomSheet() {
        _productDetailUiState.value = _productDetailUiState.value.copy(isBottomSheet = false)
    }

    fun clearError(){
        _productDetailUiState.value = _productDetailUiState.value.copy(error = null)
    }

    fun adjustStock(newStock: Int) {

        viewModelScope.launch {
            _productDetailUiState.value = _productDetailUiState.value.copy(isLoading = true)
            val product = productDetailUiState.value.product
            val result = productRepository.updateStock(
                supabaseId = product?.supabaseId ?: return@launch,
                newStock = newStock
            )

            result.onSuccess {
                _productDetailUiState.value = _productDetailUiState.value.copy(
                    isLoading = false,
                    product = product.copy(currentStock = newStock)
                )
            }

            result.onFailure { error ->
                _productDetailUiState.value = _productDetailUiState.value.copy(isLoading = false)
                _productDetailUiState.value =
                    _productDetailUiState.value.copy(error = error.message)
            }
        }

    }

    fun assignBin(binId: Int?) {
        viewModelScope.launch {

            _productDetailUiState.value = _productDetailUiState.value.copy(isLoading = true)
            val product = productDetailUiState.value.product
            val result = productRepository.updateBinAssignment(
                supabaseId = product?.supabaseId ?: return@launch, binId = binId
            )
            result.onSuccess {
                _productDetailUiState.value = _productDetailUiState.value.copy(
                    isLoading = false,
                    product = product.copy(binId = binId)
                )

            }

            result.onFailure { error ->
                _productDetailUiState.value = _productDetailUiState.value.copy(isLoading = false)
                _productDetailUiState.value =
                    _productDetailUiState.value.copy(error = error.message)
            }

        }
    }

}

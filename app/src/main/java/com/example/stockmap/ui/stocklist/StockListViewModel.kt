package com.example.stockmap.ui.stocklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmap.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class StockListViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _category: MutableStateFlow<String?> = MutableStateFlow(null)
    val category: StateFlow<String?> = _category

    private val _uiState = MutableStateFlow(StockListUiState())
    val uiState: StateFlow<StockListUiState> = _uiState

    init {
        viewModelScope.launch {
            combine(_searchQuery, _category) { search, cat ->
                search to cat
            }.flatMapLatest { (search, cat) ->
                productRepository.getFilteredProducts(search, cat)
            }.collect { products ->
                _uiState.value = _uiState.value.copy(products = products)
            }
        }

        viewModelScope.launch {
            productRepository.getCategories().collect { categories ->
                _uiState.value = _uiState.value.copy(allCategories = categories)
            }
        }
    }

    fun syncProducts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = productRepository.syncProducts()
            result.onSuccess {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
            result.onFailure { error ->
                _uiState.value = _uiState.value.copy(isLoading = false)
                _uiState.value = _uiState.value.copy(error = error.message)
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onCategoryChange(category: String?) {
        _category.value = category
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

}
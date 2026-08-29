package com.example.stockmap.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmap.data.local.datastore.WarehousePreferences
import com.example.stockmap.domain.model.Bin
import com.example.stockmap.domain.repository.BinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val binRepository: BinRepository,
    private val warehousePreferences: WarehousePreferences
) :
    ViewModel() {

    private val _state = MutableStateFlow(SettingsUiState())
    val state: StateFlow<SettingsUiState> = _state

    init {
        viewModelScope.launch {
            combine(
                warehousePreferences.name,
                warehousePreferences.rows,
                warehousePreferences.shelves,
                warehousePreferences.bins
            ) { name, rows, shelves, bins ->
                SettingsUiState(warehouseName = name, rows = rows, shelves = shelves, bins = bins)
            }.collect { newState ->
                _state.value = newState
            }
        }
    }

    fun saveWarehouseName(name: String){
        viewModelScope.launch {
            warehousePreferences.addName(name)
        }
    }

    fun saveLayout(rows: Int, shelves: Int, bins: Int){
        if (rows <= 0 || shelves <= 0 || bins <= 0) return
        viewModelScope.launch {
            binRepository.deleteBins()
            val generatedBins = mutableListOf<Bin>()

            for (rowIndex in 0 until rows) {
                val rowLabel = ('A' + rowIndex).toString()
                for (shelf in 1..shelves) {
                    for (binNumber in 1..bins) {
                        generatedBins.add(
                            Bin(
                                row = rowLabel,
                                shelf = shelf,
                                binNumber = binNumber,
                                label = "$rowLabel-$shelf-$binNumber"
                            )
                        )
                    }
                }
            }
            binRepository.insertBins(generatedBins)
            warehousePreferences.addRows(rows)
            warehousePreferences.addShelves(shelves)
            warehousePreferences.addBins(bins)
        }

    }

}
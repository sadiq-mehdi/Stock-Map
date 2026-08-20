package com.example.stockmap.ui.productdetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stockmap.domain.model.Bin
import com.example.stockmap.ui.components.BottomNavBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {

    val state by viewModel.productDetailUiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(state.product?.name ?: "No Product Found")
                },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                    }
                }

            )
        },
        bottomBar = {
            BottomNavBar()
        }, snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            InfoRow(label = "Name", value = state.product?.name ?: "Not Found")
            InfoRow(label = "Category", value = state.product?.category ?: "Not Found")
            InfoRow(label = "SKU", value = state.product?.sku ?: "-")
            InfoRow(label = "Barcode", value = state.product?.barcode ?: "Not Found")

            HorizontalDivider()

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text("Current Stock", style = MaterialTheme.typography.labelSmall)
                    Text(
                        "${state.product?.currentStock}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Button(onClick = { viewModel.onAdjustStockClick() }) { Text("Adjust Stock") }
            }



            HorizontalDivider()

            Text("Minimum Stock", style = MaterialTheme.typography.labelSmall)
            Text("${state.product?.minimumStock}", style = MaterialTheme.typography.bodyLarge)

            HorizontalDivider()

            InfoRow(
                label = "Bin Label",
                value = state.bins.find { it.id == state.product?.binId }?.label ?: "Unassigned"
            )
            Button(onClick = { viewModel.onAssignBinClick() }) { Text("Assign Bin") }
        }
        if (state.isDialog) {
            AdjustStockDialog(
                onConfirm = { newStock -> viewModel.adjustStock(newStock) },
                onDismiss = { viewModel.onDismissDialog() }
            )
        }
        if (state.isBottomSheet){
            BinAssignmentBottomSheet(
                bins = state.bins,
                occupiedBinIds = state.occupiedBinIds,
                onBinSelected = { viewModel.assignBin(it)},
                onDismiss = { viewModel.onDismissBottomSheet() }
            )
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.labelSmall)
        Text(value, style = MaterialTheme.typography.bodyLarge)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdjustStockDialog(onConfirm: (Int) -> Unit, onDismiss: () -> Unit) {

    var newStock by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Adjust Stock") },
        text = {
            OutlinedTextField(
                value = newStock,
                onValueChange = { newStock = it },
                placeholder = { Text("New Stock") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(newStock.toIntOrNull() ?: 0)
            }) { Text("Confirm") }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) { Text("Cancel") }
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BinAssignmentBottomSheet(
    bins: List<Bin>,
    occupiedBinIds: List<Int>,
    onBinSelected: (Int?) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = { onDismiss() }) {
        LazyColumn {
            items(bins) { bin ->
                val isOccupied = bin.id in occupiedBinIds
                if (isOccupied) {

                    Text(bin.label, color = Color.Gray, textDecoration = TextDecoration.LineThrough)

                } else {

                    Text(bin.label, modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            onBinSelected(bin.id)
                            onDismiss()
                        })

                }

            }
        }
    }
}
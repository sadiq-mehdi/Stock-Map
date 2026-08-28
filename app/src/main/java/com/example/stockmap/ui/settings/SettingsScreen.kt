package com.example.stockmap.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.stockmap.ui.components.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel(), navController: NavController) {

    val state by viewModel.state.collectAsState()
    var name by remember(state.warehouseName) { mutableStateOf(state.warehouseName) }
    var rows by remember(state.rows) { mutableStateOf(state.rows.toString()) }
    var shelves by remember(state.shelves) { mutableStateOf(state.shelves.toString()) }
    var bins by remember(state.bins) { mutableStateOf(state.bins.toString()) }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Settings") })
    }, bottomBar = { BottomNavBar(navController = navController) }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Warehouse name") }
            )
            Button(onClick = { viewModel.saveWarehouseName(name) }) {
                Text("Save")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("Layout")

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = rows,
                onValueChange = { rows = it },
                placeholder = { Text("Rows") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = shelves,
                onValueChange = { shelves = it },
                placeholder = { Text("Shelves") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = bins,
                onValueChange = { bins = it },
                placeholder = { Text("Bins") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(onClick = {
                viewModel.saveLayout(
                    rows.toIntOrNull() ?: 0,
                    shelves.toIntOrNull() ?: 0,
                    bins.toIntOrNull() ?: 0
                )
            }) { Text("Save Layout") }
        }
    }

}
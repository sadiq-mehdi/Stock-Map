package com.example.stockmap.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.stockmap.ui.components.BottomNavBar
import com.example.stockmap.ui.theme.Black
import com.example.stockmap.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel(), navController: NavController) {

    val state by viewModel.state.collectAsState()
    var name by remember(state.warehouseName) { mutableStateOf(state.warehouseName) }
    var rows by remember(state.rows) { mutableStateOf(state.rows.toString()) }
    var shelves by remember(state.shelves) { mutableStateOf(state.shelves.toString()) }
    var bins by remember(state.bins) { mutableStateOf(state.bins.toString()) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        },
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = currentRoute
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Warehouse section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = White
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Warehouse",
                        style = MaterialTheme.typography.titleMedium,
                        color = Black
                    )

                    Text(
                        text = "Set the name of your warehouse.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Black
                    )

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        label = {
                            Text("Warehouse name")
                        }
                    )

                    Button(
                        onClick = {
                            keyboardController?.hide()
                            viewModel.saveWarehouseName(name)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Save Name")
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = White
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Warehouse Layout",
                        style = MaterialTheme.typography.titleMedium,
                        color = Black
                    )

                    Text(
                        text = "Configure the number of rows, shelves, and bins.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Black
                    )

                    OutlinedTextField(
                        value = rows,
                        onValueChange = { rows = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Rows")
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    OutlinedTextField(
                        value = shelves,
                        onValueChange = { shelves = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Shelves per row")
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    OutlinedTextField(
                        value = bins,
                        onValueChange = { bins = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Bins per shelf")
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    Button(
                        onClick = {
                            keyboardController?.hide()

                            viewModel.saveLayout(
                                rows.toIntOrNull() ?: 0,
                                shelves.toIntOrNull() ?: 0,
                                bins.toIntOrNull() ?: 0
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Save Layout")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }}

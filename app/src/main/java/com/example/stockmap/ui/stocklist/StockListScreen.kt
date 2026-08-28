package com.example.stockmap.ui.stocklist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.stockmap.domain.model.Product
import com.example.stockmap.navigation.Routes
import com.example.stockmap.ui.components.BottomNavBar

@Composable
fun StockListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: StockListViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val category by viewModel.category.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            TopBar(onSyncClick = { viewModel.syncProducts() }, onSettingsClick = { navController.navigate("settings")})
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {

            TextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                placeholder = { Text("Search products...") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            CategoryFilterChips(
                categories = uiState.allCategories,
                selectedCategory = category,
                onCategorySelected = { viewModel.onCategoryChange(it) },
            )

            when {
                uiState.isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.products.isEmpty() -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("No Products Found.")
                    }

                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.products) { product ->
                            ProductCard(product, onClick = { navController.navigate("product_detail/${product.id}")})
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(onSyncClick: () -> Unit, onSettingsClick: () -> Unit) {

    TopAppBar(
        title = { Text("Stock Map") },
        actions = {
            IconButton(onClick = onSyncClick) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Sync")
            }
            IconButton(onClick = { onSettingsClick() }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
            }

        })
}

@Composable
private fun CategoryFilterChips(
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit,
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        item {
            FilterChip(
                selected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
                label = { Text("All") }
            )
        }

        item {
            FilterChip(
                selected = selectedCategory == "unassigned",
                onClick = { onCategorySelected("unassigned") },
                label = { Text("Unassigned") }
            )

        }

        items(categories) { category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = { onCategorySelected(category) },
                label = { Text(category) }
            )
        }
    }

}

@Composable
private fun ProductCard(product: Product, onClick: ()-> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = { onClick()}
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(product.name, fontWeight = FontWeight.Bold)
                Text("${product.currentStock}")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(product.category)

            Spacer(modifier = Modifier.height(5.dp))

            Text("SKU : ${product.sku}")

            Spacer(modifier = Modifier.height(5.dp))

            if (product.currentStock < product.minimumStock) {
                Badge(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = Color.White
                ) {
                    Text("Low Stock")
                }
            }

            if (product.binId == null) {
                Badge(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                ) {
                    Text("Unassigned")
                }
            }
        }

    }

}


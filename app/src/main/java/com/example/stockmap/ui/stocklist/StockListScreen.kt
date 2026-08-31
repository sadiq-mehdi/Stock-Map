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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.stockmap.domain.model.Product
import com.example.stockmap.ui.components.BottomNavBar
import com.example.stockmap.ui.theme.Black
import com.example.stockmap.ui.theme.Cream
import com.example.stockmap.ui.theme.Red
import com.example.stockmap.ui.theme.White

@Composable
fun StockListScreen(
    navController: NavController,
    viewModel: StockListViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val category by viewModel.category.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val warehouseName by viewModel.warehouseName.collectAsState()

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        containerColor = Cream,
        topBar = {
            TopBar(
                onSyncClick = { viewModel.syncProducts() },
                onSettingsClick = { navController.navigate("settings") },
                title = {
                    Text(
                        warehouseName.ifEmpty { "Stock Map" },
                        fontWeight = FontWeight.Bold
                    )
                })
        },
        bottomBar = {
            BottomNavBar(navController = navController, currentRoute = currentRoute)
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                placeholder = { Text("Search products...") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            CategoryFilterChips(
                categories = uiState.allCategories,
                selectedCategory = category,
                onCategorySelected = { viewModel.onCategoryChange(it) },
            )

            Spacer(modifier = Modifier.height(8.dp))

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
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.syncProducts() }) {
                            Text("Sync Products")
                        }
                    }

                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.products) { product ->
                            ProductCard(
                                product,
                                onClick = { navController.navigate("product_detail/${product.id}") })
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    onSyncClick: () -> Unit,
    onSettingsClick: () -> Unit,
    title: @Composable () -> Unit
) {

    TopAppBar(
        title = { title() },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Black,
            titleContentColor = White,
            navigationIconContentColor = White,
            actionIconContentColor = White
        ),
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
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
                label = { Text("All") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Black,
                    selectedLabelColor = White,
                    containerColor = White,
                    labelColor = Black
                )
            )
        }

        item {
            FilterChip(
                selected = selectedCategory == "unassigned",
                onClick = { onCategorySelected("unassigned") },
                label = { Text("Unassigned") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Black,
                    selectedLabelColor = White,
                    containerColor = White,
                    labelColor = Black
                )
            )

        }

        items(categories) { category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = { onCategorySelected(category) },
                label = { Text(category) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Black,
                    selectedLabelColor = White,
                    containerColor = White,
                    labelColor = Black
                )
            )
        }
    }

}

@Composable
private fun ProductCard(product: Product, onClick: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ), colors = CardDefaults.cardColors(
            containerColor = White
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Black
                )

                Spacer(modifier = Modifier.height(5.dp))

                Badge(
                    containerColor = Black,
                    contentColor = White,
                ) {
                    Text(
                        product.category,
                        color = White,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    )
                }

                if (product.binId == null) {
                    Spacer(modifier = Modifier.height(5.dp))

                    Badge(
                        containerColor = Red,
                        contentColor = White,
                    ) {
                        Text(
                            text = "Unassigned",
                            color = White,
                            modifier = Modifier.padding(horizontal = 6.dp)
                        )
                    }
                }

            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "${product.currentStock}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Black
                )

                Text("in stock", style = MaterialTheme.typography.bodySmall)
            }
        }
    }

}


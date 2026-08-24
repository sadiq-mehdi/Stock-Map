package com.example.stockmap.ui.binmap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.stockmap.domain.model.Bin
import com.example.stockmap.domain.model.Product
import com.example.stockmap.ui.components.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BinMapScreen(viewModel: BinMapViewModel = hiltViewModel(), navController: NavController) {

    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bin Map") }
            )
        },
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.bins) { bin ->
                    Card(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        ), shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = binColor(
                                bin,
                                state.products
                            )
                        ),
                        onClick = {
                            val product = state.products.find { it.binId == bin.id }
                            product?.let { navController.navigate("product_detail/${product.id}") }
                        }) {
                        Box(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(bin.label)
                        }
                    }
                }
            }
            Legend()
        }
    }

}

private fun binColor(bin: Bin, products: List<Product>): Color {
    val product = products.find { it.binId == bin.id }

    return if (product == null) Color.Gray
    else {
        when {
            product.currentStock == 0 -> Color.Red
            product.currentStock < product.minimumStock -> Color.Yellow
            else -> Color.Green
        }
    }
}

@Composable
private fun LegendItem(color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color = color, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text)
    }
}

@Composable
private fun Legend() {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        LegendItem(color = Color.Gray, text = "Unoccupied")
        LegendItem(color = Color.Green, text = "Healthy")
        LegendItem(color = Color.Yellow, text = "Low Stock")
        LegendItem(color = Color.Red, text = "Empty")
    }
}
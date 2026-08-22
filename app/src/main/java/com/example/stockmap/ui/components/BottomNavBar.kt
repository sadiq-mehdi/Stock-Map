package com.example.stockmap.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.stockmap.navigation.Routes

@Composable
fun BottomNavBar(navController: NavController) {
    NavigationBar(

    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Routes.STOCK_LIST)},
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Routes.SCAN)},
            icon = { Icon(Icons.Default.AddCircle, contentDescription = "Scan") },
            label = { Text("Scan") },
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Routes.BIN_MAP)},
            icon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Bin Map") },
            label = { Text("Bin Map") }
        )

    }
}
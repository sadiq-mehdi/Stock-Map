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
fun BottomNavBar(navController: NavController, currentRoute: String?) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == Routes.STOCK_LIST,
            onClick = {
                navController.navigate(Routes.STOCK_LIST) {
                    popUpTo(Routes.STOCK_LIST) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = currentRoute == Routes.SCAN,
            onClick = {
                navController.navigate(Routes.SCAN) {
                    popUpTo(Routes.STOCK_LIST) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.AddCircle, contentDescription = "Scan") },
            label = { Text("Scan") },
        )

        NavigationBarItem(
            selected = currentRoute == Routes.BIN_MAP,
            onClick = {
                navController.navigate(Routes.BIN_MAP) {
                    popUpTo(Routes.STOCK_LIST) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Bin Map") },
            label = { Text("Bin Map") }
        )

    }
}
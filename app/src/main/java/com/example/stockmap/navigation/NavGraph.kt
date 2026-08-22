package com.example.stockmap.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.stockmap.ui.productdetail.ProductDetailScreen
import com.example.stockmap.ui.stocklist.StockListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.STOCK_LIST
    ) {
        composable(route = Routes.STOCK_LIST) { StockListScreen(navController = navController) }
        composable(
            route = Routes.PRODUCT_DETAIL,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) {
            ProductDetailScreen(onBackClick = { navController.popBackStack() }, navController = navController)
        }
        composable(route = Routes.BIN_MAP) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text("Bin Map Coming Soon")
            }
        }
        composable(route = Routes.SCAN) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text("Scan Coming Soon")
            }
        }
        composable(route = Routes.SETTINGS) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text("Settings Coming Soon")
            }
        }

    }
}

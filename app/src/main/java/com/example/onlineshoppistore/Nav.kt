package com.example.onlineshoppistore

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.onlineshoppistore.ui.AllProductScreen
import com.example.onlineshoppistore.ui.CartScreen
import com.example.onlineshoppistore.ui.DetailScreen
import com.example.onlineshoppistore.ui.DetailScreenViewModel
import com.example.onlineshoppistore.ui.HomeScreen
import com.example.onlineshoppistore.ui.IndevelopmentScreen
import com.example.onlineshoppistore.ui.ProductPage

@Composable
fun Nav(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home"){

        composable(route = "home"){
           HomeScreen(navController= navController)

        }
        composable(route = "details"+"?id={id}&price={price}", arguments = listOf(
            navArgument(
                name = "id"
            ) {
                type = NavType.StringType
                defaultValue = ""
            },navArgument(
                name = "price"
            ) {
                type = NavType.StringType
                defaultValue = ""
            },

            )
        ){

            ProductPage(navController = navController)

        }
        composable(route = "cart"){
            CartScreen(navController)

        }
        composable(route = "all"){
            AllProductScreen(navController = navController)

        }
        composable(route = "info"){
           IndevelopmentScreen(navController = navController)

        }

    }

}
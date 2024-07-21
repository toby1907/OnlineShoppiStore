package com.example.onlineshoppistore

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.onlineshoppistore.ui.HomeScreen
import com.example.onlineshoppistore.ui.ProductsViewModel
import com.example.onlineshoppistore.ui.cart.CartScreen
import com.example.onlineshoppistore.ui.checkout.CheckoutScreen
import com.example.onlineshoppistore.ui.complete_order.CompleteOrder
import com.example.onlineshoppistore.ui.decodeText
import com.example.onlineshoppistore.ui.detail.ProductPage
import com.example.onlineshoppistore.ui.order.OrderDetailScreen
import com.example.onlineshoppistore.ui.wishlist.WishlistViewModel

@Composable
fun Nav() {
    val navController = rememberNavController()
    val navController2: NavHostController = rememberNavController()
    val productsViewModel: ProductsViewModel = hiltViewModel()
    val viewModel: WishlistViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = "home") {

        composable(route = "home") {
            HomeScreen(
                navController = navController,
                navController2 = navController2,
                productsViewModel = productsViewModel, viewModel = viewModel
            )

        }
        composable(
            route = "details" + "?id={id}&price={price}", arguments = listOf(
                navArgument(
                    name = "id"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(
                    name = "price"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                },

                )
        ) {

            ProductPage(navController = navController)

        }
        composable(route = "cart") {
            CartScreen(navController, navController2 = navController2)

        }

        composable(route = "checkout") {
            CheckoutScreen(navController = navController)

        }
        composable(route = "complete") {
            CompleteOrder(navController, navController2 = navController2)

        }

        composable(route = "complete_order_detail" + "?id={id}",
            arguments = listOf(
                navArgument(
                    name = "id"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                },
            )
        ) { entry ->
            val id = entry.arguments?.getString("id") ?: " "

            OrderDetailScreen(
                id = id,
                navController = navController,
                navController2 = navController2
            )

        }

    }

}


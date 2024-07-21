package com.example.onlineshoppistore.ui.complete_order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.onlineshoppistore.ui.components.CompleteScreen
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen() {
    // Show loading UI (e.g., CircularProgressIndicator)
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize())   {
    CircularProgressIndicator()
    }
}

@Composable
fun Content(navController2: NavHostController,navController: NavController) {
    // Your actual Compose UI goes here
 Column(verticalArrangement = Arrangement.Center,
     horizontalAlignment = Alignment.CenterHorizontally,
     modifier = Modifier.fillMaxSize())   {
     CompleteScreen(navController2 = navController2,navController)
 }
}

@Composable
fun CompleteOrder(navController: NavController,navController2: NavHostController) {
    val isLoading = remember { mutableStateOf(true) }

    // Simulate loading (e.g., fetching data)
    LaunchedEffect(Unit) {
        // Simulate data loading delay (replace with actual data fetching logic)
        delay(3000)
        isLoading.value = false
    }

    if (isLoading.value) {
        LoadingScreen()
    } else {
        Content(navController2=navController2, navController = navController)
    }
}

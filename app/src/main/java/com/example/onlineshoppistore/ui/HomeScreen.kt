package com.example.onlineshoppistore.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
               navController: NavController,  viewModel: ProductViewModel = hiltViewModel(),
               ) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Home") })
        }
    )
    {
        Content(modifier = Modifier.padding(it),navController=navController, viewModel = viewModel)

    }
}

@Composable
fun Content(modifier: Modifier = Modifier,
            viewModel: ProductViewModel ,
            navController: NavController) {
 //   val scope = rememberCoroutineScope()
    val value = viewModel.productsState.collectAsState().value

    var errorMessage: String = ""
    /*val retryState = remember {
        mutableStateOf(false
        )
    }*/

    val retry = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(retry.value) {
        viewModel.fetchProducts()
    }
    /*LaunchedEffect(key1 = true) {

        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ProductViewModel.UiEvent.ShowSnackbar -> {

                   *//* scope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message
                        )
                    }*//*
                }

                ProductViewModel.UiEvent.Loading -> {

                }

                ProductViewModel.UiEvent.Success -> {

                }
            }
        }
    }*/
    Spacer(modifier = Modifier.padding(top=16.dp))
val errorState = viewModel.productError.collectAsState().value
    val loadingState = viewModel.productloading.collectAsState().value

    if (errorState.state){
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize())
        {
            Column{
                Text(text = errorState.message)
                Text(text = "Retry",
                    color = Color.Blue,
                    modifier = Modifier.clickable {
               retry.value = !retry.value
                    }
                )
            }
        }
    }

    if (loadingState){

            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize())   {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp)
                )
            }
    }

        LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(2)) {

            items(value.size) { shoeItem ->

                Column {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable(onClick = {
                                navController.navigate("details"+"?id=${value[shoeItem].id}&price=${value[shoeItem].currentPrice[0].NGN[0].toString()}")
                            }),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(shape = RoundedCornerShape(8.dp)),
                            model = "https://api.timbu.cloud/images/${value[shoeItem].photos[0].url}",
                            contentDescription = null,
                        )

                    }
                    Text(
                        text = value[shoeItem].name,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = value[shoeItem].currentPrice[0].NGN[0].toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }


}
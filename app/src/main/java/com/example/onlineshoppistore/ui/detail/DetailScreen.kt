package com.example.onlineshoppistore.ui.detail
/*

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.onlineshoppistore.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController){
    val viewModel: DetailScreenViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(navigationIcon = {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {

                    Icon(painter = painterResource(id = R.drawable.arrow_back_24), contentDescription ="" )
                                                    }
            },title = { Text(text = "Details") })
        }
    ) {

       ProductContent(modifier = Modifier.padding(it),viewModel)
    }
}
@Composable
fun ProductContent(modifier: Modifier=Modifier,viewModel: DetailScreenViewModel){
    val product = viewModel.productState.collectAsState().value
    Spacer(modifier = Modifier.padding(top=16.dp))
    val errorState = viewModel.productError.collectAsState().value
    val loadingState = viewModel.productloading.collectAsState().value
    val retry = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(retry.value) {
        viewModel.fetchProduct()
    }

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
   if (!loadingState&&!errorState.state) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 8.dp)
            ,

            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
           Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                AsyncImage(
                    modifier = Modifier
                        .width(337.dp)
                        .height(193.dp)
                        .padding(top = 16.dp),
                    model = "https://api.timbu.cloud/images/${product.imageUrl}",
                    contentDescription = null,
                )


                Column(
                    modifier = Modifier.padding(top = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {


                    Text(
                        text = product.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = "Sneakers",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }


            Spacer(modifier = Modifier.padding(8.dp))


                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                        .padding(top = 8.dp)
                            .fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .size(width = 240.dp, height = 400.dp),
                        ) {
                            Text(
                                text = "Structure:",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = product.description,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )

                        }

Spacer(modifier = Modifier.size(8.dp))
                        Surface(
                            modifier = Modifier
                                .size(99.dp, 157.dp)
                                .clickable {

                                },
                            color = Color(0xff69bcfc),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.cart_icon),
                                    contentDescription = ""
                                )
                                Spacer(modifier = Modifier.padding(16.dp))
                                Text(
                                    text = product.price + "NGN",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(600),
                                    color = Color(0xFFFFFFFF)
                                )
                            }

                        }
                    }


        }
    }
}*/

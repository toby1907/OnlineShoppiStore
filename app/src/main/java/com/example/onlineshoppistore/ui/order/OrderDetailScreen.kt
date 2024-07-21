package com.example.onlineshoppistore.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.onlineshoppistore.R
import com.example.onlineshoppistore.data.room.CartItem
import com.example.onlineshoppistore.ui.cart.Footer2
import com.example.onlineshoppistore.ui.detail.DescriptionReviewsTabs
import com.example.onlineshoppistore.ui.detail.DetailScreenEvent
import com.example.onlineshoppistore.ui.detail.ProductTitleSection
import com.example.onlineshoppistore.ui.detail.QuantitySelector

@Composable
fun OrderDetailScreen(
    navController: NavController,
    viewModel: MyOrdersViewModel= hiltViewModel(),
    id: String,
    navController2 : NavController,
) {

val viewModelState= viewModel.cartItems.collectAsState()
val cartItem = viewModelState.value.find { it.id==id }
   Column(verticalArrangement = Arrangement.SpaceBetween,
       horizontalAlignment = Alignment.CenterHorizontally,
       modifier = Modifier.fillMaxSize()
       ) {
       TopAppBar(title = { Text(text = "Ordered Product") },
           navigationIcon = {
               Icon(painter = painterResource(id = R.drawable.arrow_back_24),
                   contentDescription ="",
                   modifier = Modifier.clickable {
                       navController.popBackStack()
                   }

               )
           },
           backgroundColor = Color.White,
           contentColor = Color.Black
           )
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                modifier = Modifier
                    .width(320.dp)
                    .height(240.dp)
                    .align(Alignment.Center)
                    .clip(shape = RoundedCornerShape(8.dp)),
                model = "https://api.timbu.cloud/images/${cartItem?.imageUrl}",
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Row(
          horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = cartItem?.name?:"",
// Text/lg: Medium
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF2A2A2A),

                        )
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                            .background(
                                color = Color(cartItem?.color ?: 0xffffff),
                                shape = RoundedCornerShape(size = 4.dp)
                            )

                            .padding(start = 4.dp, top = 4.dp, end = 4.dp, bottom = 4.dp)

                    ) {

                    }
                    //create a enum class for the color
                    /* Text(
                        text = "Black",
// Text/md: Regular
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF555555),

                            )
                    )*/
                }
                Divider(
                    Modifier
                        .padding(0.dp)
                        .width(1.dp)
                        .height(12.dp)
                        .background(color = Color(0xFFBDBDBD))
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Size ",
// Text/lg: Regular
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF2A2A2A),

                            )
                    )
                    Text(
                        text = cartItem?.size.toString(),
// Text/lg: Regular
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF555555),

                            )
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // quatity
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .width(25.dp)
                            .height(26.dp)
                            .background(
                                color = Color(0x1F0072C6),
                                shape = RoundedCornerShape(size = 2.dp)
                            )


                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = cartItem?.quantity.toString()?:"",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF2A2A2A),

                                )
                        )
                    }
                }
                Text(
                    text = "Price:  ₦ ${cartItem?.price}",
// Text/lg: Medium
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF2A2A2A),

                        )
                )
            }
        }
        DescriptionReviewsTabs(
            cartItem?.description?:""
        )
        Footer4(navController = navController,navController2 = navController2)
    }








}
@Composable
fun  Footer4(navController: NavController,navController2:NavController){
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ){

        Button(modifier = Modifier
            .width(141.dp)
            .height(42.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF0072C6),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(size = 8.dp),
            onClick = { navController2.navigate("home1")
                navController.popBackStack() }) {
            Icon(painter = painterResource(id = R.drawable.cart_icon), contentDescription ="" )
            Spacer(modifier = Modifier.size(8.dp))
            androidx.compose.material.Text(
                text = "Products",

                // Text/lg: SemiBold
                style = TextStyle(
                    fontSize = 15.sp,

                    fontWeight = FontWeight(600),
                    color = Color.White,
                )
            )
        }
    }
}

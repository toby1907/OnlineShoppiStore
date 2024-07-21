package com.example.onlineshoppistore.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.onlineshoppistore.data.room.CartItem
import com.example.onlineshoppistore.ui.checkout.CartItem2
import com.example.onlineshoppistore.ui.checkout.CheckoutViewModel
import com.example.onlineshoppistore.ui.checkout.Header
import com.example.onlineshoppistore.ui.components.EmptyOrdersScreen
import com.example.onlineshoppistore.ui.encodeText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOrderScreen(navController: NavController,navController2: NavController) {
    val viewModel: MyOrdersViewModel = hiltViewModel()
    val orderlist = viewModel.cartItems.collectAsState()
    var state by remember { mutableStateOf(0) }
    val titles = listOf("Active Orders", "Completed Orders")
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        SecondaryTabRow(selectedTabIndex = state) {
            titles.forEachIndexed { index, title ->
                Tab(text = {
                    Text(
                        text = title,
                        // Text/lg: Medium
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(500),
                        )
                    )
                }, onClick = { state = index }, selected = (index == state),
                    selectedContentColor = Color(0xFF2A2A2A),
                    unselectedContentColor = Color(0xFF555555)
                )
            }
        }
        when (state) {
            0 -> {
                if (orderlist.value.isEmpty()) {
                    EmptyOrdersScreen(navController = navController2)
                } else {

                    LazyColumn {
                            items(orderlist.value) { cartItem ->

                                CartItem3(
                                    cartItem = cartItem,navController
                                )

                            }
                    }
                        

                    
                    
                }
            }

            1 -> {
                EmptyOrdersScreen(navController = navController2)
            }
        }
    }
}

@Composable
fun CartItem3(cartItem: CartItem,navController: NavController) {
    val trimText = cartItem.description.trimIndent()
    val encodedText = encodeText(trimText)
    val trimText2 = cartItem.name.trimIndent()
    val encodedName = encodeText(trimText2)
    Row(verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(8.dp)
            .border(
                width = 1.dp, color = Color(0xFFF9F9F9),
                shape = RoundedCornerShape(size = 8.dp)
            )
            .fillMaxWidth()
            .clickable {

                navController.navigate("complete_order_detail"+"?id=${cartItem.id}")
            }
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),

        ) {
            Card(
                modifier = Modifier
                    .width(100.dp)
                    .height(120.dp)
                    .background(color = Color(0x66EAEAEA), shape = RoundedCornerShape(size = 8.dp))
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        modifier = Modifier
                            .width(80.dp)
                            .height(50.dp)
                            .align(Alignment.Center)
                            .clip(shape = RoundedCornerShape(8.dp)),
                        model = "https://api.timbu.cloud/images/${cartItem.imageUrl}",
                        contentDescription = null,
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = cartItem.name,
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
                                    color = Color(cartItem.color),
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
                            text = cartItem.size.toString(),
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
                                text = cartItem.quantity.toString(),
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF2A2A2A),

                                    )
                            )
                        }
                    }
                    Text(
                        text = cartItem.price,
// Text/lg: Medium
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF2A2A2A),

                            )
                    )
                }
            }
        }
        Button(onClick = { /*TODO*/ },
            modifier = Modifier
                .width(100.dp)
                .height(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor =  Color(0xFF0072C6),
                contentColor =  Color(0xFFFFFFFF),
            ),
            shape = RoundedCornerShape(size = 8.dp)
        )
        {
            Text(
                text = "Track",
                // Text/md: Medium
                style = TextStyle(
                    fontSize = 12.sp,

                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.Center,
                )
            )
        }

    }
}
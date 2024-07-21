package com.example.onlineshoppistore.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.onlineshoppistore.ui.cart.components.PersonalInfoSection
import com.example.onlineshoppistore.ui.components.EmptyCartScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController,navController2: NavController){
    val viewModel: CartScreenViewModel = hiltViewModel()
    val cartItems = viewModel.cartItems.collectAsState().value
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_left_02),
                    contentDescription = ""
                )
            }
        },
            title = {
                Text(
                    text = "My Cart",

                    // Heading/H4: SemiBold
                    style = TextStyle(
                        fontSize = 19.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF292929),
                    )
                )
            })
        LazyColumn {
         if (cartItems.isNotEmpty())   {
                items(cartItems) { cartItem ->

                    com.example.onlineshoppistore.ui.cart.CartItem(
                        cartItem = cartItem,
                        viewModel = viewModel,
                        navController = navController
                    )

                }
             item {
                 Spacer(modifier = Modifier.size(68.dp))
                 Footer2(navController = navController, cartItems)
             }
            }
            else{
                item {
                    EmptyCartScreen(navController2)
                }
         }
        }
    }
}
@Composable
fun CartItem(cartItem: CartItem,viewModel: CartScreenViewModel,navController: NavController){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp,Alignment.Start),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = 1.dp, color = Color(0xFFF9F9F9),
                shape = RoundedCornerShape(size = 8.dp)
            )
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
            .clickable {
                navController.navigate("details" + "?id=${cartItem.id}&price=${cartItem.price}")
            }
    ) {
        Card(
            modifier = Modifier
                .width(100.dp)
                .height(120.dp)
                .background(color = Color(0x66EAEAEA), shape = RoundedCornerShape(size = 8.dp))
        ) {
            Box(modifier = Modifier.fillMaxSize())  {
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
            verticalArrangement = Arrangement.spacedBy(8.dp,),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
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
                Icon(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable {
                            viewModel.deleteItem(cartItem.id)
                        }

                    ,
                    painter = painterResource(id = R.drawable.close_fill0_wght400_grad0_opsz24),
                    contentDescription = ""
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
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
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                // quatity
                CartItemRow(cartItem = cartItem, viewModel = viewModel)
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
}
@Composable
fun CartItemRow(cartItem: CartItem, viewModel: CartScreenViewModel) {
    var quantity by remember { mutableStateOf(cartItem.quantity) }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Minus Icon
        androidx.compose.material.Icon(
            painter = painterResource(id = R.drawable.minus_sign),
            contentDescription = "Decrease Quantity",
            tint = Color.Gray,
            modifier = Modifier
                .size(16.dp)
                .clickable {
                    if (quantity > 1) {
                        // Decrease quantity
                        quantity--
                        viewModel.updateCartItemQuantity(cartItem.id, cartItem.quantity - 1)
                    }
                }
        )

        // Quantity Display
        Box(
            modifier = Modifier
                .width(25.dp)
                .height(26.dp)
                .background(
                    color = Color(0x1F0072C6),
                    shape = RoundedCornerShape(size = 2.dp)
                )
        ) {
            androidx.compose.material.Text(
                modifier = Modifier.align(Alignment.Center),
                text = quantity.toString(),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF2A2A2A),
                )
            )
        }

        // Plus Icon
        androidx.compose.material.Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Increase Quantity",
            tint = Color.Gray,
            modifier = Modifier
                .size(16.dp)
                .clickable {
                    // Increase quantity
                    if (quantity < 10) {

                    quantity++
                    viewModel.updateCartItemQuantity(cartItem.id, cartItem.quantity + 1)
                    }
                }
        )
    }
}



@Composable
fun  Footer2(navController: NavController,cartItems:List<CartItem>){
    val totalPrice = cartItems.sumOf { it.itemTotalPrice*it.quantity }
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            androidx.compose.material.Text(
                text = "Total price",
// Text/md: Regular
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF9D9D9D),

                    )
            )
            androidx.compose.material.Text(
                text = "₦ $totalPrice",
// Heading/H4: Medium
                style = TextStyle(
                    fontSize = 19.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF2A2A2A),

                    )
            )
        }
        Button(modifier = Modifier
            .width(141.dp)
            .height(42.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF0072C6),
                    contentColor = Color.White
                ),
            shape = RoundedCornerShape(size = 8.dp),
            onClick = { navController.navigate("checkout") }) {
            androidx.compose.material.Icon(painter = painterResource(id = R.drawable.cart_icon), contentDescription ="" )
            Spacer(modifier = Modifier.size(8.dp))
            androidx.compose.material.Text(
                text = "Checkout",

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
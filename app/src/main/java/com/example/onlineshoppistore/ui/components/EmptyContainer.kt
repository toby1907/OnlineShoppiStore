package com.example.onlineshoppistore.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.onlineshoppistore.R

@Composable
fun EmptyWishlistScreen (){

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier
            .padding(1.dp)
            .width(104.dp)
            .height(104.dp)
            .background(color = Color(0xFFEAECF0), shape = CircleShape),
        ){
            Box(modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .background(color = Color(0x66344054), shape = RoundedCornerShape(size = 24.dp))
                .align(Alignment.Center)
                .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 12.dp)){

                Icon(painter = painterResource(id = R.drawable.favorite_fill0_wght400_grad0_opsz24),
                    contentDescription ="icon",
                    tint = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                    )
            }
        }

        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = "You have not added any item to your wish list",

                // Text/lg: Medium
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF2A2A2A),
                    textAlign = TextAlign.Center,
                )
            )
            Button(onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0072C6),
                ),
                shape = RoundedCornerShape(size = 8.dp),
                modifier = Modifier
                    .width(350.dp)
                    .height(42.dp)
                    .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,

                ) {
                    Text(
                        text = "Discover products",

                        // Text/lg: Medium
                        style = TextStyle(
                            fontSize = 15.sp,

                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF)
                        )
                    )
                }
            }
        }

    }
}
@Composable
fun EmptyOrdersScreen (navController: NavController){

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier
            .padding(1.dp)
            .width(104.dp)
            .height(104.dp)
            .background(color = Color(0xFFEAECF0), shape = CircleShape),
        ){

            Image(painter = painterResource(id = R.drawable.background),
                contentDescription ="",
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center,
                modifier = Modifier
                    .padding(1.dp)
                    .width(141.dp)
                    .height(106.dp)
            )
        }

        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = "You do not have any active order",

                // Text/lg: Medium
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF2A2A2A),
                    textAlign = TextAlign.Center,
                )
            )
            Button(onClick = { navController.navigate("home1")  },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0072C6),
                ),
                shape = RoundedCornerShape(size = 8.dp),
                modifier = Modifier
                    .width(350.dp)
                    .height(42.dp)

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,

                    ) {
                    Text(
                        text = "Discover products",

                        // Text/lg: Medium
                        style = TextStyle(
                            fontSize = 15.sp,

                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF)
                        )
                    )
                }
            }
        }

    }
}

@Composable
fun EmptyCartScreen (navController: NavController){

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier
            .padding(1.dp)
            .width(104.dp)
            .height(104.dp)
            .background(color = Color(0xFFEAECF0), shape = CircleShape),
        ){
            Box(modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .background(color = Color(0x66344054), shape = RoundedCornerShape(size = 24.dp))
                .align(Alignment.Center)
                .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 12.dp)){

                Icon(painter = painterResource(id = R.drawable.favorite_fill0_wght400_grad0_opsz24),
                    contentDescription ="icon",
                    tint = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = "You have not added any item to cart",

                // Text/lg: Medium
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF2A2A2A),
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.size(8.dp))
            Button(onClick = {
               // navController.navigate("home1")
                navController.popBackStack()
                             },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0072C6),
                ),
                shape = RoundedCornerShape(size = 8.dp),
                modifier = Modifier
                    .width(350.dp)
                    .height(42.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,

                    ) {
                    Text(
                        text = "Discover products",

                        // Text/lg: Medium
                        style = TextStyle(
                            fontSize = 15.sp,

                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF)
                        )
                    )
                }
            }
        }

    }
}

@Composable
fun CompleteScreen (navController2: NavHostController,navController: NavController){

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier
            .padding(1.dp)
            .width(104.dp)
            .height(104.dp)
            .background(color = Color(0xFFEAECF0), shape = CircleShape),
        ){
            Box(modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .background(color = Color(0x66344054), shape = RoundedCornerShape(size = 24.dp))
                .align(Alignment.Center)
                .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 12.dp)){

                Image(painter = painterResource(id = R.drawable.complete), contentDescription ="", modifier = Modifier.align(
                    Alignment.Center) )
            }
        }

        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = "Payment successful",

                // Heading/H4: Medium
                style = TextStyle(
                    fontSize = 19.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF2A2A2A),
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "You have successfully placed an order. Details of your order has been sent to your email. ",

                // Text/lg: Regular
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF707070),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .width(350.dp)
            )
            Spacer(modifier = Modifier.size(24.dp))
            Button(onClick = {

                navController2.navigate("home1")
                navController.popBackStack()
                             },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0072C6),
                ),
                shape = RoundedCornerShape(size = 8.dp),
                modifier = Modifier
                    .width(350.dp)
                    .height(42.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,

                    ) {
                    Text(
                        text = "Okay",

                        // Text/lg: Medium
                        style = TextStyle(
                            fontSize = 15.sp,

                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF)
                        )
                    )
                }
            }
        }

    }
}
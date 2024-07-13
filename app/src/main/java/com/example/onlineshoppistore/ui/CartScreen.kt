package com.example.onlineshoppistore.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onlineshoppistore.R
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController){

  Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize())  {
      TopAppBar(navigationIcon = {
         IconButton(onClick = {navController.navigateUp() }) {
              Icon(
                  painter = painterResource(id = R.drawable.arrow_left_02),
                  contentDescription = ""
              )
          }
      },
          title = { Text(
              text = "My Cart",

              // Heading/H4: SemiBold
              style = TextStyle(
                  fontSize = 19.sp,
                  fontWeight = FontWeight(600),
                  color = Color(0xFF292929),
              )
          ) })
      CartItem()
      CartItem()
      Spacer(modifier = Modifier.size(68.dp))
      Footer2(navController =navController, )

    }

}
@Composable
fun CartItem(){
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
    ) {
        Card(
            modifier = Modifier
                .width(100.dp)
                .height(120.dp)
                .background(color = Color(0x66EAEAEA), shape = RoundedCornerShape(size = 8.dp))
        ) {
            Box(modifier = Modifier.fillMaxSize())  {
                Image(
                    modifier = Modifier
                        .width(80.dp)
                        .height(50.dp)
                        .align(Alignment.Center)
                    ,
                    painter = painterResource(id = R.drawable.shoe1),
                    contentDescription = "shoe"
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
                    text = "Ego Raid",
// Text/lg: Medium
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF2A2A2A),

                        )
                )
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
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
                                color = Color(0xFF2A2A2A),
                                shape = RoundedCornerShape(size = 4.dp)
                            )

                            .padding(start = 4.dp, top = 4.dp, end = 4.dp, bottom = 4.dp)

                    ) {

                    }
                    Text(
                        text = "Black",
// Text/md: Regular
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF555555),

                            )
                    )
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
                        text = "Size",
// Text/lg: Regular
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF2A2A2A),

                            )
                    )
                    Text(
                        text = " 39",
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
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    androidx.compose.material.Icon(
                        painter = painterResource(id = R.drawable.minus_sign),
                        contentDescription = "Decrease Quantity",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(16.dp)
                    )

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
                            text = "1",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF2A2A2A),

                                )
                        )
                    }
                    androidx.compose.material.Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase Quantity",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable {
                            }
                    )
                }
                Text(
                    text = "₦ 37,000.00",
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
fun  Footer2(navController: NavController){
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(8.dp)
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
                text = "₦ 37,000.00",
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
            onClick = { navController.navigate("info") }) {
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
package com.example.onlineshoppistore.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.onlineshoppistore.R

@Composable
fun BottomAppNav(navController: NavController){

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                BottomAppBar(
                    containerColor = Color.White,
                    modifier = Modifier

                        .align(Alignment.BottomCenter),
                    tonalElevation = 8.dp
                )
                {
                    Row(
                        modifier = Modifier.fillMaxWidth(),

                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
// Child views.
                            IconButton(
                                onClick = { /*navController.navigate(AppScreens.HOMESCREEN.name)*/ },
                                /*  modifier = Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(color = Color.Gray, radius = 24.dp)
                                )*/
                            ) {
                                Icon(
                                    painterResource(id = R.drawable.star_half), contentDescription = "Home",
                                    tint = Color(0xff555555)
                                )
                            }
                            Text(text ="Home",
                                    style = TextStyle(
                                    fontSize = 12.sp,
                                fontWeight = FontWeight(600),
                                color = Color(0xFF0072C6),
                                textAlign = TextAlign.Center,
                            )
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
// Child views.

                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painterResource(id = R.drawable.favorite_fill0_wght400_grad0_opsz24),
                                contentDescription = "Favorite",
                                tint = Color(0xff555555)
                            )
                        }
                            Text(text ="All Products",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight(600),
                                    color = Color(0xFF0072C6),
                                    textAlign = TextAlign.Center,
                                )
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
// Child views.
                            IconButton(onClick = {
                            })
                            {
                                Icon(
                                    painterResource(id = R.drawable.cart_icon),
                                    contentDescription = "Message",
                                    tint = Color(0xff555555)
                                )
                            }
                            Text(text ="Cart",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight(600),
                                    color = Color(0xFF0072C6),
                                    textAlign = TextAlign.Center,
                                )
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
// Child views.
                            IconButton(onClick = {
                            })
                            {
                                Icon(
                                    painterResource(id = R.drawable.cart_icon),
                                    contentDescription = "Message",
                                    tint = Color(0xff555555)
                                )
                            }
                            Text(text ="My Orders",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight(600),
                                    color = Color(0xFF0072C6),
                                    textAlign = TextAlign.Center,
                                )
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
// Child views.
                            IconButton(onClick = {
                            })
                            {
                                Icon(
                                    painterResource(id = R.drawable.cart_icon),
                                    contentDescription = "Message",
                                    tint = Color(0xff555555)
                                )
                            }
                            Text(text ="Profile",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight(600),
                                    color = Color(0xFF0072C6),
                                    textAlign = TextAlign.Center,
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavPanel(/*navController2: NavHostController*/ navController: NavController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val bottomAppItems = listOf(
        BottomAppItem(name = "Home",painterResource(id = R.drawable.home_black_24dp) ),
        BottomAppItem(name = "All Products",painterResource(id = R.drawable.dashboard_square_03) ),
        BottomAppItem(name = "Cart",painterResource(id = R.drawable.cart_icon)),
        BottomAppItem(name = "My Orders",painterResource(id = R.drawable.user_account)),
        BottomAppItem(name = "Profile",painterResource(id = R.drawable.profile)),

        )
    LaunchedEffect(Unit){
        if (selectedItem == 0) {
          /*  navController2.navigate("home") {
                popUpTo(0)
            }*/
        }
    }

    NavigationBar( containerColor = Color.White,
        contentColor = Color(0xff555555)
    ) {
        bottomAppItems.forEachIndexed { index, item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor =Color(0xFF0072C6) ,
                    selectedTextColor = Color(0xFF0072C6),
                   // indicatorColor = Color(0x1F0072C6) ,
                    unselectedIconColor =Color(0xff555555) ,
                    unselectedTextColor =Color(0xff555555)
                ),
                icon = { Icon(modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
                    , painter = item.painter, contentDescription = item.name) },
                label = {
                    Text(
                        text = item.name,
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight(500),
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.5.sp,
                        )
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    if (index == 0) {
                       /* navController2.navigate("home") {
                            popUpTo(0)
                        }*/
                    }
                    if (index == 1) {
                       /* navController2.navigate("favourite") {
                            popUpTo(0)
                        }*/
                        navController.navigate("all")

                    }
                    if (index == 2) {
                       /* navController2.navigate("calendar") {
                            popUpTo(0)
                        }*/
                        navController.navigate("cart")
                    }
                    if (index == 3) {
                      /*  navController2.navigate("media") {
                            popUpTo(0)
                        }*/
                        navController.navigate("info")
                    }
                    if (index == 4) {
                        /*  navController2.navigate("media") {
                              popUpTo(0)
                          }*/
                        navController.navigate("info")
                    }
                }
            )
        }
    }

}

data class BottomAppItem(val name:String,val painter: Painter)
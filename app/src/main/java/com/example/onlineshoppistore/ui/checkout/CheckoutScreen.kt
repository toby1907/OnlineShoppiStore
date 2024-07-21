package com.example.onlineshoppistore.ui.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.onlineshoppistore.ui.components.BottomSheet
import com.example.onlineshoppistore.ui.components.EmptyCartScreen
import com.example.onlineshoppistore.ui.components.PaymentOptionBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(navController: NavController) {
    val viewModel: CheckoutViewModel = hiltViewModel()
    val cartItems = viewModel.cartItems.collectAsState().value
    val totalPrice = cartItems.sumOf { it.itemTotalPrice*it.quantity }
    val scope = rememberCoroutineScope()

    var openBottomSheet by remember { mutableStateOf(false) }
    val skipPartiallyExpanded by remember { mutableStateOf(false) }
    val edgeToEdgeEnabled by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    val paymentOptionBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    var openPaymentOptionBottomSheet by remember {
        mutableStateOf(false)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),

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
                    text = "Checkout",

                    // Heading/H4: SemiBold
                    style = TextStyle(
                        fontSize = 19.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF292929),
                    )
                )
            })
        LazyColumn {
            if (cartItems.isNotEmpty()) {
                item {
                    Header(navController = navController)
                }
                items(cartItems) { cartItem ->

                    CartItem2(
                        cartItem = cartItem,
                        viewModel = viewModel,
                        navController = navController
                    )

                }
                item { PersonalInfoSection( editSheet = {
                    openBottomSheet = true
                },
                    added = viewModel.cardNo.value.cardNo.isEmpty(),
                    itsAdded = viewModel.address.value.address.isEmpty(),
                    paymentMethodSheet = {
                        openPaymentOptionBottomSheet = true
                }, viewModel =viewModel,
                    totalPrice = totalPrice
                    )
                }
                item {
                    Spacer(modifier = Modifier.size(8.dp))
                    Footer3(navController = navController, cartItems,viewModel,)
                }
            } else {
                item {
                    EmptyCartScreen(navController)
                }
            }
        }

        if (openBottomSheet) {
            val windowInsets = if (edgeToEdgeEnabled)
                WindowInsets(0) else BottomSheetDefaults.windowInsets
            BottomSheet(
                onDismissRequest = { openBottomSheet = false },
                sheetState = bottomSheetState,
                windowInsets = windowInsets,
                onClick = {
                    scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                        if (!bottomSheetState.isVisible) {
                            openBottomSheet = false
                        }
                    }
                },
                viewModel = viewModel
            )

        }
        if (openPaymentOptionBottomSheet){
            val windowInsets = if (edgeToEdgeEnabled)
                WindowInsets(0) else BottomSheetDefaults.windowInsets
            PaymentOptionBottomSheet(
                onDismissRequest = {   openPaymentOptionBottomSheet = false },
                sheetState = paymentOptionBottomSheetState,
                windowInsets = windowInsets ,
                onClick = {
                    scope.launch { paymentOptionBottomSheetState.hide() }.invokeOnCompletion {
                        if (!paymentOptionBottomSheetState.isVisible) {
                            openPaymentOptionBottomSheet = false
                        }
                    }
                },
                viewModel =viewModel
            )
        }
    }
}

@Composable
fun CartItem2(cartItem: CartItem, viewModel: CheckoutViewModel, navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = 1.dp, color = Color(0xFFF9F9F9),
                shape = RoundedCornerShape(size = 8.dp)
            )
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
            .clickable {

            }
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
}

@Composable
fun Footer3(navController: NavController, cartItems: List<CartItem>,viewModel: CheckoutViewModel) {
    val totalPrice = cartItems.sumOf { it.itemTotalPrice*it.quantity + 1500 }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total price",
// Text/md: Regular
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF9D9D9D),

                    )
            )
            Text(
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
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0072C6),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(size = 8.dp),
            onClick = {
                viewModel.saveUpdatedCartItems()
                navController.navigate("complete") {
                    popUpTo("home")
                }
            }) {

            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Proceed",

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

@Composable
fun Header(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Order list",
// Text/lg: Medium
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF2A2A2A),

                )
        )
        Text(
            text = "Edit",
            modifier =Modifier.clickable {
                                         navController.navigate("cart")
                                         {
                                             launchSingleTop = true
                                             popUpTo("checkout") { inclusive = true }
                                         }
            },
            // Text/lg: SemiBold
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFF0072C6),
            )
        )
    }
}
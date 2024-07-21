package com.example.onlineshoppistore.ui.wishlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.onlineshoppistore.R
import com.example.onlineshoppistore.data.ResponseItem
import com.example.onlineshoppistore.data.datastore.FavoriteItem
import com.example.onlineshoppistore.network.Status
import com.example.onlineshoppistore.ui.UserGreetings
import com.example.onlineshoppistore.ui.components.EmptyWishlistScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistScreen(navController: NavController,viewModel: WishlistViewModel){

    Scaffold (
        topBar = {
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
                        text = "My Wishlist",

                        // Heading/H4: SemiBold
                        style = TextStyle(
                            fontSize = 19.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF292929),
                        )
                    )
                })
        }
    )
    {
WishlistContent(viewModel =viewModel , navController =navController, modifier = Modifier.padding(it) )

    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WishlistContent(viewModel:WishlistViewModel,modifier: Modifier =Modifier, backgroundColor: Color = Color.Transparent,
                    backgroundColorWhileUpdate: Color = Color.LightGray,navController: NavController) {
    var errorMessage: String = ""

    val retry = remember {
        mutableStateOf(false)
    }
    /*LaunchedEffect(retry.value) {
        viewModel.fetchProducts()
    }*/
    val wishlistViewModel: WishlistViewModel = hiltViewModel()

    val dataState by wishlistViewModel.products.collectAsState()
    val favoriteItem by wishlistViewModel.favoriteItem.collectAsState()
    //val invitedPeople: List<Person> = people.filter { invitedToParty.contains(it.nickname) }
    val wishlist: List<ResponseItem> =
        dataState.data?.filter { favoriteItem.contains(FavoriteItem(it.id)) } ?: emptyList()
    // Directly infer the refreshing state from the resource status.
    val isRefreshing =
        dataState.status == Status.LOADING || dataState.status == Status.UPDATING
    val pullRefreshState = rememberPullRefreshState(isRefreshing, wishlistViewModel::fetchProducts)

    Box(
        modifier = Modifier
            .background(if (dataState.status == Status.UPDATING) backgroundColorWhileUpdate else backgroundColor)
            .fillMaxSize()
            .pullRefresh(state = pullRefreshState),

        ) {

    when (dataState.status) {
        Status.SUCCESS, Status.UPDATING -> {
            LazyVerticalGrid(
                modifier = modifier,
                columns = GridCells.Fixed(2),

                ) {

              if(wishlist.isNotEmpty())  {
                    wishlist.let { products ->
                        items(products.size) { shoeItem ->

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    //      modifier = Modifier.fillMaxSize(),
                                ) {


                                    val isFilled2 =
                                        wishlistViewModel.isProductFavorite(products[shoeItem].id)
                                            .collectAsState()
                                    var isFilled by remember { mutableStateOf(isFilled2.value) }
                                    val icon: Painter =
                                        if (isFilled) painterResource(id = R.drawable.favourite) else painterResource(
                                            id = R.drawable.favorite_fill0_wght400_grad0_opsz24
                                        )
                                    val iconTint: Color = if (isFilled) Color.White else Color.White
                                    val boxColor: Color =
                                        if (isFilled) Color.Red else Color(0x99000000)


                                    Card(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .width(168.5.dp)
                                            .height(180.dp)
                                            .clickable(onClick = {

                                            }),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color(
                                                0x66EAEAEA
                                            )
                                        )
                                    ) {
                                        AsyncImage(
                                            modifier = Modifier
                                                .clickable {
                                                    navController.navigate("details" + "?id=${products[shoeItem].id}&price=${products[shoeItem].currentPrice[0].NGN[0].toString()}")
                                                }
                                                .aspectRatio(1f)
                                                .clip(shape = RoundedCornerShape(8.dp)),
                                            model = "https://api.timbu.cloud/images/${products[shoeItem].photos[0].url}",
                                            contentDescription = null,
                                        )

                                    }
                                    Box(modifier = Modifier
                                        .clickable {

                                        }
                                        .align(Alignment.TopEnd)
                                        .offset(
                                            x = (-8).dp,
                                            y = 8.dp
                                        ) // Adjust the offset for precise positioning
                                        .size(48.dp)
                                        .background(
                                            color = Color.Transparent,
                                            shape = RoundedCornerShape(size = 32.dp)
                                        )) {
                                        Box(
                                            modifier = Modifier
                                                .clickable {

                                                }
                                                .align(Alignment.Center)
                                                .size(34.dp)
                                                .background(
                                                    color = boxColor,
                                                    shape = RoundedCornerShape(size = 32.dp)
                                                )


                                        ) {

                                            /*  Icon(
                                    imageVector = if (favoriteState.value == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    modifier = Modifier.toggleable(
                                        value = favoriteState.value ?: false,
                                        onValueChange = { input ->
                                            voiceJournalPreviewViewModel.onChangeFavourite(
                                                change = input,
                                                currentNote = currentNote
                                            )
                                            favoriteState.value = input
                                            // voiceJournalPreviewViewModel.getNotes()
                                        }

                                    ),  tint = if (favoriteState.value==true) Variables.SchemesError else Variables.SchemesOnPrimary
                                    )*/
                                            Icon(
                                                painter = icon,
                                                contentDescription = "",
                                                tint = iconTint,
                                                modifier = Modifier
                                                    .align(Alignment.Center)
                                                    .toggleable(
                                                        value = isFilled ?: false,
                                                        onValueChange = { input ->
                                                            wishlistViewModel.onChangeFavourite(
                                                                change = input,
                                                                productId = products[shoeItem].id
                                                            )
                                                            isFilled = input
                                                            // voiceJournalPreviewViewModel.getNotes()
                                                        }
                                                    )
                                            )
                                        }
                                    }
                                }

                                Row(
                                    modifier = Modifier
                                        .padding(start = 4.dp, end = 4.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Bottom,

                                    ) {
                                    Column(
                                        modifier = Modifier.padding(start = 16.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp),
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Text(
                                            text = "Athletic/Sportswear",
// Text/md2: Regular
                                            style = TextStyle(
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight(400),
                                                color = Color(0xFF2A2A2A),

                                                )
                                        )
                                        Text(
                                            text = products[shoeItem].name,
                                            style = MaterialTheme.typography.headlineSmall,
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1,
                                            modifier = Modifier.size(
                                                width = 124.dp,
                                                height = 32.dp
                                            )
                                        )
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.star_half),
                                                contentDescription = "image description",
                                                contentScale = ContentScale.None
                                            )
                                            Text(
                                                text = "4.5 (100 sold)",
// Text/md2: Medium
                                                style = TextStyle(
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight(500),
                                                    color = Color(0xFF2A2A2A),

                                                    )
                                            )
                                        }
                                        Text(
                                            text = "₦ " + products[shoeItem].currentPrice[0].NGN[0].toString(),
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight(600),
                                                color = Color(0xFF0072C6),

                                                ),
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1
                                        )
                                        Text(
                                            text = "₦ " + (products[shoeItem].currentPrice[0].NGN[0].toString()
                                                .toDouble() + 2000).toString(),
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight(500),
                                                color = Color(0xFF9D9D9D),

                                                ),
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1,
                                            textDecoration = TextDecoration.LineThrough
                                        )
                                    }

                                    IconButton(
                                        /* colors = IconButtonDefaults.iconButtonColors(
                                             containerColor = Color(0x1F0072C6),
                                         ),*/
                                        onClick = {
                                            navController.navigate("details" + "?id=${products[shoeItem].id}&price=${products[shoeItem].currentPrice[0].NGN[0].toString()}")
                                        },
                                        modifier = Modifier
                                            .width(36.dp)
                                            .height(28.dp)
                                            .background(
                                                color = Color(0x1F0072C6),
                                                shape = RoundedCornerShape(size = 8.dp)
                                            )
                                            .padding(
                                                start = 12.dp,
                                                top = 8.dp,
                                                end = 12.dp,
                                                bottom = 8.dp
                                            )


                                    ) {
                                        Icon(
                                            tint = Color(0xff0072C6),
                                            painter = painterResource(id = R.drawable.shopping_basket_02),
                                            contentDescription = ""
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.padding(bottom = 16.dp))
                            }

                        }
                    }
                }

                else{
                  item(span = {
                      // LazyGridItemSpanScope:
                      // maxLineSpan
                      GridItemSpan(maxLineSpan)
                  }) {
EmptyWishlistScreen()

                  }
              }
            }

        }

        Status.ERROR -> DefaultErrorContent2(
            dataState.message, Modifier.align(Alignment.Center), viewModel = wishlistViewModel
        )

        Status.LOADING -> DefaultLoadingContent2(
            Modifier.align(Alignment.Center)
        )
    }
    PullRefreshIndicator(
        refreshing = isRefreshing,
        state = pullRefreshState,
        modifier = modifier.align(Alignment.TopCenter)
    )
}


}

@Composable
fun DefaultLoadingContent2(modifier: Modifier = Modifier) {
    androidx.compose.material.Text(
        text = "Loading...",
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}
@Composable
fun DefaultErrorContent2(message: String?, modifier: Modifier = Modifier,viewModel:WishlistViewModel) {
    val retry = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(retry.value) {
        viewModel.fetchProducts()
    }
    Column(modifier = modifier)  {
        androidx.compose.material.Text(
            text = message ?: "Error",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )

        Text(
            modifier = Modifier.clickable {
                retry.value = !retry.value
            },
            text = "retry",
            color = Color.Blue
        )
    }
}
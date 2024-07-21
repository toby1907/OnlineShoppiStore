package com.example.onlineshoppistore.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.onlineshoppistore.R
import com.example.onlineshoppistore.data.ResponseItem
import com.example.onlineshoppistore.network.Status
import com.example.onlineshoppistore.ui.cart.CartScreen
import com.example.onlineshoppistore.ui.components.BottomNavPanel
import com.example.onlineshoppistore.ui.order.MyOrderScreen
import com.example.onlineshoppistore.ui.wishlist.WishlistScreen
import com.example.onlineshoppistore.ui.wishlist.WishlistViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController, navController2: NavHostController ,
    productsViewModel: ProductsViewModel ,
    viewModel: WishlistViewModel ,
) {


    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.ag_ezenard),
                        contentDescription = ""
                    )
                },
                actions = {
                   /* IconButton(onClick = { navController.navigate("wishlist") }) {
                        Icon(
                            painter = painterResource(id = R.drawable.search_01),
                            contentDescription = "search"
                        )
                    }*/

                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomNavPanel(/*navController2=navController*/ navController2, viewModel)
        }
    )
    {

        NavHost(
            modifier = Modifier.padding(it),
            navController = navController2,
            startDestination = "home1"
        ) {
            composable(route = "home1"){
                Content( navController = navController, productsViewModel = productsViewModel)

            }
            composable(route = "cart"){
                CartScreen(navController,navController2)

            }
            composable(route = "all"){
                AllProductScreen(navController = navController)

            }
            composable(route = "info"){
                MyOrderScreen(navController = navController, navController2 = navController2)

            }
            composable(route = "wishlist"){
                WishlistScreen(navController = navController,viewModel)

            }

        }
    //    Content(modifier = Modifier.padding(it), navController = navController)

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Content(
    modifier: Modifier = Modifier,
    /*  viewModel: ProductViewModel ,*/
    navController: NavController,
    backgroundColor: Color = Color.Transparent,
    backgroundColorWhileUpdate: Color = Color.LightGray,
    productsViewModel: ProductsViewModel
) {
    //   val scope = rememberCoroutineScope()
    //  val value = viewModel.productsState.collectAsState().value
    val currentShoeBrand = remember { mutableStateOf(ShoeBrand.All) }
    val lowercaseValue = currentShoeBrand.value.name.lowercase()
    var errorMessage: String = ""


    val retry = remember {
        mutableStateOf(false)
    }
    /*LaunchedEffect(retry.value) {
        viewModel.fetchProducts()
    }*/


    val dataState by productsViewModel.products.collectAsState()
    // Directly infer the refreshing state from the resource status.
    val isRefreshing =
        dataState.status == Status.LOADING || dataState.status == Status.UPDATING
    val pullRefreshState = rememberPullRefreshState(isRefreshing, productsViewModel::fetchProducts)


    /* Spacer(modifier = Modifier.padding(top=16.dp))
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
     }*/


    Box(
        modifier = Modifier
            .background(if (dataState.status == Status.UPDATING) backgroundColorWhileUpdate else backgroundColor)
            .fillMaxSize()
            .pullRefresh(state = pullRefreshState),

        ) {
        val filteredItems = if (lowercaseValue!="all")  {
            dataState.data?.filter { item ->
                item.categories.any { category -> category.name == lowercaseValue }
            }
        }
        else{
            dataState.data
        }

        when (dataState.status) {
            Status.SUCCESS, Status.UPDATING -> {
                LazyVerticalGrid(
                    modifier = modifier,
                    columns = GridCells.Fixed(2),

                    ) {
                    item(span = {
                        // LazyGridItemSpanScope:
                        // maxLineSpan
                        GridItemSpan(maxLineSpan)
                    }) {
                        UserGreetings()

                    }
                    item(span = {
                        // LazyGridItemSpanScope:
                        // maxLineSpan
                        GridItemSpan(maxLineSpan)
                    }) {
                      val filteredPager =  dataState.data?.filter { item ->
                            item.categories.any { category -> category.name == "adidas" }
                        }
                        filteredPager?.let {
                            HorizontalPagerWithIndicators(
                                products = it,
                                navcontroller = navController
                            )
                        }
                    }
                    item(span = {
                        // LazyGridItemSpanScope:
                        // maxLineSpan
                        GridItemSpan(maxLineSpan)
                    }) {
                        SegmentedControl(
                            modifier = Modifier,
                            onNikePressed = {
                                currentShoeBrand.value = ShoeBrand.Nike
                            },
                            onAdidasPressed = {
                                currentShoeBrand.value = ShoeBrand.Adidas
                            },
                            onGucciPressed = {
                                currentShoeBrand.value = ShoeBrand.Gucci
                            },
                            onJordanPressed = {
                                currentShoeBrand.value = ShoeBrand.Jordan
                            },
                            onReebokPressed = {
                                currentShoeBrand.value = ShoeBrand.Reebok
                            },
                            onNewBalancedPressed = {
                                currentShoeBrand.value = ShoeBrand.NewBalance
                            },
                            onPumaPressed = {
                                currentShoeBrand.value = ShoeBrand.Puma
                            },
                            onAllPressed = {
                                currentShoeBrand.value = ShoeBrand.All
                            },
                        )

                    }
                    item(span = {
                        // LazyGridItemSpanScope:
                        // maxLineSpan
                        GridItemSpan(
                            maxLineSpan,
                        )
                    }) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "Our Special Offers",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF000000),

                                )
                        )
                    }

                    filteredItems?.let { products ->
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
                                        productsViewModel.isProductFavorite(products[shoeItem].id)
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
                                                            productsViewModel.onChangeFavourite(
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

            }

            Status.ERROR -> DefaultErrorContent(
                dataState.message, Modifier.align(Alignment.Center), viewModel = productsViewModel
            )

            Status.LOADING -> DefaultLoadingContent(
                Modifier.align(Alignment.Center)
            )
        }
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = modifier.align(Alignment.TopCenter)
        )

        /*LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(2),

            ) {

            items(value.size) { shoeItem ->

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable(onClick = {
                                navController.navigate("details" + "?id=${value[shoeItem].id}&price=${value[shoeItem].currentPrice[0].NGN[0].toString()}")
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.padding(start = 4.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = value[shoeItem].name,
                                style = MaterialTheme.typography.headlineSmall,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier.size(width = 124.dp, height = 32.dp)
                            )
                            Text(
                                text = value[shoeItem].currentPrice[0].NGN[0].toString() + " NGN",
                                style = MaterialTheme.typography.bodyMedium,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }

                        IconButton(onClick = {
                            navController.navigate("details" + "?id=${value[shoeItem].id}&price=${value[shoeItem].currentPrice[0].NGN[0].toString()}")
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_circle_right_24),
                                contentDescription = ""
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(bottom = 16.dp))
                }

            }
        }*/
    }
}


// Default composable functions for loading and error states for modular error handling and UI updates
@Composable
fun DefaultErrorContent(
    message: String?,
    modifier: Modifier = Modifier,
    viewModel: ProductsViewModel
) {
    val retry = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(retry.value) {
        viewModel.fetchProducts()
    }
    Column(modifier = modifier) {
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

@Composable
fun DefaultLoadingContent(modifier: Modifier = Modifier) {
    androidx.compose.material.Text(
        text = "Loading...",
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

data class Logo(val painter: Painter, val name: String)

@Composable
fun SegmentedControl(
    modifier: Modifier,
    onNikePressed: () -> Unit,
    onAdidasPressed: () -> Unit,
    onGucciPressed: () -> Unit,
    onJordanPressed: () -> Unit,
    onReebokPressed: () -> Unit,
    onNewBalancedPressed: () -> Unit,
    onPumaPressed: () -> Unit,
    onAllPressed: () -> Unit,
) {
    val items = listOf(
        Logo(painterResource(R.drawable.nike_logo), "Nike"),
        Logo(painterResource(R.drawable.adidas_logo), "Adidas"),
        Logo(painterResource(R.drawable.gucci), "Gucci"),
        Logo(painterResource(R.drawable.jordan), "Jordan"),
        Logo(painterResource(R.drawable.reebok), "Reebok"),
        Logo(painterResource(R.drawable.resource_new), "New Balance"),
        Logo(painterResource(R.drawable.puma_logo), "Puma"),
        Logo(painterResource(R.drawable.view_all), " ")
    )
    var selectedItemIndex by remember { mutableStateOf(7) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 42.dp, top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (index in 0 until 4) {
                if (index < items.size) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .clickable {
                                    selectedItemIndex = index
                                    when (index) {
                                        0 -> {
                                            onNikePressed()
                                        }

                                        1 -> {
                                            onAdidasPressed()
                                        }

                                        2 -> {
                                            onGucciPressed()
                                        }

                                        3 -> {
                                            onJordanPressed()
                                        }

                                    }
                                }
                                .width(48.dp)
                                .height(48.dp)
                                .background(
                                    color = if (selectedItemIndex == index) Color(0xff0072C6) else Color(
                                        0xFFF9F9F9
                                    ),
                                    shape = RoundedCornerShape(size = 40.dp)
                                )

                                .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 12.dp)
                        ) {
                            Image(
                                painter = items[index].painter,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                colorFilter = if (selectedItemIndex == index) ColorFilter.tint(
                                    Color(
                                        0xffffffff
                                    )
                                ) else ColorFilter.tint(
                                    Color.Gray
                                )
                            )

                        }
                        Text(
                            text = items[index].name,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (selectedItemIndex == index) Color(0xff0072C6) else Color.Gray
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (index in 4 until 8) {
                if (index < items.size) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .clickable {
                                    selectedItemIndex = index
                                    when (index) {
                                        4 -> {
                                            onReebokPressed()
                                        }

                                        5 -> {
                                            onNewBalancedPressed()
                                        }

                                        6 -> {
                                            onPumaPressed()
                                        }

                                        7 -> {
                                            onAllPressed()
                                        }
                                    }
                                }
                                .width(48.dp)
                                .height(48.dp)
                                .background(
                                    color = if (selectedItemIndex == index) Color(0xff0072C6) else Color(
                                        0xFFF9F9F9
                                    ),
                                    shape = RoundedCornerShape(size = 40.dp)
                                )
                                .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 12.dp)
                        ) {
                            Image(
                                painter = items[index].painter,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                colorFilter = if (selectedItemIndex == index) ColorFilter.tint(
                                    Color(
                                        0xffffffff
                                    )
                                ) else ColorFilter.tint(
                                    Color.Gray
                                )
                            )

                        }
                        Text(
                            text = items[index].name,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (selectedItemIndex == index) Color(0xff0072C6) else Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HorizontalPagerWithIndicators(products: List<ResponseItem>, navcontroller: NavController) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val gradient = Brush.linearGradient(
        colors = listOf(Color(0xff0072C6), Color(0xff003760)), // Specify your gradient colors

    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(200.dp)
            .background(shape = RoundedCornerShape(8.dp), brush = gradient),
        contentAlignment = Alignment.Center,
    ) {
        HorizontalPager(
            count = products.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) { page ->
            Column {
                DisplayProduct(product = products[page], navcontroller = navcontroller)
            }
        }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }
                .collect { currentPage ->
                    delay(5000) // Wait for 5 seconds
                    val nextPage = (currentPage + 1) % products.size
                    pagerState.animateScrollToPage(nextPage)
                }
        }


        HorizontalPagerIndicator(
            pageCount = products.size,
            pagerState = pagerState,
            activeColor = Color(0xFFFFFFFF),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 4.dp)
                .clickable {
                    val currentPage = pagerState.currentPage
                    val totalPages = products.size
                    val nextPage =
                        if (currentPage < totalPages - 1) currentPage + 1 else 0
                    coroutineScope.launch { pagerState.animateScrollToPage(nextPage) }
                }
        )

    }

}

@Composable
fun DisplayProduct(product: ResponseItem, navcontroller: NavController) {
    val gradient = Brush.linearGradient(
        colors = listOf(Color(0xff0072C6), Color(0xff003760)), // Specify your gradient colors

    )
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        FetchImageFromUrl(imageUrl = "https://api.timbu.cloud/images/${product.photos[0].url}")
        Column(
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.End,
            modifier = Modifier
                .align(Alignment.CenterEnd)
        ) {
            Text(
                text = product.name + " ₦ ${product.currentPrice[0].NGN[0]}",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFFEAEAEA),

                    )
            )
            Button(
                onClick = { navcontroller.navigate("cart") }, modifier = Modifier
                    .width(113.dp)
                    .height(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffffffff),
                ),
                shape = RoundedCornerShape(size = 8.dp)

            ) {
                Text(
                    text = "Add to Cart",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF0072C6),

                        )
                )
            }
        }
    }

}

@Composable
fun FetchImageFromUrl(imageUrl: String) {
    val painter: Painter = rememberAsyncImagePainter(imageUrl)
    Image(
        painter = painter, contentDescription = null,
        modifier = Modifier
            .size(154.dp)
            .offset(x = (-100).dp, y = 48.dp),
    )
}

@Composable
fun UserGreetings() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.padding(8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(49.dp)
                .height(48.dp)
                .background(
                    color = Color(0xFFE89705),
                    shape = RoundedCornerShape(size = 46.12828.dp)
                )
                .border(
                    width = 2.dp,
                    color = Color(0xFFFFA500),
                    shape = RoundedCornerShape(size = 46.12828.dp)
                )
        )
        {
            Text(
                text = "Hello".getOrNull(0).toString()
                    .toUpperCase(Locale.getDefault()),
                style = TextStyle(
                    fontSize = 19.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF2A2A2A),

                    )
            )
        }

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Welcome 👋🏽",
// Text/lg: Regular
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF707070),

                    )
            )
           /* Text(
                text = "Ada Dennis",
// Heading/H4: Medium
                style = TextStyle(
                    fontSize = 19.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF2A2A2A),

                    )
            )*/
        }
    }
    Spacer(modifier = Modifier.padding(8.dp))
}









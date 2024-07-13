package com.example.onlineshoppistore.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.onlineshoppistore.R
import com.example.onlineshoppistore.data.Photo
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductPage(navController: NavController) {
    val viewModel: DetailScreenViewModel = hiltViewModel()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    )
    {

        NewProductContent(modifier = Modifier, viewModel=viewModel, navController = navController)
        TopAppBar(
            modifier = Modifier.align(Alignment.TopStart),title = { }, navigationIcon = {
            IconButton(
                onClick = {navController.navigateUp()},

            ) {
                Icon(painter = painterResource(id = R.drawable.arrow_left_02), contentDescription = "")
            }
        },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            ))
    }

}

@Composable
fun NewProductContent(modifier: Modifier, viewModel: DetailScreenViewModel,navController: NavController) {
    val product = viewModel.productState.collectAsState().value
    val errorState = viewModel.productError.collectAsState().value
    val loadingState = viewModel.productloading.collectAsState().value
    val dataState by viewModel.products.collectAsState()
    val retry = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(retry.value) {
        viewModel.fetchProduct()
    }

    if (errorState.state) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize())
        {
            Column {
                androidx.compose.material3.Text(text = errorState.message)
                androidx.compose.material3.Text(text = "Retry",
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        retry.value = !retry.value
                    }
                )
            }
        }
    }

    if (loadingState) {

        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            androidx.compose.material3.CircularProgressIndicator(
                modifier = Modifier.size(48.dp)
            )
        }
    }
    if (!loadingState && !errorState.state) {

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize(),
            columns = GridCells.Fixed(2)) {
            item(span = {
                // LazyGridItemSpanScope:
                // maxLineSpan
                GridItemSpan(maxLineSpan)
            }) {
                

                SneakerImageCarousel(photos = product.imageUrl)

            }
            item(span = {
                // LazyGridItemSpanScope:
                // maxLineSpan
                GridItemSpan(maxLineSpan)
            }) {

            Spacer(modifier = Modifier.size(16.dp))
                ProductTitleSection(
                    title = product.name,
                    price = product.price,
                    rating = 4.5f
                )
            }
            item(span = {
                // LazyGridItemSpanScope:
                // maxLineSpan
                GridItemSpan(maxLineSpan)
            }) {

            Spacer(modifier = Modifier.size(16.dp))
                DescriptionReviewsTabs(
                    product.description
                )
            }
            item(span = {
                // LazyGridItemSpanScope:
                // maxLineSpan
                GridItemSpan(maxLineSpan)
            }) {

            Spacer(modifier = Modifier.size(16.dp))
                val sizes = listOf(38, 39, 40, 42, 45)
                var selectedSize by remember { mutableStateOf<Int?>(39) }

                SizePicker(
                    sizes = sizes,
                    selectedSize = selectedSize,
                    onSizeSelected = { selectedSize = it }
                )
            }


            item(span = {
                // LazyGridItemSpanScope:
                // maxLineSpan
                GridItemSpan(maxLineSpan)
            }) {

            Spacer(modifier = Modifier.size(16.dp))
                val colors = listOf(Color(0xFFFFA500), Color(0xFF9747FF), Color(0xFF09C53B), Color(0xFF141B34))
                var selectedColor by remember { mutableStateOf<Color?>(Color(0xFFFFA500)) }

                ColorPicker(
                    colors = colors,
                    selectedColor = selectedColor,
                    onColorSelected = { selectedColor = it }
                )
            }
            item(span = {
                // LazyGridItemSpanScope:
                // maxLineSpan
                GridItemSpan(maxLineSpan)
            }) {


                QuantitySelector(
                    initialQuantity = 1,
                    onQuantityChanged = {
                    }
                )
            }

            item(span = {
                // LazyGridItemSpanScope:
                // maxLineSpan
                GridItemSpan(maxLineSpan)
            }) {
                Text(
                    text = "More from Ego",
                    modifier = Modifier
                        .padding(start = 8.dp, top = 24.dp, bottom = 16.dp)
                        .clickable {
                            viewModel.fetchProducts()
                        },

                    // Heading/H3: Medium
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                    )
                )
            }
            dataState.data?.let { products ->
                items(products.size) { shoeItem ->

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .width(168.5.dp)
                                    .height(180.dp)
                                    .clickable(onClick = {
                                        navController.navigate("details" + "?id=${products[shoeItem].id}&price=${products[shoeItem].currentPrice[0].NGN[0].toString()}")
                                    }
                                    ),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor =  Color(0x66EAEAEA))
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .clip(shape = RoundedCornerShape(8.dp)),
                                    model = "https://api.timbu.cloud/images/${products[shoeItem].photos[0].url}",
                                    contentDescription = null,
                                )

                            }
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(x = (-20).dp, y = (16).dp)
                                    .width(32.dp)
                                    .height(32.dp)
                                    .background(
                                        color = Color(0x99000000),
                                        shape = RoundedCornerShape(size = 32.dp)
                                    )

                                    .padding(
                                        start = 6.4.dp,
                                        top = 6.4.dp,
                                        end = 6.4.dp,
                                        bottom = 6.4.dp
                                    )
                            ) {

                                androidx.compose.material3.Icon(
                                    painter = painterResource(id = R.drawable.favorite_fill0_wght400_grad0_opsz24),
                                    contentDescription = "",
                                    tint = Color.White
                                )
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
                                androidx.compose.material3.Text(
                                    text = "Athletic/Sportswear",
// Text/md2: Regular
                                    style = TextStyle(
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF2A2A2A),

                                        )
                                )
                                androidx.compose.material3.Text(
                                    text = products[shoeItem].name,
                                    style = MaterialTheme.typography.headlineSmall,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    modifier = Modifier.size(
                                        width = 124.dp,
                                        height = 32.dp
                                    )
                                )
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp,)) {
                                    Image(
                                        painter = painterResource(id = R.drawable.star_half),
                                        contentDescription = "image description",
                                        contentScale = ContentScale.None
                                    )
                                    androidx.compose.material3.Text(
                                        text = "4.5 (100 sold)",
// Text/md2: Medium
                                        style = TextStyle(
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight(500),
                                            color = Color(0xFF2A2A2A),

                                            )
                                    )
                                }
                                androidx.compose.material3.Text(
                                    text = "₦ " + products[shoeItem].currentPrice[0].NGN[0].toString(),
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight(600),
                                        color = Color(0xFF0072C6),

                                        ),
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                                androidx.compose.material3.Text(
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

                            androidx.compose.material3.IconButton(
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
                                androidx.compose.material3.Icon(
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
            item(span = {
                // LazyGridItemSpanScope:
                // maxLineSpan
                GridItemSpan(maxLineSpan)
            }) {

                Footer(navController = navController,product.price)
            }


            }

    }
}

@Composable
fun SneakerImageCarousel(photos: List<Photo>) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            // .width(390.dp)
            .height(399.dp)
            .background(color = Color(0x66EAEAEA)),
        contentAlignment = Alignment.Center,
    ) {
        HorizontalPager(
            count = photos.size,
            state = pagerState,
        ) { page ->
            Column {
                DisplayPhoto(photo = photos[page])
            }
        }

        /* LaunchedEffect(pagerState) {
             snapshotFlow { pagerState.currentPage }
                 .collect { currentPage ->
                     delay(5000) // Wait for 5 seconds
                     val nextPage = (currentPage + 1) % photos.size
                     pagerState.animateScrollToPage(nextPage)
                 }
         }*/


        HorizontalPagerIndicator(
            pageCount = photos.size,
            pagerState = pagerState,
            activeColor = Color(0xFF0072C6),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 4.dp)
                .clickable {
                    val currentPage = pagerState.currentPage
                    val totalPages = photos.size
                    val nextPage =
                        if (currentPage < totalPages - 1) currentPage + 1 else 0
                    coroutineScope.launch { pagerState.animateScrollToPage(nextPage) }
                }
        )

    }

}

@Composable
fun DisplayPhoto(photo: Photo) {
    Box(
        modifier = Modifier
            .width(239.99997.dp)
            .height(239.99997.dp),
        contentAlignment = Alignment.Center,
    ) {
        FetchPhotoFromUrl(imageUrl = "https://api.timbu.cloud/images/${photo.url}")
    }

}

@Composable
fun FetchPhotoFromUrl(imageUrl: String) {
    val painter: Painter = rememberAsyncImagePainter(imageUrl)
    Image(
        painter = painter, contentDescription = null,
        modifier = Modifier
            .width(239.99997.dp)
            .height(239.99997.dp)

    )
}

@Composable
fun ProductTitleSection(title: String, price: String, rating: Float) {
    Column(verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(start = 8.dp)
    ){
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "Iconic Casual Brands",
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF2A2A2A),
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title, style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF2A2A2A),

                    )
            )
            Box(
                modifier = Modifier
                    .width(30.8.dp)
                    .height(30.8.dp)
                    .background(color = Color(0xFFD42620), shape = RoundedCornerShape(size = 32.dp))
                    .padding(start = 6.4.dp, top = 6.4.dp, end = 6.4.dp, bottom = 6.4.dp)

            ) {

                androidx.compose.material3.Icon(
                    modifier = Modifier.align(Alignment.Center),
                    painter = painterResource(id = R.drawable.favourite),
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
        Text(
            text = "₦ $price", style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF2A2A2A),

                )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "100 sold",
                // Text/md: Medium
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF555555),
                )
            )

            Image(
                painter = painterResource(id = R.drawable.star_half),
                contentDescription = "Star",
            )

            Text(
                text = "4.5 (32 reviews)",

                // Text/md: Medium
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF555555),
                )
            )
        }
    }
}




            @Composable
            fun SizePicker(
                sizes: List<Int>,
                selectedSize: Int?,
                onSizeSelected: (Int) -> Unit
            ) {
                Column(verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.Start){
                    Text(
                        text = "Size",

                        // Text/lg: Medium
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF2A2A2A),
                        )
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        sizes.forEach { size ->
                            SizeItem(
                                size = size,
                                isSelected = size == selectedSize,
                                onClick = { onSizeSelected(size) }
                            )
                        }
                    }
                }
            }

    @Composable
    fun SizeItem(
        size: Int,
        isSelected: Boolean,
        onClick: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .size(60.dp, 40.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(if (isSelected) Color(0xFF0072C6) else Color(0xFFF9F9F9))
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = size.toString(),
                color = if (isSelected) Color.White else Color(0xff555555),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

        }
    }



@Composable
fun QuantitySelector(
    initialQuantity: Int,
    onQuantityChanged: (Int) -> Unit
) {
    var quantity by remember { mutableStateOf(initialQuantity) }

    Column(verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(start = 8.dp)
    ){
        Spacer(modifier = Modifier.size(16.dp))
       Text(
           text = "Quantity",

           // Text/lg: Medium
           style = TextStyle(
               fontSize = 15.sp,
               fontWeight = FontWeight(500),
               color = Color(0xFF2A2A2A),
           )
       )
        Spacer(modifier = Modifier.size(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(id = R.drawable.minus_sign),
                contentDescription = "Decrease Quantity",
                tint = Color.Gray,
                modifier = Modifier
                    .size(16.dp)
                    .clickable { if (quantity > 1) quantity-- }
            )

            Box(
                modifier = Modifier
                    .width(25.dp)
                    .height(26.dp)
                    .background(color = Color(0x1F0072C6), shape = RoundedCornerShape(size = 2.dp))


            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = quantity.toString(),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF2A2A2A),

                        )
                )
            }
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase Quantity",
                tint = Color.Gray,
                modifier = Modifier
                    .size(16.dp)
                    .clickable {
                        if (quantity < 10) {
                            quantity++
                        }
                    }
            )
        }
    }

    // Notify the parent when the quantity changes
    onQuantityChanged(quantity)
}






@Composable
fun ColorPicker(
    colors: List<Color>,
    selectedColor: Color?,
    onColorSelected: (Color) -> Unit
) {
    Column(verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.Start){
        Text(
            text = "Colours",
// Text/lg: Medium
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF2A2A2A),

                )
        )
        Spacer(modifier = Modifier.size(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            colors.forEach { color ->
                ColorItem(
                    color = color,
                    isSelected = color == selectedColor,
                    onClick = { onColorSelected(color) }
                )
            }
        }
    }
}

@Composable
fun ColorItem(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = Color.White
            )
        }
    }
}


@Composable
fun DescriptionReviewsTabs(description: String) {
    Column(verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(start = 8.dp, top = 8.dp)
    ){
        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = "Description",
// Text/lg: Medium
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF2A2A2A),

                )
        )
        Spacer(modifier = Modifier.size(8.dp))
        androidx.compose.material3.Text(
            modifier = Modifier
                .width(358.dp)
                .height(108.dp),
            text = description,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF555555),

                )
        )

    }
}

@Composable
fun  Footer(navController: NavController,price: String){
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(8.dp)
        ){
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
                text = "₦ $price",
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
            .height(42.dp)
            ,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF0072C6),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(size = 8.dp),
             onClick = { navController.navigate("cart") }) {
            Icon(painter = painterResource(id = R.drawable.cart_icon), contentDescription ="" )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Add to Cart",

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

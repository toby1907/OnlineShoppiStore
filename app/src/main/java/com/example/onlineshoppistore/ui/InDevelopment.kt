package com.example.onlineshoppistore.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onlineshoppistore.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndevelopmentScreen(navController: NavController){

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start){
        TopAppBar(navigationIcon = {
            IconButton(onClick = {navController.navigateUp() }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_left_02),
                    contentDescription = ""
                )
            }
        },
            title = { Text(modifier = Modifier.align(Alignment.CenterHorizontally),
                text = " ",

                // Heading/H4: SemiBold
                style = TextStyle(
                    fontSize = 19.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF292929),
                )
            ) })
       Text(text = "Under Construction")
    }
}
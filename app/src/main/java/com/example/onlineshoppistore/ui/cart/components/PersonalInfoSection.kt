package com.example.onlineshoppistore.ui.cart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onlineshoppistore.R
import com.example.onlineshoppistore.ui.checkout.CheckoutViewModel
import com.example.onlineshoppistore.ui.components.MaskedCardNumber

@Composable
fun PersonalInfoSection(editSheet: () -> Unit,
                        added:Boolean,
                        itsAdded:Boolean,
                        paymentMethodSheet: () -> Unit,
                        viewModel: CheckoutViewModel,
                        totalPrice : Double
) {

   val paymentText = when(added){
        true -> {
            "Change"
        }
        false -> "Add"
    }

val personalInfoText = when(itsAdded){
    true -> { "Edit"}
    false -> {"Add"}
}
   Column {
       Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,
           modifier = Modifier.fillMaxWidth()) {
           Text(
               text = "Payment Method",
// Text/lg: Medium
               style = TextStyle(
                   fontSize = 15.sp,
                   fontWeight = FontWeight(500),
                   color = Color(0xFF2A2A2A),

                   )
           )
           Text(
               text = paymentText,

               // Text/lg: SemiBold
               style = TextStyle(
                   fontSize = 15.sp,
                   fontWeight = FontWeight(600),
                   color = Color(0xFF0072C6),
               ),
               modifier = Modifier.clickable {
                   paymentMethodSheet()
               }
           )
       }

       Spacer(modifier = Modifier.size(16.dp))
       Row(verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.SpaceBetween,
           modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp,Alignment.Start))   {
               Image(
                   painter = painterResource(id = R.drawable.payment_method_icon),
                   contentDescription = "",
                   contentScale = ContentScale.None,
                   modifier = Modifier.size(width = 32.dp, height = 32.dp)
               )
               Column(
                   verticalArrangement = Arrangement.SpaceBetween,
                   horizontalAlignment = Alignment.Start
               ) {
                   MaskedCardNumber(viewModel.cardNo.value.cardNo)
                   Text(
                       text = "${viewModel.month.value.month}/${viewModel.year.value.year}",
                       // Text/md: Medium
                       style = TextStyle(
                           fontSize = 16.sp,
                           fontWeight = FontWeight(500),
                           color = Color(0xFF1D2939),
                       )
                   )

               }
           }

           RadioButton(selected = true, onClick = { },
               colors = RadioButtonDefaults.colors(
                  selectedColor =Color(0xff099137) ,
                   unselectedColor = Color(0xff099137)

               )
               )
       }
   }
    Column {

        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Personal information",
// Text/lg: Medium
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF2A2A2A),

                    )
            )
            Text(
                text = "Edit",

                // Text/lg: SemiBold
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF0072C6),
                ),
                modifier = Modifier.clickable {
                    editSheet()
                }
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFFF9F9F9),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .width(358.dp)
                .height(74.dp)
                .background(color = Color(0xFFF9F9F9), shape = RoundedCornerShape(size = 8.dp))
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
         Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,
             modifier = Modifier.fillMaxWidth())   {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.contact),
                        contentDescription = "contact name"
                    )
                    Text(
                        text = viewModel.name.value.name,

                        // Text/md: Medium
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF2A2A2A),
                        )
                    )
                }

             Row(
                 verticalAlignment = Alignment.CenterVertically,
                 horizontalArrangement = Arrangement.spacedBy(4.dp)
             ) {
                 Icon(
                     painter = painterResource(id = R.drawable.smart_phone_02),
                     contentDescription = "contact name"
                 )
                 Text(
                     text = viewModel.phoneNo.value.phoneNo,

                     // Text/md: Medium
                     style = TextStyle(
                         fontSize = 12.sp,
                         fontWeight = FontWeight(500),
                         color = Color(0xFF2A2A2A),
                     )
                 )
             }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.contact),
                    contentDescription = "contact name"
                )
                Text(
                    text = viewModel.email.value.email,

                    // Text/md: Medium
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF2A2A2A),
                    )
                )
            }


        }
    }
    Column {

        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Delivery option",

                // Text/lg: Medium
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF2A2A2A),
                )
            )

        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFFF9F9F9),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .width(358.dp)
                .height(74.dp)
                .background(color = Color(0xFFF9F9F9), shape = RoundedCornerShape(size = 8.dp))
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth())   {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.delivery_tracking_01),
                        contentDescription = "contact name"
                    )
                    Text(
                        text = "Pick up point",

                        // Text/md: Medium
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF2A2A2A),
                        )
                    )
                }

                    Text(
                        text = viewModel.city.value.city,

                        // Text/md: Medium
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF2A2A2A),
                        )
                    )

            }

        }
    }
    Column {

        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Price summary",

                // Text/lg: Medium
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF2A2A2A),
                )
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFFF9F9F9),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .width(358.dp)
                .height(74.dp)
                .background(color = Color(0xFFF9F9F9), shape = RoundedCornerShape(size = 8.dp))
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth())   {
                Text(
                    text = "Total price",

                    // Text/lg: Regular
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF555555),
                    ),
                    modifier = Modifier
                        .width(226.dp)
                        .height(18.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                )  {
                    Text(
                        text = "₦",

                        // Text/lg: Medium
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF2A2A2A),
                        )
                    )
                    Text(
                        text = totalPrice.toString(),

                        // Text/lg: Medium
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF2A2A2A),
                        )
                    )
                }

            }

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth())   {
                Text(
                    text = "Delivery fee",

                    // Text/lg: Regular
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF555555),
                    ),
                    modifier = Modifier
                        .width(226.dp)
                        .height(18.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,

                )  {
                    Text(
                        text = "₦",

                        // Text/lg: Medium
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF2A2A2A),
                        )
                    )
                    Text(
                        text = "1,550.00",

                        // Text/lg: Medium
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF2A2A2A),
                        )
                    )
                }

            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth())   {
                Text(
                    text = "Discount",

                    // Text/lg: Regular
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF555555),
                    ),
                    modifier = Modifier
                        .width(226.dp)
                        .height(18.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "₦",

                        // Text/lg: Medium
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF2A2A2A),
                        )
                    )
                    Text(
                        text = "0.00",

                        // Text/lg: Medium
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF2A2A2A),
                        )
                    )
                }

            }
            Spacer(modifier = Modifier.size(20.dp))
            Divider(modifier = Modifier
                .padding(0.dp)
                .width(326.dp)
                .height(1.dp)
                .background(color = Color(0xFFEAEAEA)))

            Spacer(modifier = Modifier.size(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth())   {
                Text(
                    text = "Total",

                    // Text/lg: Regular
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF555555),
                    ),
                    modifier = Modifier
                        .width(226.dp)
                        .height(18.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "₦",

                        // Text/lg: Medium
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF2A2A2A),
                        )
                    )
                    Text(
                        text =totalPrice.toString(),

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
}

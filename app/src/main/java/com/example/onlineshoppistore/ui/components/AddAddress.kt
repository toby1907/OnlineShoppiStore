package com.example.onlineshoppistore.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlineshoppistore.R
import com.example.onlineshoppistore.ui.checkout.CheckoutScreenEvent
import com.example.onlineshoppistore.ui.checkout.CheckoutViewModel
import java.util.Calendar

@Composable
fun AddAddress(viewModel: CheckoutViewModel){
    Column(verticalArrangement = Arrangement.spacedBy(8.dp,),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(16.dp)) {

       Column(verticalArrangement = Arrangement.spacedBy(4.dp), horizontalAlignment = Alignment.Start) {
           Text(
               text = "Name",
// Text/md: Regular
               style = TextStyle(
                   fontSize = 12.sp,
                   fontWeight = FontWeight(400),
                   color = Color(0xFF2A2A2A),

                   )
           )


            OutlinedTextField(
                value = viewModel.name.value.name //viewModel.lastName,
                ,
                onValueChange = {
                    //   viewModel.onEvent(AddInfoEvent.EnteredLastName(it))
                    viewModel.onEvent(CheckoutScreenEvent.EnteredName(it))
                },
                placeholder = {
                    Text(
                        text = "Enter your full name",
// Text/md: Regular
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF9D9D9D),

                            )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)

        ) {

            Column(verticalArrangement = Arrangement.spacedBy(4.dp), horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Prefix",
// Text/md: Regular
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF2A2A2A),

                        )
                )
                OutlinedTextField(
                    modifier = Modifier.size(
                        width = 100.dp, height = 58.dp
                    ),
                    value = "+234",
                    onValueChange = {
                        // viewModel.onEvent(AddChallengeEvent.Frequency(it))
                    },
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.expand_more_fill0_wght400_grad0_opsz24),
                            contentDescription = "",
                            modifier = Modifier.clickable {
                                //    showFrequencyDialog.value = true
                            },
                        )
                    },
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp), horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Phone Number",
// Text/md: Regular
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF2A2A2A),

                        )
                )
                OutlinedTextField(
                    value = viewModel.phoneNo.value.phoneNo,
                    onValueChange = {
                        viewModel.onEvent(CheckoutScreenEvent.EnteredPhoneNo(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "Enter your Phone No",
// Text/md: Regular
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF9D9D9D),

                                )
                        )
                    },
                )
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp), horizontalAlignment = Alignment.Start) {
            androidx.compose.material3.Text(
                text = "Email",
// Text/md: Regular
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 12.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight(400),
                    color = androidx.compose.ui.graphics.Color(0xFF2A2A2A),

                    )
            )
            OutlinedTextField(
                value =viewModel.email.value.email //viewModel.lastName,
                ,
                onValueChange = {
                    //   viewModel.onEvent(AddInfoEvent.EnteredLastName(it))
                    viewModel.onEvent(CheckoutScreenEvent.EnteredEmail(it))
                },

                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Enter your email address",
// Text/md: Regular
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF9D9D9D),

                            )
                    )
                },
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp), horizontalAlignment = Alignment.Start) {
            androidx.compose.material3.Text(
                text = "Address",
// Text/md: Regular
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 12.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight(400),
                    color = androidx.compose.ui.graphics.Color(0xFF2A2A2A),

                    )
            )
            OutlinedTextField(
                value =viewModel.address.value.address //viewModel.lastName,
                ,
                onValueChange = {
                    //   viewModel.onEvent(AddInfoEvent.EnteredLastName(it))
                    viewModel.onEvent(CheckoutScreenEvent.EnteredAddress(it))
                },

                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Enter your Address",
// Text/md: Regular
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF9D9D9D),

                            )
                    )
                },
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp), horizontalAlignment = Alignment.Start) {
            androidx.compose.material3.Text(
                text = "City",
// Text/md: Regular
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 12.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight(400),
                    color = androidx.compose.ui.graphics.Color(0xFF2A2A2A),

                    )
            )
            OutlinedTextField(
                value = viewModel.city.value.city //viewModel.lastName,
                ,
                onValueChange = {
                    //   viewModel.onEvent(AddInfoEvent.EnteredLastName(it))
                    viewModel.onEvent(CheckoutScreenEvent.EnteredCity(it))
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Enter your City",
// Text/md: Regular
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF9D9D9D),

                            )
                    )
                },
            )
        }

    }
}
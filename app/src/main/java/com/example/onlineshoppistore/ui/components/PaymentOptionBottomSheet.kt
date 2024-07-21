package com.example.onlineshoppistore.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlineshoppistore.ui.cart.CartScreenViewModel
import com.example.onlineshoppistore.ui.checkout.CheckoutViewModel
import kotlinx.coroutines.DisposableHandle
import com.example.onlineshoppistore.R
import com.example.onlineshoppistore.ui.checkout.CheckoutScreenEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    windowInsets: WindowInsets,
    onClick: () -> DisposableHandle,
    viewModel: CheckoutViewModel
) {
    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        sheetState = sheetState,
        windowInsets = windowInsets,

        ) {
        Row(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,

    ) { Text(
            text = "Personal Information",

            // Heading/H4: Medium
            style = TextStyle(
                fontSize = 19.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF2A2A2A),
            )
        )
            Icon(painter = painterResource(id = R.drawable.close_fill0_wght400_grad0_opsz24),
                contentDescription = "close",
                modifier = Modifier.clickable {
                    onClick()
                }
                )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
                Spacer(modifier = Modifier.padding(8.dp))


                LazyColumn {

                    item {
                        AddAddress(viewModel = viewModel)
                    }
                    item{
                     Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
                         modifier = Modifier.fillMaxWidth())   {
                            Button(modifier = Modifier
                                .width(141.dp)
                                .height(42.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF0072C6),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(size = 8.dp),
                                onClick = {
                                    onClick()
                                }) {

                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    text = "Done",

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
                }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentOptionBottomSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    windowInsets: WindowInsets,
    onClick: () -> DisposableHandle,
    viewModel: CheckoutViewModel
) {

    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        sheetState = sheetState,
        windowInsets = windowInsets,

        ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) { Text(
            text = "Payment Method",

            // Heading/H4: Medium
            style = TextStyle(
                fontSize = 19.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF2A2A2A),
            )
        )
            Icon(painter = painterResource(id = R.drawable.close_fill0_wght400_grad0_opsz24),
                contentDescription = "close",
                modifier = Modifier.clickable {
                    onClick()
                }
                )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(8.dp))
            val (selectedOption, onOptionSelected) = remember { mutableStateOf("Pay with Mastercard and Visa") }
            Column(Modifier.selectableGroup()) {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = ("Pay with Mastercard and Visa" == selectedOption),
                            onClick = {
                                onOptionSelected("Pay with Mastercard and Visa")
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
                ) {
                    RadioButton(
                        selected = ("Pay with Mastercard and Visa" == selectedOption),
                        onClick = null // null recommended for accessibility with screenreaders
                    )
                    Text(
                        text = "Pay with Mastercard and Visa",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.payment_method_icon),
                        contentDescription = "",
                        contentScale = ContentScale.None,
                        modifier = Modifier.size(width = 32.dp, height = 32.dp)
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = ("BankTransfer" == selectedOption),
                            onClick = {
                                onOptionSelected("BankTransfer")
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
                ) {
                    RadioButton(
                        selected = ("BankTransfer" == selectedOption),
                        onClick = null // null recommended for accessibility with screenreaders
                    )
                    Text(
                        text = "BankTransfer",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start){
                        Icon(painter = painterResource(id = R.drawable.account_balance_24dp_5f6368_fill0_wght400_grad0_opsz24), contentDescription ="" )
                        Icon(painter = painterResource(id = R.drawable.phone_android_24dp_5f6368_fill0_wght400_grad0_opsz24), contentDescription ="" )
                    }
                }

                if (selectedOption=="Pay with Mastercard and Visa") {
                    AddPaymentCartScreen(
                        viewModel =viewModel
                    )
                }

            }



        }
    }
}

@Composable
fun AddPaymentCartScreen(viewModel: CheckoutViewModel){
    val text = remember {
        mutableStateOf("")
    }
   Column(verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.Start) {
       TextField(value = viewModel.cardNo.value.cardNo, onValueChange = {viewModel.onEvent(CheckoutScreenEvent.EnteredCardNo(it))
       }, modifier = Modifier
           .fillMaxWidth()
           .padding(start = 16.dp, end = 16.dp)
           , placeholder = {
           Text(text = "Card Number 12 digits")
       },
           colors = TextFieldDefaults.colors(
               focusedContainerColor = Color.Transparent,
               unfocusedContainerColor = Color.Transparent,

           )
       )
       Spacer(modifier = Modifier.size(8.dp))
      Column(verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.Start) {
          Text(
              text = "Expire Date",

              // Text/md: Regular
              style = TextStyle(
                  fontSize = 12.sp,
                  fontWeight = FontWeight(400),
                  color = Color(0xFF2A2A2A),
              ),
              modifier = Modifier.padding(start = 16.dp)
          )
           Row(
               verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement = Arrangement.Start,
               modifier = Modifier.fillMaxWidth()
           ) {
               TextField(value = viewModel.month.value.month, onValueChange = {
                   viewModel.onEvent(CheckoutScreenEvent.EnteredMonth(it))
               }, modifier = Modifier
                   .size(height = 58.dp, width = 120.dp),
                   placeholder = {
                   Text(text = "MM")
               },
                   colors = TextFieldDefaults.colors(
                       focusedContainerColor = Color.Transparent,
                       unfocusedContainerColor = Color.Transparent,

                       ),
                   trailingIcon = {
                       Icon(
                           painter = painterResource(id = R.drawable.expand_more_fill0_wght400_grad0_opsz24),
                           contentDescription = ""
                       )
                   }
               )
               TextField(value = viewModel.year.value.year,
                   onValueChange = {
                       viewModel.onEvent(CheckoutScreenEvent.EnteredYear(it))
                   }, modifier = Modifier
                       .size(height = 58.dp, width = 120.dp)
                       ,
                   placeholder = {
                       Text(text = "YY")
                   },
                   colors = TextFieldDefaults.colors(
                       focusedContainerColor = Color.Transparent,
                       unfocusedContainerColor = Color.Transparent,

                       ),
                   trailingIcon = {
                       Icon(
                           painter = painterResource(id = R.drawable.expand_more_fill0_wght400_grad0_opsz24),
                           contentDescription = ""
                       )
                   }
               )
               TextField(value = viewModel.cvv.value.cvv,
                   onValueChange = {
                       viewModel.onEvent(CheckoutScreenEvent.EnteredCvv(it))
                   }, modifier = Modifier
                       .size(height = 58.dp, width = 140.dp),
                   placeholder = {
                       Text(text = "CVV")
                   },
                   colors = TextFieldDefaults.colors(
                       focusedContainerColor = Color.Transparent,
                       unfocusedContainerColor = Color.Transparent,

                       ),
                   trailingIcon = {
                       Icon(
                           painter = painterResource(id = R.drawable.help_24dp_5f6368_fill0_wght400_grad0_opsz24),
                           contentDescription = ""
                       )
                   },
                   leadingIcon = {
                       Icon(
                           painter = painterResource(id = R.drawable.help_24dp_5f6368_fill0_wght400_grad0_opsz24),
                           contentDescription = ""
                       )
                   }
               )
           }
       }
   }
}
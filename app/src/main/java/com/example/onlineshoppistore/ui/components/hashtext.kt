package com.example.onlineshoppistore.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MaskedCardNumber(cardNumber: String) {
    val maskedText = buildString {
        // Mask the first 12 digits with asterisks
        append("************")
        // Display the last 4 digits
        append(cardNumber.takeLast(4))
    }

    Text(
        text = maskedText,
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight(500),
            color = Color(0xFF1D2939),
        ),
    )
}

@Preview
@Composable
fun PreviewMaskedCardNumber() {
    MaskedCardNumber("1234567890123456")
}

package com.example.onlineshoppistore.ui.checkout

sealed class CheckoutScreenEvent {
    data class EnteredName(val value: String):  CheckoutScreenEvent()
    data class EnteredPhoneNo(val value: String):  CheckoutScreenEvent()
    data class EnteredAddress(val value: String):  CheckoutScreenEvent()
    data class EnteredCity(val value: String):  CheckoutScreenEvent()
    data class EnteredEmail(val value: String):  CheckoutScreenEvent()
    data class EnteredCardNo(val value: String):  CheckoutScreenEvent()
    data class EnteredMonth(val value: String):  CheckoutScreenEvent()
    data class EnteredYear(val value: String):  CheckoutScreenEvent()
    data class EnteredCvv(val value: String):  CheckoutScreenEvent()
}
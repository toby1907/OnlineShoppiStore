package com.example.onlineshoppistore.ui.checkout

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppistore.data.datastore.FavoriteDataStore
import com.example.onlineshoppistore.data.room.CartItem
import com.example.onlineshoppistore.data.room.CartRepositoryImpl
import com.example.onlineshoppistore.network.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val apiRepository: ApiRepository,
    private val cartRepository: CartRepositoryImpl,
    private val favoriteDataStore: FavoriteDataStore
) : ViewModel() {

    private val cartItemsFlow: MutableStateFlow<List<CartItem>> = MutableStateFlow(emptyList())
    val cartItems: StateFlow<List<CartItem>> = cartItemsFlow

    private val _name = mutableStateOf(
        PersonInfoScreenState()
    )
    private val _phoneNo = mutableStateOf(
        PersonInfoScreenState()
    )
    private val _address = mutableStateOf(
        PersonInfoScreenState()
    )
    private val _city = mutableStateOf(
        PersonInfoScreenState()
    )
    private val _email = mutableStateOf(
        PersonInfoScreenState()
    )
    private val _month = mutableStateOf(
        PersonInfoScreenState()
    )
    private val _year = mutableStateOf(
        PersonInfoScreenState()
    )
    private val _cvv = mutableStateOf(
        PersonInfoScreenState()
    )
    private val _cardNo = mutableStateOf(
        PersonInfoScreenState()
    )


    val name: State<PersonInfoScreenState> = _name
    val phoneNo: State<PersonInfoScreenState> = _phoneNo
    val address: State<PersonInfoScreenState> = _address
    val city: State<PersonInfoScreenState> = _city
    val email: State<PersonInfoScreenState> = _email
    val month: State<PersonInfoScreenState> = _month
    val year: State<PersonInfoScreenState> = _year
    val cvv: State<PersonInfoScreenState> = _cvv
    val cardNo: State<PersonInfoScreenState> = _cardNo


    init {
        viewModelScope.launch {
            cartItemsFlow.value = cartRepository.getAllCartItems().filter {
                it.isAddedToCart
            }
        }
    }

    fun onEvent(event: CheckoutScreenEvent) {
        when (event) {
            is CheckoutScreenEvent.EnteredAddress -> {
                _address.value = _address.value.copy(
                    address = event.value
                )
            }

            is CheckoutScreenEvent.EnteredCity -> {
                _city.value = _city.value.copy(
                    city = event.value
                )
            }

            is CheckoutScreenEvent.EnteredName -> {
                _name.value = _name.value.copy(
                    name = event.value
                )
            }

            is CheckoutScreenEvent.EnteredPhoneNo -> {
                _phoneNo.value = _phoneNo.value.copy(phoneNo = event.value)
            }

            is CheckoutScreenEvent.EnteredEmail -> {
                _email.value = _email.value.copy(
                    email = event.value
                )
            }

            is CheckoutScreenEvent.EnteredCardNo -> {
                _cardNo.value = _cardNo.value.copy(
                    cardNo = event.value
                )
            }

            is CheckoutScreenEvent.EnteredCvv -> {
                _cvv.value = _cvv.value.copy(
                    cvv = event.value
                )
            }

            is CheckoutScreenEvent.EnteredMonth -> {
                _month.value = _month.value.copy(
                    month = event.value
                )
            }

            is CheckoutScreenEvent.EnteredYear -> {

                _year.value = _year.value.copy(
                    year = event.value
                )
            }
        }
    }

    fun deleteItem(itemId: String) {
        viewModelScope.launch {
            cartRepository.deleteCartItemById(itemId)
            cartItemsFlow.value = cartRepository.getAllCartItems()
        }

    }

    fun updateItem(cartItem: CartItem) {
        viewModelScope.launch {
            cartRepository.updateCartItem(cartItem = cartItem)
            cartItemsFlow.value = cartRepository.getAllCartItems()
        }
    }

    fun markAllItemsAsOrderedCompleted(cartItems: List<CartItem>): List<CartItem> {
        return cartItemsFlow.value.map { it.copy(isOrderedCompleted = true) }
    }

    fun saveUpdatedCartItems() {
        viewModelScope.launch {
            val updatedValues = cartItemsFlow.value.map {
                it.copy(isOrderedCompleted = true, isAddedToCart = false)
            }

            cartRepository.updateCartItems(updatedValues)
        }
    }

    data class PersonInfoScreenState(
        val name: String = "",
        val phoneNo: String = "",
        val address: String = "",
        val city: String = "",
        val email: String = "",
        val cardNo: String = "",
        val month: String = "",
        val year: String = "",
        val cvv: String = ""
    )
}
package com.example.pizzaria

data class Pizza(
    val pizzaName: String,
    val ingredients: String,
    val quantity: Int,
    val price: Float
)

object Cart {
    val cartItems = mutableListOf<Pizza>()
}


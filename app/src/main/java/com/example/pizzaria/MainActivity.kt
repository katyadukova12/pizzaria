package com.example.pizzaria

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var fragmentContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentContainer = findViewById(R.id.fragmentContainer)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setBackgroundColor(
            ContextCompat.getColor(this, R.color.light_pink)
        )

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HomeFragment())
                .commit()
        }

        val cartItems = Cart.cartItems
        for (item in cartItems) {
            Log.d("CartItem", "${item.pizzaName}, ${item.ingredients}, ${item.quantity}, ${item.price}")
        }


        bottomNavigationView.selectedItemId = R.id.nav_home

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, HomeFragment())
                        .commit()
                    true
                }
                R.id.nav_fav -> {
                    true
                }
                R.id.nav_cart -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, CartFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}
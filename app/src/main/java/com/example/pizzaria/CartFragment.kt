package com.example.pizzaria

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartFragment : Fragment() {

    private lateinit var recyclerViewCart: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var txtTotalPrice: TextView
    private lateinit var btnContinue: Button

    var isCartEmpty = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        recyclerViewCart = view.findViewById(R.id.recyclerViewCart)
        txtTotalPrice = view.findViewById(R.id.txtTotalPrice)
        btnContinue = view.findViewById(R.id.btnContinue)

        recyclerViewCart.layoutManager = LinearLayoutManager(requireContext())

        val initialTotalPrice = Cart.cartItems.sumOf { it.price.toDouble() }.toFloat()
        txtTotalPrice.text = "Обща цена: ${String.format("%.2f лв.", initialTotalPrice)}"

        cartAdapter = CartAdapter(Cart.cartItems) { newTotalPrice ->
            txtTotalPrice.text = "Обща цена: ${String.format("%.2f лв.", newTotalPrice)}"
            if(newTotalPrice == 0f){
                isCartEmpty = true
            }
        }
        recyclerViewCart.adapter = cartAdapter

        btnContinue.setOnClickListener {
            if (isCartEmpty) {
                Toast.makeText(requireContext(), "Количката е празна", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                val intent = Intent(requireContext(), OrderActivity::class.java)
                startActivity(intent)
            }
        }

        return view
    }
}

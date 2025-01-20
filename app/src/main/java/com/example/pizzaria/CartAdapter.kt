package com.example.pizzaria

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private val cartItems: MutableList<Pizza>,
    private val onTotalPriceChange: (Float) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtPizzaName: TextView = view.findViewById(R.id.txtPizzaName)
        val txtIngredients: TextView = view.findViewById(R.id.txtIngredients)
        val txtQuantity: TextView = view.findViewById(R.id.txtQuantity)
        val txtPrice: TextView = view.findViewById(R.id.txtPrice)
        val btnRemoveFromCart: Button = view.findViewById(R.id.btnRemoveFromCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_cart_layout, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.txtPizzaName.text = item.pizzaName
        holder.txtIngredients.text = "${item.ingredients}"
        holder.txtQuantity.text = "Количество: ${item.quantity}"
        holder.txtPrice.text = "Цена: ${String.format("%.2f лв.", item.price)}"

        holder.btnRemoveFromCart.setOnClickListener {
            cartItems.removeAt(position)

            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cartItems.size)

            val totalPrice = calculateTotalPrice()
            onTotalPriceChange(totalPrice)
        }
    }

    override fun getItemCount(): Int = cartItems.size

    private fun calculateTotalPrice(): Float {
        return cartItems.sumOf { it.price.toDouble() }.toFloat()
    }

}


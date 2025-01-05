package com.example.pizzaria

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load

class MyAdapter(private val pizzaName: List<String>, private val pizzaPrices: List<String>, private val pizzaIngredients: List<String>, private val imgUrl: List<String>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtPizzaName: TextView = view.findViewById(R.id.txtPizzaName)
        val txtPizzaIngredients: TextView = view.findViewById(R.id.txtPizzaIngredients)
        val txtPizzaPrice: TextView = view.findViewById(R.id.txtPizzaPrice)
        val imgPizza: ImageView = view.findViewById(R.id.imgPizza)
        val conLay: ConstraintLayout = view.findViewById(R.id.conLay)
        val btnChoosePizza: Button = view.findViewById(R.id.btnChoosePizza)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_pizza_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtPizzaName.text = pizzaName[position]
        holder.txtPizzaIngredients.text = pizzaIngredients[position]
        holder.txtPizzaPrice.text = "${pizzaPrices[position]} лв."
        holder.imgPizza.load(imgUrl[position])

        holder.btnChoosePizza.setOnClickListener(){
            val intent = Intent(holder.itemView.context, PizzaDetailsActivity::class.java)
            intent.putExtra("pizzaName", pizzaName[position])
            intent.putExtra("pizzaIngredient", pizzaIngredients[position])
            intent.putExtra("pizzaPrice", pizzaPrices[position])
            intent.putExtra("pizzaUrl", imgUrl[position])
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = pizzaName.size
}
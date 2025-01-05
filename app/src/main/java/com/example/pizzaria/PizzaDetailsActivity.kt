package com.example.pizzaria

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load

class PizzaDetailsActivity : AppCompatActivity() {

    lateinit var txtPizzaName: TextView
    lateinit var txtPizzaIngredient: TextView
    lateinit var txtPizzaPrice: TextView
    lateinit var pizzaImage: ImageView
    lateinit var btnPar: ConstraintLayout
    lateinit var btnKra: ConstraintLayout
    lateinit var btnCa: ConstraintLayout
    lateinit var btnGu: ConstraintLayout
    lateinit var txtPar: TextView
    lateinit var txtKra: TextView
    lateinit var txtCa: TextView
    lateinit var txtGu: TextView

    var pizzaPrices: Float? = null

    var isParAdded: Boolean = false
    var isKraAdded: Boolean = false
    var isCaAdded: Boolean = false
    var isGuAdded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_details)

        txtPizzaName = findViewById(R.id.txtPizzaName)
        txtPizzaIngredient = findViewById(R.id.txtPizzaIngredients)
        txtPizzaPrice = findViewById(R.id.txtPizzaPrice)
        pizzaImage = findViewById(R.id.pizzaImage)
        btnPar = findViewById(R.id.btnPar)
        btnKra = findViewById(R.id.btnKra)
        btnCa = findViewById(R.id.btnCa)
        btnGu = findViewById(R.id.btnGu)
        txtPar = findViewById(R.id.txtPar)
        txtKra = findViewById(R.id.txtKra)
        txtCa = findViewById(R.id.txtCa)
        txtGu = findViewById(R.id.txtGu)

        val pizzaName = intent.getStringExtra("pizzaName")
        val pizzaIngredient = intent.getStringExtra("pizzaIngredient")
        pizzaPrices = intent.getStringExtra("pizzaPrice")?.toFloat()
        val pizzaUrl = intent.getStringExtra("pizzaUrl")

        btnPar.setOnClickListener{
            isParAdded = adding(isParAdded, 2.5f, txtPar)
        }

        btnKra.setOnClickListener{
            isKraAdded = adding(isKraAdded, 1f, txtKra)
        }

        btnCa.setOnClickListener{
            isCaAdded = adding(isCaAdded, 1.2f, txtCa)
        }

        btnGu.setOnClickListener{
            isGuAdded = adding(isGuAdded, 1.3f, txtGu)
        }

        pizzaImage.load(pizzaUrl)
        txtPizzaName.text = pizzaName
        txtPizzaIngredient.text = pizzaIngredient
        txtPizzaPrice.text = "$pizzaPrices лв."

    }

    private fun adding(isAdded: Boolean, addPrice: Float, txt: TextView): Boolean {
        var newAdded: Boolean = isAdded
        if(!isAdded){
            pizzaPrices = pizzaPrices?.plus(addPrice)
            txtPizzaPrice.text = "$pizzaPrices лв."
            newAdded = true
            txt.text = "✔"
        }
        else{
            pizzaPrices = pizzaPrices?.minus(addPrice)
            txtPizzaPrice.text = "$pizzaPrices лв."
            newAdded = false
            txt.text = "+"
        }
        return newAdded
    }

}
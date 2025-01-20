package com.example.pizzaria

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
    lateinit var txtQuantity: TextView
    lateinit var conLayPlus: ConstraintLayout
    lateinit var conLayMinus: ConstraintLayout
    lateinit var backAction: ImageView
    lateinit var btnAddToCart: Button

    var originalPizzaPrice: Float = 0f
    var pizzaPrice: Float = 0f
    var quantity = 1
    var pizzaIngredients: String = ""

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
        conLayPlus = findViewById(R.id.conLayPlus)
        conLayMinus = findViewById(R.id.conLayMinus)
        txtQuantity = findViewById(R.id.txtQuantity)
        backAction = findViewById(R.id.backAction)
        btnAddToCart = findViewById(R.id.btnAddToCart)

        val pizzaName = intent.getStringExtra("pizzaName")
        pizzaIngredients = intent.getStringExtra("pizzaIngredient").toString()
        pizzaPrice = intent.getStringExtra("pizzaPrice")?.toFloat()!!
        originalPizzaPrice = intent.getStringExtra("pizzaPrice")?.toFloat()!!
        val pizzaUrl = intent.getStringExtra("pizzaUrl")

        btnPar.setOnClickListener{
            isParAdded = adding(isParAdded, 2.5f, txtPar, "пармезан")
        }

        btnKra.setOnClickListener{
            isKraAdded = adding(isKraAdded, 1f, txtKra, "краставички")
        }

        btnCa.setOnClickListener{
            isCaAdded = adding(isCaAdded, 1.2f, txtCa, "царевица")
        }

        btnGu.setOnClickListener{
            isGuAdded = adding(isGuAdded, 1.3f, txtGu, "гъби")
        }

        backAction.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        conLayPlus.setOnClickListener {
            if (quantity == 20) {
                Toast.makeText(this, "Може да поръчате максимум 20 пици от един и същи вид!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            quantity += 1
            pizzaPrice += originalPizzaPrice
            if (isParAdded) pizzaPrice += 2.5f
            if (isKraAdded) pizzaPrice += 1f
            if (isCaAdded) pizzaPrice += 1.2f
            if (isGuAdded) pizzaPrice += 1.3f

            val formattedPrice = String.format("%.2f", pizzaPrice)
            txtPizzaPrice.text = "$formattedPrice лв."
            txtQuantity.text = quantity.toString()
        }

        conLayMinus.setOnClickListener {
            if (quantity == 1) return@setOnClickListener

            pizzaPrice -= originalPizzaPrice
            if (isParAdded) pizzaPrice -= 2.5f
            if (isKraAdded) pizzaPrice -= 1f
            if (isCaAdded) pizzaPrice -= 1.2f
            if (isGuAdded) pizzaPrice -= 1.3f

            quantity -= 1
            val formattedPrice = String.format("%.2f", pizzaPrice)
            txtPizzaPrice.text = "$formattedPrice лв."
            txtQuantity.text = quantity.toString()
        }

        btnAddToCart.setOnClickListener{
            val cartItem = Pizza(
                pizzaName = txtPizzaName.text.toString(),
                ingredients = pizzaIngredients,
                quantity = quantity,
                price = pizzaPrice
            )
            Cart.cartItems.add(cartItem)
            Toast.makeText(this, "Успешно добавихте в количката!", Toast.LENGTH_SHORT).show()
            clearDetails()
        }

        pizzaImage.load(pizzaUrl)
        txtPizzaName.text = pizzaName
        txtPizzaIngredient.text = pizzaIngredients
        txtPizzaPrice.text = "$pizzaPrice лв."

    }

    private fun clearDetails(){
        val intent = intent
        finish()
        startActivity(intent)
    }

    private fun adding(isAdded: Boolean, addPrice: Float, txt: TextView, ingredient: String): Boolean {
        var newAdded: Boolean = isAdded
        if (!isAdded) {
            pizzaPrice += addPrice * quantity
            val formattedPizzaPrice = String.format("%.2f", pizzaPrice)
            txtPizzaPrice.text = "$formattedPizzaPrice лв."
            newAdded = true
            txt.text = "✔"

            pizzaIngredients += ", $ingredient"
            txtPizzaIngredient.text = "$pizzaIngredients"
        } else {
            pizzaPrice -= addPrice * quantity
            val formattedPizzaPrice = String.format("%.2f", pizzaPrice)
            txtPizzaPrice.text = "$formattedPizzaPrice лв."
            newAdded = false
            txt.text = "+"

            pizzaIngredients = pizzaIngredients
                .replace(", $ingredient", "")
                .replace("$ingredient, ", "")
                .replace("$ingredient", "")
                .trim()

            txtPizzaIngredient.text = "$pizzaIngredients"
        }
        return newAdded
    }


}
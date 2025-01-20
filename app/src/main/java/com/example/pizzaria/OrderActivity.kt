package com.example.pizzaria

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class OrderActivity : AppCompatActivity() {

    lateinit var spinnerCities: Spinner
    lateinit var edtxName: EditText
    lateinit var edtxSurname: EditText
    lateinit var edtxPhoneNumber: EditText
    lateinit var edtxCity: EditText
    lateinit var edtxComment: EditText
    lateinit var btnOrder: Button

    var selectedDistrictCity: String = "София"
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        spinnerCities = findViewById(R.id.spinnerCities)
        edtxName = findViewById(R.id.edtxName)
        edtxSurname = findViewById(R.id.edtxSurname)
        edtxPhoneNumber = findViewById(R.id.edtxPhoneNumber)
        edtxCity = findViewById(R.id.edtxCity)
        edtxComment = findViewById(R.id.edtxComment)
        btnOrder = findViewById(R.id.btnOrder)

        ArrayAdapter.createFromResource(
            this,
            R.array.cities,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCities.adapter = adapter
        }

        val defaultCity = "София"
        val position = resources.getStringArray(R.array.cities).indexOf(defaultCity)
        if (position != -1) {
            spinnerCities.setSelection(position)
        }

        spinnerCities.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: android.view.View,
                position: Int,
                id: Long
            ) {
                val selectedCitySpinner = parent.getItemAtPosition(position).toString()
                selectedDistrictCity = selectedCitySpinner
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        btnOrder.setOnClickListener {
            when {
                edtxName.text.toString().trim().isEmpty() -> {
                    edtxName.error = "Моля, въведете име"
                    edtxName.requestFocus()
                    return@setOnClickListener
                }
                edtxSurname.text.toString().trim().isEmpty() -> {
                    edtxSurname.error = "Моля, въведете фамилия"
                    edtxSurname.requestFocus()
                    return@setOnClickListener
                }
                edtxPhoneNumber.text.toString().trim().isEmpty() -> {
                    edtxPhoneNumber.error = "Моля, въведете телефонен номер"
                    edtxPhoneNumber.requestFocus()
                    return@setOnClickListener
                }
                edtxCity.text.toString().trim().isEmpty() -> {
                    edtxCity.error = "Моля, въведете град"
                    edtxCity.requestFocus()
                    return@setOnClickListener
                }
                else -> {
                    val name = edtxName.text.toString().trim()
                    val surname = edtxSurname.text.toString().trim()
                    val phoneNumber = edtxPhoneNumber.text.toString().trim()
                    val city = edtxCity.text.toString().trim()
                    val comment: String
                    if(edtxComment.text.isEmpty()){
                        comment = "Без коментар"
                    }
                    else{
                        comment = edtxComment.text.toString().trim()
                    }


                    val cartDetails = StringBuilder()
                    for (pizza in Cart.cartItems) {
                        cartDetails.append(
                            "Пица: ${pizza.pizzaName}, ${pizza.ingredients}, " +
                                    "Количество: ${pizza.quantity}, Цена: ${pizza.price} лв.,\n"
                        )
                    }

                    val orderDetails = """
                        Поръчка:

                        $cartDetails
                        Детайли за доставка:
                        Име: $name $surname, 
                        Телефонен номер: $phoneNumber, 
                        Областен град: $selectedDistrictCity
                        Град: $city, 
                        Коментар: $comment
                    """.trimIndent()

                    val orderData = hashMapOf(
                        "Details" to orderDetails
                    )

                    db.collection("Orders")
                        .document("Order from $name $surname")
                        .set(orderData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Поръчката е записана успешно!", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Грешка при записването на поръчката.", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
    }
}

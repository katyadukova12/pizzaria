package com.example.pizzaria

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private lateinit var imgCart: ImageView
    private lateinit var imgFavourites: ImageView
    private lateinit var rvPizzas: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate your fragment layout:
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    // Here, we bind views and do the Firestore logic
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        // Find views by ID from the fragment layout
        rvPizzas = view.findViewById(R.id.rv_pizzas)
//        imgCart = view.findViewById(R.id.imgCart)
//        imgFavourites = view.findViewById(R.id.imgFavourites)

        val db = FirebaseFirestore.getInstance()

        db.collection("Pizzaria").document("pizzaNames").get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val pizzaNames = document.get("Names") as? List<String>
                    val pizzaPrices = document.get("Prices") as? List<String>
                    val pizzaIngredients = document.get("Ingridients") as? List<String>
                    val imgUrl = document.get("ImgUrl") as? List<String>

                    if (pizzaNames != null && pizzaPrices != null
                        && pizzaIngredients != null && imgUrl != null
                        && pizzaNames.size == pizzaPrices.size
                        && pizzaNames.size == pizzaIngredients.size) {

                        rvPizzas.layoutManager = GridLayoutManager(requireContext(), 2)
                        rvPizzas.adapter = MyAdapter(
                            pizzaNames,
                            pizzaPrices,
                            pizzaIngredients,
                            imgUrl
                        )
                    } else {
                        Log.d("Firestore", "One or more fields are null, or the sizes don't match")
                    }
                } else {
                    Log.d("Firestore", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Firestore", "get failed with ", exception)
            }
    }
}


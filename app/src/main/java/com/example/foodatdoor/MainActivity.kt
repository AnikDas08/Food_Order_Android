package com.example.foodatdoor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodatdoor.databinding.ActivityMainBinding
import com.example.foodatdoor.fragments.CartFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController:NavController=findNavController(R.id.fragmentContainerView)
        val bottomNav:BottomNavigationView=findViewById<BottomNavigationView>(R.id.bottomnavview)
        bottomNav.setupWithNavController(navController)

        //when detail activity add to cart then go to card fragment
        if (intent.getBooleanExtra("openCart", false)) {
            navController.navigate(R.id.cartFragment)
        }


    }
}
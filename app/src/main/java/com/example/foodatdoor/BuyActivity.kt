package com.example.foodatdoor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodatdoor.databinding.ActivityBuyBinding

class BuyActivity : AppCompatActivity() {
    lateinit var binding:ActivityBuyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityBuyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val totalprice=intent.getStringExtra("totalPrice")?:"0.0"
        binding.totalamount.setText(totalprice)
    }
}
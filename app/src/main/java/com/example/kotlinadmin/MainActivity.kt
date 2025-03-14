package com.example.kotlinadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.kotlinadmin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.kldfjdlk.setOnClickListener(View.OnClickListener {
            val intent=Intent(this,AddItem::class.java)
            startActivity(intent)
            finish()
        })
        binding.cardView3.setOnClickListener(View.OnClickListener {
            val intent=Intent(this,ItemViewActivityHere::class.java)
            startActivity(intent)
        })
        binding.profileDetails.setOnClickListener(View.OnClickListener {
            val intent=Intent(this,ProfileActivity::class.java)
            startActivity(intent)
        })
    }
}
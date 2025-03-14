package com.example.foodatdoor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.foodatdoor.databinding.ActivityDetailsBinding
import com.example.foodatdoor.fragments.CartFragment
import com.example.foodatdoor.model.Cartitems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {
    lateinit var binding:ActivityDetailsBinding
    lateinit var database:DatabaseReference
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val name=intent.getStringExtra("foodName")
        val price=intent.getStringExtra("price")
        val image=intent.getStringExtra("foodImage")
        val description=intent.getStringExtra("foodDescription")
        val ingredent=intent.getStringExtra("ingredent")

        binding.FoodName.text=name
        Glide.with(this).load(image).into(binding.imageView10);
        binding.ShortDescriptoin.text=description
        binding.NewItem.text=ingredent
        binding.PriceAll.text="Price is :"+price

        binding.AddCartButtonAll.setOnClickListener(View.OnClickListener {
            auth=FirebaseAuth.getInstance()
            database=FirebaseDatabase.getInstance().reference
            val userId:String=auth.currentUser!!.uid

            val cartitems=Cartitems(name.toString(),price.toString(),description.toString(),image.toString(),1)
            database.child("user").child(userId).child("cartitems").push().setValue(cartitems)
                .addOnSuccessListener {
                    Toast.makeText(this, "item are store", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("openCart", true)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener{
                    Toast.makeText(this, "Data Not save", Toast.LENGTH_SHORT).show()
                }
        })

    }
}
package com.example.foodatdoor.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodatdoor.BuyActivity
import com.example.foodatdoor.R
import com.example.foodatdoor.adapter.CartAdapter
import com.example.foodatdoor.databinding.FragmentCartBinding
import com.example.foodatdoor.model.Cartitems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding
    lateinit var cardAdapter:CartAdapter
    lateinit var auth:FirebaseAuth
    lateinit var database:DatabaseReference
    private var totalPrice = 0.0
    val list= mutableListOf<Cartitems>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCartBinding.inflate(inflater, container, false)
        binding.recyclercart.layoutManager= LinearLayoutManager(requireContext())
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference


        cardAdapter = CartAdapter(list,requireContext())
        binding.recyclercart.adapter = cardAdapter

        val userId = auth.currentUser?.uid ?:""
        val cartRef = database.child("user").child(userId).child("cartitems")


        cartRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                totalPrice = 0.0
                for (itemSnapshot in snapshot.children) {
                    val cartItem = itemSnapshot.getValue(Cartitems::class.java)
                    cartItem?.let { list.add(it) }
                    totalPrice += cartItem!!.foodPrice?.toDouble() ?: 0.0
                }
                cardAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to load cart items", Toast.LENGTH_SHORT).show()
            }
        })

        binding.Button.setOnClickListener {
            if (totalPrice > 0) {
                val intent = Intent(requireContext(), BuyActivity::class.java)
                intent.putExtra("totalPrice", totalPrice.toString()) // Pass total price
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Your cart is empty!", Toast.LENGTH_SHORT).show()
            }
        }




        return binding.root
    }


}
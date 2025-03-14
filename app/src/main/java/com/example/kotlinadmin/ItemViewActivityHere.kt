package com.example.kotlinadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinadmin.adapter.AddItemListAdapter
import com.example.kotlinadmin.databinding.ActivityAddItemBinding
import com.example.kotlinadmin.databinding.ActivityItemViewHereBinding
import com.example.kotlinadmin.databinding.ItemaddShowBinding
import com.example.kotlinadmin.model.AddModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ItemViewActivityHere : AppCompatActivity() {
    lateinit var binding: ActivityItemViewHereBinding
    lateinit var auth:FirebaseAuth
    lateinit var adapter:AddItemListAdapter
    lateinit var list:ArrayList<AddModel>
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityItemViewHereBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("menu")
        list= ArrayList()

        binding.Recle.layoutManager = LinearLayoutManager(this)
        adapter = AddItemListAdapter(this, list)
        binding.Recle.adapter = adapter

        fetchDataFromFirebase()

    }
    public fun fetchDataFromFirebase()
    {
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for(foodsncpshot in snapshot.children){
                    val foodItem=foodsncpshot.getValue(AddModel::class.java)
                    foodItem?.let {
                        list.add(it)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ItemViewActivityHere, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
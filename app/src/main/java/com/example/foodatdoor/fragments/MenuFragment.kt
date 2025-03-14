package com.example.foodatdoor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodatdoor.R
import com.example.foodatdoor.adapter.MenuAdapter
import com.example.foodatdoor.databinding.FragmentMenuBinding
import com.example.foodatdoor.model.MenuItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MenuFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentMenuBinding
    lateinit var list:MutableList<MenuItem>
    lateinit var database:DatabaseReference
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMenuBinding.inflate(inflater, container, false)

        auth=FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance().reference

        binding.MenuRecyclerview.layoutManager= LinearLayoutManager(requireContext())
        list = mutableListOf()
        val adapter=MenuAdapter(list,requireContext())
        binding.MenuRecyclerview.adapter = adapter

        database.child("menu").addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
               for(dataSnapshort in snapshot.children){
                   val menuItem =dataSnapshort.getValue(MenuItem::class.java)
                   menuItem?.let { list.add(it) }
               }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }

        })

        binding.CartBackItem.setOnClickListener(View.OnClickListener {
            dismiss()
        })



        return binding.root
    }

}
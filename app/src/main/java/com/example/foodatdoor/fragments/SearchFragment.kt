package com.example.foodatdoor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodatdoor.R
import com.example.foodatdoor.adapter.MenuAdapter
import com.example.foodatdoor.databinding.FragmentSearchBinding
import com.example.foodatdoor.model.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    lateinit var list:MutableList<MenuItem>
    lateinit var auth:FirebaseAuth
    lateinit var originalList:MutableList<MenuItem>
    lateinit var adapter:MenuAdapter
    lateinit var database: DatabaseReference
    private lateinit var filteredList: MutableList<MenuItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSearchBinding.inflate(inflater, container, false)

        auth=FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance().reference

        binding.SearchRec.layoutManager= LinearLayoutManager(requireContext())
        list = mutableListOf()
        originalList = mutableListOf()
        filteredList = mutableListOf()
        adapter=MenuAdapter(list,requireContext())
        binding.SearchRec.adapter = adapter

        loadMenuItems()
        setupSearchView()


        return binding.root
    }
    private fun loadMenuItems() {
        database.child("menu").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                originalList.clear()
                for (data in snapshot.children) {
                    val item = data.getValue(MenuItem::class.java)
                    item?.let { list.add(it)
                        originalList.add(it)}
                }
                filteredList.clear()
                filteredList.addAll(list)
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun setupSearchView() {
        binding.SearchVies.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText)
                return true
            }
        })
    }

    private fun filter(query: String?) {
        val filteredList = if (query.isNullOrEmpty()) {
            originalList
        } else {
            originalList.filter { it.foodName?.lowercase()?.contains(query.lowercase().trim()) == true }
        }
        adapter.updateList(filteredList)
    }
}
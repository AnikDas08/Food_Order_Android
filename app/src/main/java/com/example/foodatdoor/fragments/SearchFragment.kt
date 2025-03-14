package com.example.foodatdoor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodatdoor.R
import com.example.foodatdoor.adapter.MenuAdapter
import com.example.foodatdoor.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    lateinit var adapter: MenuAdapter
    val name= mutableListOf<String>("lkdjfk","lkdjfkldf","ldkjfkd","ldkjfkd","dfkd")
    val image= mutableListOf<Int>(
        R.drawable.foodicon,
        R.drawable.foodlogo,
        R.drawable.foodicon,
        R.drawable.foodlogo,
        R.drawable.foodicon
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSearchBinding.inflate(inflater, container, false)

        binding.SearchRec.layoutManager= LinearLayoutManager(requireContext())

        /*adapter=MenuAdapter(name,image,requireContext())
        binding.SearchRec.adapter=adapter*/

        hereSearch()
        showAllItems()


        return binding.root
    }

    public fun hereSearch() {
        binding.SearchVies.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (!query.isNullOrEmpty()) {
                    filter(query)
                } else {
                    showAllItems()
                }
                return true
            }

            override fun onQueryTextChange(textsubject: String): Boolean {
                if (textsubject.isNullOrEmpty()) {
                    showAllItems()  // Reset to original data if query is empty
                } else {
                    filter(textsubject)  // Filter data based on query
                }
                return true
            }
        })
    }
    public fun showAllItems(){
        name.clear()
        image.clear()

        name.addAll(mutableListOf("lkdjfk", "lkdjfkldf", "ldkjfkd", "ldkjfkd", "dfkd"))
        image.addAll(mutableListOf(
            R.drawable.foodicon,
            R.drawable.foodlogo,
            R.drawable.foodicon,
            R.drawable.foodlogo,
            R.drawable.foodicon
        ))
    }
    public fun filter(query:String) {
        val filteredNames = mutableListOf<String>()
        val filteredImages = mutableListOf<Int>()

        // Filter the items based on the query
        name.forEachIndexed { index, s ->
            if (s.contains(query, ignoreCase = true)) {
                filteredNames.add(s)
                filteredImages.add(image[index])  // Add the corresponding image
            }
        }

        // Clear the original lists and update with filtered data
        name.clear()
        image.clear()
        name.addAll(filteredNames)
        image.addAll(filteredImages)

        //adapter.notifyDataSetChanged()
    }
}
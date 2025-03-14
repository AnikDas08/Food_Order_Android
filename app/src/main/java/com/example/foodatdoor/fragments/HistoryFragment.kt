package com.example.foodatdoor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodatdoor.R
import com.example.foodatdoor.adapter.Buyadapter
import com.example.foodatdoor.databinding.FragmentHistoryBinding
import com.example.foodatdoor.databinding.ItemBuyBinding

class HistoryFragment : Fragment() {
    lateinit var binding:FragmentHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHistoryBinding.inflate(inflater, container, false)

        val name= listOf<String>("lkdjfk","lkdjfkldf","ldkjfkd","ldkjfkd","dfkd")
        val image= listOf<Int>(R.drawable.foodicon, R.drawable.foodlogo, R.drawable.foodicon, R.drawable.foodlogo,R.drawable.foodicon
        )

        val adapter=Buyadapter(name,image)
        binding.Recylerview.layoutManager=LinearLayoutManager(requireContext())

        binding.Recylerview.adapter=adapter

        return binding.root

    }

}
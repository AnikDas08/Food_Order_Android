package com.example.foodatdoor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodatdoor.R
import com.example.foodatdoor.adapter.Buyadapter
import com.example.foodatdoor.databinding.FragmentNotificationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotificationFragment : BottomSheetDialogFragment() {
    lateinit var binding:FragmentNotificationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val name= listOf<String>("lkdjfk","lkdjfkldf","ldkjfkd","ldkjfkd","dfkd")
        val image= listOf<Int>(R.drawable.foodicon, R.drawable.foodlogo, R.drawable.foodicon, R.drawable.foodlogo,R.drawable.foodicon
        )

        /*val adapter= Buyadapter(name,image)
        binding.Recylerview.layoutManager= LinearLayoutManager(requireContext())

        binding.Recylerview.adapter=adapter*/
    }
}
package com.example.foodatdoor.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foodatdoor.R
import com.example.foodatdoor.adapter.MenuAdapter
import com.example.foodatdoor.adapter.PopulerAdapter
import com.example.foodatdoor.databinding.FragmentHomeBinding
import com.example.foodatdoor.databinding.FragmentMenuBinding
import com.example.foodatdoor.model.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var list:MutableList<MenuItem>
    lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentHomeBinding.inflate(inflater, container, false)

        binding.menuitem.setOnClickListener(View.OnClickListener {
            val bottomsheet= MenuFragment()
            bottomsheet.show(parentFragmentManager,"Test")
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList=ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.foodicon))
        imageList.add(SlideModel(R.drawable.foodlogo));

        binding.imageSlider.setImageList(imageList, scaleType = ScaleTypes.FIT)
        binding.imageSlider.startSliding(2000)

        auth=FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance().reference

        binding.recylerViewItemPopuler.layoutManager= LinearLayoutManager(requireContext())
        list = mutableListOf()

        database.child("menu").addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for(dataSnapshort in snapshot.children){
                    val menuItem =dataSnapshort.getValue(MenuItem::class.java)
                    menuItem?.let { list.add(it) }
                }

                //adapter.notifyDataSetChanged()
                val index:List<Int> = list.indices.toList().shuffled()
                val numberofitemlist=6
                val subMenuItem:List<MenuItem> = index.take(numberofitemlist).map { list[it] }

                val adapter= PopulerAdapter(subMenuItem,requireContext())
                binding.recylerViewItemPopuler.adapter = adapter
                Log.d("make","kdjkf")

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }

        })




    }


}
package com.example.foodatdoor.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.foodatdoor.DetailsActivity
import com.example.foodatdoor.databinding.PopulerItemBinding
import com.example.foodatdoor.model.MenuItem

class PopulerAdapter(private val list:List<MenuItem>,private val contex:Context): RecyclerView.Adapter<PopulerAdapter.viewholder>() {
    class viewholder(val binding:PopulerItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): viewholder {
        val binding = PopulerItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return viewholder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(viewhoder: viewholder, i: Int) {
        val lists=list[i]
        viewhoder.binding.textView10.text=lists.foodName
        viewhoder.binding.textView13.text=lists.foodPrice
        Glide.with(contex)
            .load(lists.foodImage)
            .into(viewhoder.binding.imageView4)
        viewhoder.itemView.setOnClickListener(View.OnClickListener {
            val intent=Intent(contex,DetailsActivity::class.java)
            intent.putExtra("foodName",lists.foodName)
            intent.putExtra("price",lists.foodPrice)
            intent.putExtra("foodImage",lists.foodImage)
            intent.putExtra("foodDescription",lists.foodDescription)
            intent.putExtra("ingredent",lists.foodMenu)
            contex.startActivity(intent)
        })

    }
}
package com.example.foodatdoor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodatdoor.databinding.ItemBuyBinding

class Buyadapter(val list:List<String>,val image:List<Int>): RecyclerView.Adapter<Buyadapter.viewholder>() {
    class viewholder(val binding:ItemBuyBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(viewHolder: ViewGroup, i: Int): viewholder {
       val binding=ItemBuyBinding.inflate(LayoutInflater.from(viewHolder.context),viewHolder,false)
        return viewholder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(viewHolder: viewholder, i: Int) {
        viewHolder.binding.textView20.text=list[i]
        viewHolder.binding.imageView11.setImageResource(image[i])
    }
}
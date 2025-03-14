package com.example.foodatdoor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodatdoor.databinding.NotificationItemiewBinding

class NotificationAdapter(val list:ArrayList<String>,val image:List<Int>) : RecyclerView.Adapter<NotificationAdapter.viewholder>() {

    class viewholder(var binding:NotificationItemiewBinding): RecyclerView.ViewHolder(
        binding.root) {

    }

    override fun onCreateViewHolder(viewHolder: ViewGroup, i: Int): viewholder {
       val binding=NotificationItemiewBinding.inflate(LayoutInflater.from(viewHolder.context),viewHolder,false)
        return viewholder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(viewholer: viewholder, i: Int) {
        viewholer.binding.textView12.text=list[i]
        viewholer.binding.imageView8.setImageResource(image[i])
    }
}
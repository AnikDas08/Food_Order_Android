package com.example.foodatdoor.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodatdoor.DetailsActivity
import com.example.foodatdoor.databinding.MenuItemBinding
import com.example.foodatdoor.model.MenuItem

class MenuAdapter(private var list:MutableList<MenuItem>,private val context:Context): RecyclerView.Adapter<MenuAdapter.viewHolder>() {
    class viewHolder(val binding:MenuItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(viewholder: ViewGroup, i: Int): viewHolder {
        val binding=MenuItemBinding.inflate(LayoutInflater.from(viewholder.context),viewholder,false)
        return viewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(viewHolder: viewHolder, position: Int) {
        val lists:MenuItem=list[position]
        viewHolder.binding.menuName.text=lists.foodName
        viewHolder.binding.textView13.text=lists.foodPrice
        Glide.with(context)
            .load(lists.foodImage)
            .into(viewHolder.binding.menuImages)
        viewHolder.itemView.setOnClickListener(View.OnClickListener {
            val intent=Intent(context,DetailsActivity::class.java)
            intent.putExtra("foodName",lists.foodName)
            intent.putExtra("price",lists.foodPrice)
            intent.putExtra("foodImage",lists.foodImage)
            intent.putExtra("foodDescription",lists.foodDescription)
            intent.putExtra("ingredent",lists.foodMenu)
            context.startActivity(intent)
        })
    }
    // Function to update the list dynamically
    fun updateList(newList: List<MenuItem>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged() // Refresh RecyclerView
    }
}
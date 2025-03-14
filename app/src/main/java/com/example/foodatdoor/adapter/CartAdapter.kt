package com.example.foodatdoor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodatdoor.databinding.ItemCartBinding
import com.example.foodatdoor.databinding.PopulerItemBinding
import com.example.foodatdoor.model.Cartitems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CartAdapter(private val list: MutableList<Cartitems>,private val context:Context): RecyclerView.Adapter<CartAdapter.viewholder>() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    class viewholder(val binding: ItemCartBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(viewgroup: ViewGroup, i: Int): viewholder {
        val binding=ItemCartBinding.inflate(LayoutInflater.from(viewgroup.context),viewgroup,false)
        return viewholder(binding)
    }

    override fun getItemCount(): Int =list.size

    override fun onBindViewHolder(viewHolder: viewholder, i: Int) {
        var cartItem = list[i]
        viewHolder.binding.foodName.text = cartItem.foodName
        viewHolder.binding.foodPrice.text = "$: ${cartItem.foodPrice}"
        viewHolder.binding.quantityAll.text = cartItem.foodQuantity.toString()
        Glide.with(context).load(cartItem.foodImage).into(viewHolder.binding.cartimage)

        // Increment button
        viewHolder.binding.incrementAll.setOnClickListener {
            cartItem.foodQuantity = (cartItem.foodQuantity ?: 0) + 1
            notifyItemChanged(i)  // ✅ Update UI
        }

        // Decrement button
        viewHolder.binding.decrementAll.setOnClickListener {
            if ((cartItem.foodQuantity ?: 0) > 1) {
                cartItem.foodQuantity = (cartItem.foodQuantity ?: 0) - 1
                notifyItemChanged(i)  // ✅ Update UI
            }
        }

        //DELETE BUTTON - Remove item when clicked
        viewHolder.binding.deleteAll.setOnClickListener {
            deleteItem(cartItem, i)
        }

    }


    private fun deleteItem(cartItem: Cartitems, position: Int) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            Toast.makeText(context, "User not logged in!", Toast.LENGTH_SHORT).show()
            return
        }

        val cartRef = database.child("user").child(userId).child("cartitems")

        cartRef.orderByChild("foodName").equalTo(cartItem.foodName)
            .get().addOnSuccessListener { snapshot ->
                for (cartSnapshot in snapshot.children) {
                    cartSnapshot.ref.removeValue().addOnSuccessListener {
                        if (position >= 0 && position < list.size) {
                            list.removeAt(position)  // Remove item safely
                            notifyItemRemoved(position)  // Refresh UI
                            notifyItemRangeChanged(position, list.size)  // Fix index shifting
                        }
                        Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show()
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(context, "Failed to remove item", Toast.LENGTH_SHORT).show()
            }
    }
}
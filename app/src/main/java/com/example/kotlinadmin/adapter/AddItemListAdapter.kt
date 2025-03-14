package com.example.kotlinadmin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinadmin.R
import com.example.kotlinadmin.databinding.ItemaddShowBinding
import com.example.kotlinadmin.model.AddModel
import com.google.firebase.database.FirebaseDatabase
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import com.squareup.picasso.Picasso

class AddItemListAdapter(val contex:Context,private var list:ArrayList<AddModel>): RecyclerView.Adapter<AddItemListAdapter.viewholder>() {
    class viewholder(val binding:ItemaddShowBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(viewholder: ViewGroup, i: Int): viewholder {
        val binding=ItemaddShowBinding.inflate(LayoutInflater.from(viewholder.context),viewholder,false)
        return viewholder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(viewholder: viewholder, i: Int) {
        val lists=list.get(i)
        viewholder.binding.textView15.text=lists.foodName
        viewholder.binding.textView16.text=lists.foodPrice
        Picasso.get()
            .load(lists.foodImage)  // Image URL or URI
            .into(viewholder.binding.imageView6)
        var quantity = 1

        viewholder.binding.imageView5.setOnClickListener {
            if (quantity > 1) {
                quantity--
                viewholder.binding.textView17.text = quantity.toString()
            }
        }
        viewholder.binding.imageView6.setOnClickListener {
            quantity++
            viewholder.binding.textView17.text = quantity.toString()
        }

        viewholder.itemView.setOnClickListener {
            val dialogPlus = DialogPlus.newDialog(contex)
                .setContentHolder(ViewHolder(R.layout.bottomsheet))
                .setExpanded(true, 1200)
                .create()

            val dialogView = dialogPlus.holderView
            val updateName = dialogView.findViewById<EditText>(R.id.UpdateName)
            val updatePrice = dialogView.findViewById<EditText>(R.id.UpdatePrice)
            val updateDescription = dialogView.findViewById<EditText>(R.id.UpdateDescription)
            val updateItem = dialogView.findViewById<EditText>(R.id.UpdateItem)
            val updateImage = dialogView.findViewById<EditText>(R.id.UpdateImage)
            val updateButton = dialogView.findViewById<Button>(R.id.UpdateButtonId)

            updateName.setText(lists.foodName)
            updatePrice.setText(lists.foodPrice.toString())
            updateDescription.setText(lists.foodDescription.toString())
            updateImage.setText(lists.foodImage.toString())
            updateItem.setText(lists.foodMenu.toString())

            dialogPlus.show()
            updateButton.setOnClickListener(View.OnClickListener {
                val updatedFoodName = updateName.text.toString()
                val updatedFoodPrice = updatePrice.text.toString()
                val updatedFoodDescription = updateDescription.text.toString()
                val updatedFoodItem = updateItem.text.toString()
                val updatedImage = updateImage.text.toString()
                if (updatedFoodName.isNotBlank() && updatedFoodPrice.isNotBlank() && updatedFoodDescription.isNotBlank() && updatedFoodItem.isNotBlank() && updatedImage.isNotBlank()) {

                    // Update data in Firebase or your data source here
                    val foodItem=AddModel()
                    val databaseReference = FirebaseDatabase.getInstance().getReference("menu")

                    // Use the item's unique ID (assumed to be foodItem.foodId) to find the node to update
                    val foodId = databaseReference.push().key

                    if (foodId != null) {
                        // Create a map of updated values
                        val updatedValues = mapOf(
                            "foodName" to updatedFoodName,
                            "foodPrice" to updatedFoodPrice,
                            "foodDescription" to updatedFoodDescription,
                            "foodMenu" to updatedFoodItem,
                            "foodImage" to updatedImage
                        )

                        // Update the food item in Firebase using the unique item ID
                        databaseReference.child(foodId).updateChildren(updatedValues)
                            .addOnSuccessListener {
                                // Success: Item updated
                                Toast.makeText(contex, "Food item updated successfully!", Toast.LENGTH_SHORT).show()
                                // Optionally, refresh the data or notify the adapter
                            }
                            .addOnFailureListener { exception ->
                                // Failure: Show error message
                                Toast.makeText(contex, "Failed to update food item: ${exception.message}", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(contex, "Food item ID is null. Unable to update.", Toast.LENGTH_SHORT).show()
                    }
                    dialogPlus.dismiss()
                } else {
                    Toast.makeText(contex, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

}
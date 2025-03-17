package com.example.foodatdoor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.foodatdoor.R
import com.example.foodatdoor.databinding.FragmentProfilekBinding
import com.example.foodatdoor.model.UserInformation
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ProfilekFragment : Fragment() {
    lateinit var binding:FragmentProfilekBinding
    lateinit var name:String
    lateinit var address:String
    lateinit var email:String
    lateinit var phone:String
    var isEditable = false
    lateinit var auth:FirebaseAuth
    lateinit var database:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentProfilekBinding.inflate(inflater, container, false)

        auth=FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance().reference

        disableEditing()

        loadUserData()

        binding.textView24.setOnClickListener {
            isEditable = !isEditable
            toggleEditMode(isEditable)
        }

        binding.SaveInformation.setOnClickListener {
            name = binding.NameEdit.text.toString()
            email = binding.EmailEdit.text.toString()
            address = binding.AddressEdit.text.toString()
            phone = binding.PhoneEdit.text.toString()

            if (name.isEmpty() || address.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            val user = UserInformation(name, address, email, phone)

            // Save the data to Firebase under the user's node
            database.child("user").child(userId).child("userInformation").setValue(user)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Information Saved", Toast.LENGTH_SHORT).show()
                    disableEditing() // Disable fields after saving
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Information Failed", Toast.LENGTH_SHORT).show()
                }
        }

        return binding.root
    }

    private fun loadUserData() {
        val userId = auth.currentUser?.uid ?: return
        database.child("user").child(userId).child("userInformation")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.getValue(UserInformation::class.java)
                        user?.let {
                            // Populate EditTexts with data from Firebase
                            binding.NameEdit.setText(it.name ?: "")
                            binding.AddressEdit.setText(it.address ?: "")
                            binding.EmailEdit.setText(it.email ?: "")
                            binding.PhoneEdit.setText(it.phone ?: "")
                        }
                    } else {
                        Toast.makeText(requireContext(), "No user data found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            })
    }
    private fun toggleEditMode(isEditable: Boolean) {
        binding.NameEdit.isEnabled = isEditable
        binding.AddressEdit.isEnabled = isEditable
        binding.EmailEdit.isEnabled = isEditable
        binding.PhoneEdit.isEnabled = isEditable
        binding.SaveInformation.isEnabled = isEditable

        if (isEditable) {
            // Request focus on the first editable field
            binding.NameEdit.requestFocus()
        }
    }

    // Disable editing (lock the fields and the save button)
    private fun disableEditing() {
        binding.NameEdit.isEnabled = false
        binding.AddressEdit.isEnabled = false
        binding.EmailEdit.isEnabled = false
        binding.PhoneEdit.isEnabled = false
        binding.SaveInformation.isEnabled = false
    }

}
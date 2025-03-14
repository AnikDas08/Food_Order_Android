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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ProfilekFragment : Fragment() {
    lateinit var binding:FragmentProfilekBinding
    lateinit var name:String
    lateinit var address:String
    lateinit var email:String
    lateinit var phone:String
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

        binding.SaveInformation.isEnabled=false
        binding.NameEdit.isEnabled=false
        binding.AddressEdit.isEnabled=false
        binding.EmailEdit.isEnabled=false
        binding.PhoneEdit.isEnabled=false
        var isEnable=false

        binding.textView24.setOnClickListener(View.OnClickListener {
            isEnable=!isEnable
            binding.NameEdit.isEnabled=isEnable
            binding.AddressEdit.isEnabled=isEnable
            binding.EmailEdit.isEnabled=isEnable
            binding.PhoneEdit.isEnabled=isEnable
            binding.SaveInformation.isEnabled=isEnable
            if(isEnable){
                binding.SaveInformation.setOnClickListener(View.OnClickListener {

                    name=binding.NameEdit.text.toString()
                    email=binding.EmailEdit.text.toString()
                    address=binding.AddressEdit.text.toString()
                    phone=binding.PhoneEdit.text.toString()

                    val newId=FirebaseAuth.getInstance().currentUser!!.uid
                    val user=UserInformation(name,address,email,phone)
                    database.child("user").child(newId).child("userInformation").setValue(user).addOnSuccessListener {
                        Toast.makeText(requireContext(),"Information Save",Toast.LENGTH_SHORT).show()
                    }
                        .addOnFailureListener(OnFailureListener {
                            Toast.makeText(requireContext(),"Information Failed",Toast.LENGTH_SHORT).show()
                        })
                })
                binding.NameEdit.requestFocus()
            }
        })




        binding.SaveInformation.setOnClickListener(View.OnClickListener {

            name=binding.NameEdit.text.toString()
            email=binding.EmailEdit.text.toString()
            address=binding.AddressEdit.text.toString()
            phone=binding.PhoneEdit.text.toString()

            val newId=FirebaseAuth.getInstance().currentUser!!.uid
            val user=UserInformation(name,address,email,phone)
            database.child("user").child(newId).child("userInformation").setValue(user).addOnSuccessListener {
                Toast.makeText(requireContext(),"Information Save",Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener(OnFailureListener {
                    Toast.makeText(requireContext(),"Information Failed",Toast.LENGTH_SHORT).show()
                })
        })


        return binding.root
    }


}
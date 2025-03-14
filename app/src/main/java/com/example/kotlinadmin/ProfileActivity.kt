package com.example.kotlinadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.kotlinadmin.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    lateinit var binding:ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.NameEdit.isEnabled=false
        binding.AddressEditIdText.isEnabled=false
        binding.EmailEdit.isEnabled= false
        binding.phoneedit.isEnabled=false
        binding.passwordedit.isEnabled=false

        var isEnable=false
        binding.EditButton.setOnClickListener(View.OnClickListener {
            isEnable=!isEnable
            binding.NameEdit.isEnabled=isEnable
            binding.AddressEditIdText.isEnabled=isEnable
            binding.EmailEdit.isEnabled= isEnable
            binding.phoneedit.isEnabled=isEnable
            binding.passwordedit.isEnabled=isEnable
            if(isEnable){
                binding.NameEdit.requestFocus()
            }
        })
    }
}
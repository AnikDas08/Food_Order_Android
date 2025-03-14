package com.example.kotlinadmin

import android.app.Activity
import android.content.ClipDescription
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.kotlinadmin.databinding.ActivityAddItemBinding
import com.example.kotlinadmin.model.AddModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Locale

class AddItem : AppCompatActivity() {
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    lateinit var binding:ActivityAddItemBinding
    lateinit var foodName:String
    lateinit var foodPrice:String
    lateinit var foodDescription:String
    lateinit var foodBuild:String
    lateinit var auth:FirebaseAuth
    lateinit var database:FirebaseDatabase
    private var imageUri: Uri? = null
    private lateinit var storage: FirebaseStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)


        database=FirebaseDatabase.getInstance()
        auth=FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()

        binding.backButton.setOnClickListener(View.OnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        })
        binding.ButtonId.setOnClickListener(View.OnClickListener {
            foodName=binding.foodName.text.toString()
            foodPrice=binding.foodPrice.text.toString()
            foodDescription=binding.sortDesc.text.toString()
            foodBuild=binding.ndfkd.text.toString()

            if(foodName.isBlank()||foodPrice.isBlank()||foodDescription.isBlank()||foodBuild.isBlank()){
                Toast.makeText(this,"give the value all",Toast.LENGTH_SHORT).show()
            }
            else{
                uploadData()
            }
        })
        binding.imageSelector.setOnClickListener(View.OnClickListener {
            pickImageFromGallery()
        })
        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                imageUri = result.data?.data
                imageUri?.let {
                    binding.imageSelector.setImageURI(it)
                }
            }
        }
    }
    public fun  uploadData(){
        if (imageUri == null) {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
            return
        }
        val RefNewData:DatabaseReference=database.getReference("menu")
        val fileName = "images/${RefNewData.push().key}.jpg"

        // Reference to Firebase Storage
        val storageRef = storage.reference.child(fileName)

        // Upload the image to Firebase Storage
        storageRef.putFile(imageUri!!)
            .addOnSuccessListener {
                // Get the image URL after successful upload
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    saveDataToDatabase(foodName, foodPrice, foodDescription, foodBuild, imageUrl)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
            }
    }
    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"  // Allow all image types (JPEG, PNG, etc.)
        imagePickerLauncher.launch(intent)
    }
    private fun saveDataToDatabase(foodName:String,foodPrice:String,foodDescription:String,foodBuild:String,imageUrl:String){
        val reference: DatabaseReference = database.getReference("menu")
        val itemId = reference.push().key  // Generate a unique ID

        val foodItem = AddModel(foodName, foodPrice, foodDescription, imageUrl,foodBuild)

        // Save the data in Firebase Realtime Database
        reference.child(itemId!!).setValue(foodItem)
            .addOnSuccessListener {
                Toast.makeText(this, "Data uploaded successfully", Toast.LENGTH_SHORT).show()
                val intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to upload data", Toast.LENGTH_SHORT).show()
            }
    }
    }
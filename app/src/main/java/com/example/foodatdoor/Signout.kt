package com.example.foodatdoor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.foodatdoor.databinding.ActivitySignoutBinding
import com.example.foodatdoor.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Signout : AppCompatActivity() {
    lateinit var binding:ActivitySignoutBinding
    lateinit var userName:String
    lateinit var resturantName:String
    lateinit var email:String
    lateinit var password:String
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance().reference

        binding.TextView.setOnClickListener(View.OnClickListener {

            var intent=Intent(this,SigninActivity::class.java)
            startActivity(intent)
        })
        binding.SignINButton.setOnClickListener(View.OnClickListener {
            email=binding.editTextTextEmailAddress2.text.toString()
            userName=binding.editTextTextEmailAddress2.text.toString()

            password=binding.editTextNumberPassword.text.toString()
            resturantName=binding.editTextName.text.toString()

            if(email.isBlank()||userName.isBlank()||password.isBlank()||resturantName.isBlank()){
            }
            else{
                createAccount(email,password);
            }
        })
    }
    public fun createAccount(email:String,password:String) {
        val usersRef = FirebaseDatabase.getInstance().getReference("user")

        usersRef.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // Email already exists in the database
                    } else {
                        // Email does not exist, proceed with account creation
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    saveDatabase()
                                    startActivity(Intent(this@Signout, SigninActivity::class.java))
                                    finish()
                                } else {
                                }
                            }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }
    public fun saveDatabase(){
        email=binding.editTextTextEmailAddress2.text.toString()
        userName=binding.editTextTextEmailAddress2.text.toString()

        password=binding.editTextNumberPassword.text.toString()
        resturantName=binding.editTextName.text.toString()

        val user=User(userName,email,password)
        val newId=FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(newId).setValue(user)
    }
}
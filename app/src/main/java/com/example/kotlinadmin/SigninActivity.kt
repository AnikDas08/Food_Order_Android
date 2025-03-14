package com.example.kotlinadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kotlinadmin.databinding.ActivitySigninBinding
import com.example.kotlinadmin.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SigninActivity : AppCompatActivity() {

    lateinit var binding:ActivitySigninBinding
    lateinit var userName:String
    lateinit var resturantName:String
    lateinit var email:String
    lateinit var password:String
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance().reference

        binding.SignUpId.setOnClickListener(View.OnClickListener {
            email=binding.editTextTextEmailAddress2.text.toString()
            userName=binding.editTextTextEmailAddress2.text.toString()

            password=binding.editTextNumberPassword.text.toString()
            resturantName=binding.NameRustarentId.text.toString()

            if(email.isBlank()||userName.isBlank()||password.isBlank()||resturantName.isBlank()){
            }
            else{
                createAccount(email,password);
            }
        })

        binding.GoLogin.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        })

    }
    public fun createAccount(email:String,password:String) {
        val usersRef = FirebaseDatabase.getInstance().getReference("admin")

        usersRef.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // Email already exists in the database
                        Toast.makeText(this@SigninActivity, "This email is already registered. Try logging in.", Toast.LENGTH_SHORT).show()
                    } else {
                        // Email does not exist, proceed with account creation
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    saveDatabase()
                                    Toast.makeText(this@SigninActivity, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@SigninActivity, LoginActivity::class.java))
                                    finish()
                                } else {
                                    Toast.makeText(this@SigninActivity, "Account Creation Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@SigninActivity, "Database Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
    public fun saveDatabase(){
        email=binding.editTextTextEmailAddress2.text.toString()
        userName=binding.editTextTextEmailAddress2.text.toString()

        password=binding.editTextNumberPassword.text.toString()
        resturantName=binding.NameRustarentId.text.toString()
        val user=User(userName,resturantName,email,password)
        val newId=FirebaseAuth.getInstance().currentUser!!.uid
        database.child("admin").child(newId).setValue(user)
    }
}
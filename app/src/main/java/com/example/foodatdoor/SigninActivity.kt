package com.example.foodatdoor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.foodatdoor.databinding.ActivitySigninBinding
import com.google.android.gms.signin.SignInOptions
import com.google.android.gms.signin.internal.SignInClientImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SigninActivity : AppCompatActivity() {
    lateinit var binding:ActivitySigninBinding
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var email:String
    lateinit var password:String
    private lateinit var googleSignInClient: SignInOptions
    private lateinit var oneTapClient: SignInClientImpl
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance().reference

        binding.TextView.setOnClickListener(View.OnClickListener {
            val intent=Intent(this,Signout::class.java)
            startActivity(intent)
            finish()
        })
        binding.Loginbutton.setOnClickListener(View.OnClickListener {
            email=binding.editTextTextEmailAddress2.text.toString()
            password=binding.editTextNumberPassword.text.toString()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Email and Password are required", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        })

    }

    public fun loginUser(email:String,password:String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java) // Redirect to main activity
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUsers:FirebaseUser?=auth.currentUser
        if(currentUsers!=null){
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
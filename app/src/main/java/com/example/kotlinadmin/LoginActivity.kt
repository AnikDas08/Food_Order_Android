package com.example.kotlinadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kotlinadmin.databinding.ActivityLoginBinding
import com.google.android.gms.signin.SignInOptions
import com.google.android.gms.signin.internal.SignInClientImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    lateinit var bindig:ActivityLoginBinding
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var email:String
    lateinit var password:String
    private lateinit var googleSignInClient: SignInOptions
    private lateinit var oneTapClient: SignInClientImpl
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindig= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindig.root)
        auth=FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance().reference

        bindig.Loginbutton.setOnClickListener(View.OnClickListener {
            email=bindig.editTextTextEmailAddress2.text.toString()
            password=bindig.editTextNumberPassword.text.toString()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Email and Password are required", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        })
        bindig.TextView.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,SigninActivity::class.java))
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
                Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
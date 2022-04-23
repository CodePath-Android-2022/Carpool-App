package com.example.carpool

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Check current user
        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    private var authStateListener: FirebaseAuth.AuthStateListener =
        FirebaseAuth.AuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser == null) {
                val intent = Intent(this@MainActivity, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }
            if (firebaseUser != null) {
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
}

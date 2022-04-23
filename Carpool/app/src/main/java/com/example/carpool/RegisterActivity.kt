package com.example.carpool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.parse.ParseUser

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        findViewById<Button>(R.id.register_button).setOnClickListener {
            val firstName = findViewById<EditText>(R.id.et_firstName).text.toString()
            val lastName = findViewById<EditText>(R.id.et_lastName).text.toString()
            val email = findViewById<EditText>(R.id.et_signup_email).text.toString()
            val password = findViewById<EditText>(R.id.et_signup_password).text.toString()
            val username = findViewById<EditText>(R.id.et_signup_username).text.toString()
            signUpUser(username, firstName, lastName, email, password)
        }

        findViewById<TextView>(R.id.tv_Signin).setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUpUser(username: String, firstname: String, lastname: String, email: String, password: String) {
        // Create the ParseUser
        val user = ParseUser()

        // Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)
        user.setEmail(email)
        //set all fields for user's profile to save
        user.put("firstName", firstname)
        user.put("lastName", lastname)

        user.signUpInBackground { e ->
            if (e == null) {
                Toast.makeText(this, "Successfully Signed In", Toast.LENGTH_SHORT).show()
                goToMainActivity()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Error Signing up", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun goToMainActivity() {
        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object{
        val TAG = "RegisterFragment"
    }
}
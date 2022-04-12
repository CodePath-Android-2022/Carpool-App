package com.example.carpool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseObject
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //check if user's already logged in
        //if true, take them to MainActivity
        if(ParseUser.getCurrentUser() != null) {
            goToMainActivity()
        }

        findViewById<Button>(R.id.login_button).setOnClickListener {
            val username = findViewById<EditText>(R.id.et_username).text.toString()
            val password = findViewById<EditText>(R.id.et_password).text.toString()

            loginUser(username, password)
        }
    }

    //Test login:
    //username: john_doe
    //password: john123

    fun loginUser(username: String, password: String) {
        ParseUser.logInInBackground(username, password, ({ user, e->
            if (user != null) {
                Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
                goToMainActivity()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Error loggin in", Toast.LENGTH_LONG).show()
            }
        }))
    }

    private fun goToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
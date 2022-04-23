package com.example.carpool

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pasa.carpool.R.layout.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(activity_home)
        val bottomNavigationView =
            findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_favorites -> Toast.makeText(applicationContext, "Home", Toast.LENGTH_SHORT).show()
                R.id.action_home -> Toast.makeText(applicationContext, "Create", Toast.LENGTH_SHORT).show()
                R.id.action_profile -> {
                    Toast.makeText(applicationContext, "Profile", Toast.LENGTH_SHORT).show()
                    val inentProfile = Intent(this@HomeActivity, ProfileActivity::class.java)
                    startActivity(inentProfile)
                }
            }
            true
        }
    }
}

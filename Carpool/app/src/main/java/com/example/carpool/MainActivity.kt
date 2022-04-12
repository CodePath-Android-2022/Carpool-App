package com.example.carpool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.carpool.fragments.ComposeFragment
import com.example.carpool.fragments.FeedFragment
import com.example.carpool.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
             var fragmentToDisplay: Fragment? = null
            when (item.itemId) {
                R.id.action_home -> {
                    fragmentToDisplay = FeedFragment()
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                }
                R.id.action_compose -> {
                    fragmentToDisplay = ComposeFragment()
                    Toast.makeText(this, "Compose Ride", Toast.LENGTH_SHORT).show()
                }
                R.id.action_profile -> {
                    fragmentToDisplay = ProfileFragment()
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                }
            }

            if (fragmentToDisplay != null) {
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToDisplay).commit()
            }
            true
        }

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.action_home
    }
}
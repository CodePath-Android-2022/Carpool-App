package com.example.carpool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.carpool.fragments.ComposeFragment
import com.example.carpool.fragments.FeedFragment
import com.example.carpool.fragments.ProfileFragment
import com.example.carpool.fragments.SearchFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.ParseUser

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_profileIcon -> {
                    // Take user to the profile page
                    Log.i(TAG, "Profile was clicked")
                    fragmentManager.beginTransaction().replace(R.id.flContainer, ProfileFragment()).commit()
                    true
                }
                R.id.action_signout -> {
                    ParseUser.logOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    Toast.makeText(this, "Successfully logged out", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    finish()

                    Log.i(TAG, "Sign out was clicked")
                    true
                }
                else -> false
            }
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
             var fragmentToDisplay: Fragment? = null
            when (item.itemId) {
                R.id.action_search -> {
                    fragmentToDisplay = SearchFragment()
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                }

                R.id.action_home -> {
                    fragmentToDisplay = FeedFragment()
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                }
                R.id.action_compose -> {
                    fragmentToDisplay = ComposeFragment()
                    Toast.makeText(this, "Compose Ride", Toast.LENGTH_SHORT).show()
                }
                /*
                R.id.action_profile -> {
                    fragmentToDisplay = ProfileFragment()
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                }
                 */
            }

            if (fragmentToDisplay != null) {
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToDisplay).commit()
            }
            true
        }

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.action_home

    }

    companion object{
        val TAG = "MainActivity"
    }
}
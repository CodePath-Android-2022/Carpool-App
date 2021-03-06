package com.example.carpool

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.carpool.fragments.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.parse.ParseUser

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)

        //Get data from intent from search and add it to a bundle to send off to Explore
        val fromLocation = intent.getStringExtra("fromLocation")
        val toLocation = intent.getStringExtra("toLocation")
        val priceMin = intent.getStringExtra("priceMin")
        val priceMax = intent.getStringExtra("priceMax")

        val args = Bundle()
        args.putString("fromLocation", fromLocation)
        args.putString("toLocation", toLocation)
        args.putString("priceMin", priceMin)
        args.putString("priceMax", priceMax)



        //floating button
        val fab = findViewById<FloatingActionButton>(R.id.action_compose)

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
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
                    fab.hide()
                    fragmentToDisplay = SearchFragment()
                    fragmentToDisplay.arguments = args
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                }

                R.id.action_home -> {
                    fab.show()
                    fragmentToDisplay = FeedFragment()
                    fragmentToDisplay.arguments = args
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                }
                R.id.action_compose -> {
                    fab.hide()
                    fragmentToDisplay = ComposeFragment()
                    Toast.makeText(this, "Compose Ride", Toast.LENGTH_SHORT).show()
                }
                R.id.action_profileIcon -> {
                    fab.hide()
                    // Take user to the profile page
                    Log.i(TAG, "Profile was clicked")
                    fragmentManager.beginTransaction().replace(R.id.flContainer, ProfileFragment()).commit()
                }
            }

            if (fragmentToDisplay != null) {
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToDisplay).commit()
                    //.addToBackStack("optional tag")
            }
            true
        }

        //navigating back to the homepage
        fab.setOnClickListener{
            fragmentManager.beginTransaction().replace(R.id.flContainer, ComposeFragment()).addToBackStack("ExploreFragment").commit()
        }

        //Allowing user to navigate backward
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        }

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.action_home


    }

    companion object{
        val TAG = "MainActivity"
    }
}
package com.example.carpool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class DetailedRideView : AppCompatActivity() {

    private lateinit var ivProfileImage: ImageView
    private lateinit var tvUsername: TextView
    private lateinit var tvTweetBody: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_ride_view)

    }
}
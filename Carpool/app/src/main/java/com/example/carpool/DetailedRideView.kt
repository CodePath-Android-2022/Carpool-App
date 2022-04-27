package com.example.carpool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.parse.ParseUser

class DetailedRideView : AppCompatActivity() {

    private lateinit var tvHostUsername: TextView
    private lateinit var ivProfileImage: ImageView
    private lateinit var tvFromLocation: TextView
    private lateinit var tvToLocation: TextView
    private lateinit var tvDescriptionText: TextView
    private lateinit var tvCapacityValue: TextView
    private lateinit var tvCost: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_ride_view)

        // get reference to the views for detail screen
        tvHostUsername = findViewById(R.id.tv_host_username)
        ivProfileImage = findViewById(R.id.iv_profile_image)
        tvFromLocation = findViewById(R.id.tv_from_location)
        tvToLocation = findViewById(R.id.tv_to_location)
        tvDescriptionText = findViewById(R.id.tv_description_text)
        tvCapacityValue = findViewById(R.id.tvTotalCap_DV)
        tvCost = findViewById(R.id.tvCostContent_DV)

        // get CarpoolPost object out of intent's putExtra
        val carpoolPost = intent.getParcelableExtra<CarpoolPost>(CARPOOL_POST_EXTRA) as CarpoolPost

        // take data from the carpoolPost object and populate the detail screen views
        tvHostUsername.text = carpoolPost.getUser()?.username
        // can't load in the trip creator's profile image. we do not have a function to retrieve that yet
        // Glide.with(this).load(current ride owner's profile photo).into(ivProfileImage)
        tvFromLocation.text = carpoolPost.getSourceLocation()
        tvToLocation.text = carpoolPost.getDestinationLocation()
        tvDescriptionText.text = carpoolPost.getDescription()
        tvCapacityValue.text = carpoolPost.getCarCapacity().toString()
        tvCost.text = carpoolPost.getPrice().toString()
        val user = carpoolPost.getUser()
        val userParseImage = user?.getParseFile("profileImg")
        Glide.with(this).load(userParseImage?.url).into(ivProfileImage)


    }
}

/**
 * We do not have a function to return the list of people who have joined a trip. We could do that for improving the app's functionality
 *
 */

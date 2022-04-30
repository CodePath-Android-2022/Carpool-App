package com.example.carpool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.carpool.adapters.CARPOOL_POST_EXTRA
import com.parse.ParseUser
import java.text.DateFormat

class DetailedRideView : AppCompatActivity() {

    private lateinit var tvHostUsername: TextView
    private lateinit var ivProfileImage: ImageView
    private lateinit var tvFromLocation: TextView
    private lateinit var tvToLocation: TextView
    private lateinit var tvDescriptionText: TextView
    private lateinit var tvCapacityValue: TextView
    private lateinit var tvDetailedCreatedAt: TextView
    private lateinit var tvCost: TextView
    private lateinit var btnAcceptRideDetailed: Button

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
        tvDetailedCreatedAt = findViewById(R.id.tvDetailedCreatedAt)
        tvCost = findViewById(R.id.tvCostContent_DV)
        btnAcceptRideDetailed = findViewById(R.id.btnAcceptRideDetailed)

        // get CarpoolPost object out of intent's putExtra
        val carpoolPost = intent.getParcelableExtra<CarpoolRide>(CARPOOL_POST_EXTRA) as CarpoolRide

        val currentUser = ParseUser.getCurrentUser()
        val fullname = "${currentUser.get("firstName")} ${currentUser.get("lastName")}"
        // take data from the carpoolPost object and populate the detail screen views
        tvHostUsername.text = fullname
        // can't load in the trip creator's profile image. we do not have a function to retrieve that yet
        // Glide.with(this).load(current ride owner's profile photo).into(ivProfileImage)
        tvFromLocation.text = carpoolPost.getSourceLocation()
        tvToLocation.text = carpoolPost.getDestinationLocation()
        tvDescriptionText.text = carpoolPost.getDescription()
        tvCapacityValue.text = carpoolPost.getCarCapacity().toString()
        tvCost.text = carpoolPost.getPrice().toString()

        val format: DateFormat = DateFormat.getDateInstance()
        val date = carpoolPost.createdAt
        //tv_createdAt.text = format.format(date)
        tvDetailedCreatedAt.text = date.toLocaleString()

        val user = carpoolPost.getUser()
        val userParseImage = user?.getParseFile("profileImg")
        Glide.with(this).load(userParseImage?.url).into(ivProfileImage)

        val rideUserID = ParseUser.getCurrentUser().objectId
        val userRequests = carpoolPost.get("userRequests") as ArrayList<String>
        if (userRequests.contains(rideUserID)) {
            btnAcceptRideDetailed.text = "Joined"
            btnAcceptRideDetailed.setEnabled(false)
        }


    }
}

/**
 * We do not have a function to return the list of people who have joined a trip. We could do that for improving the app's functionality
 *
 */

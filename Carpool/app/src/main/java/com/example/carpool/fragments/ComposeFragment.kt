package com.example.carpool.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.carpool.CarpoolPost
import com.example.carpool.R
import com.parse.ParseUser
import java.util.*


/**
 * Add functionality so users can create a new Carpool
 */
class ComposeFragment : Fragment() {

    private val TAG = "ComposeFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false)
    }


    // set onclick listeners and setup logic
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // fill username view with current user's username
        view.findViewById<TextView>(R.id.tv_username).text = ParseUser.getCurrentUser().username

        // get reference to the create carpool button view
        view.findViewById<Button>(R.id.btn_create_carpool).setOnClickListener {

            // grab the start location the user has inputted
            val sourceLocation = view.findViewById<EditText>(R.id.et_start_location).text.toString()

            // grab the destination location the user has inputted
            val destinationLocation = view.findViewById<EditText>(R.id.et_destination_location).text.toString()

            // grab the departure date the user has inputted
            val departureDate = view.findViewById<EditText>(R.id.et_departure_date).text.toString()

            // grab the departure time the user has inputted
            val departureTime = view.findViewById<EditText>(R.id.et_departure_time).text.toString()

            // grab the car capacity the user has inputted
            val carCapacity = view.findViewById<EditText>(R.id.et_car_capacity).text.toString()

            // grab the trip description the user has inputted
            val description = view.findViewById<EditText>(R.id.et_trip_description).text.toString()

            // grab the price the user has inputted
            val price = view.findViewById<EditText>(R.id.et_trip_price).text.toString()

            // check that all fields are filled & submit info to the home page after the user has filled all required information
            if (sourceLocation.isEmpty() || destinationLocation.isEmpty() || departureDate.isEmpty() ||  departureTime.isEmpty() ||  carCapacity.isEmpty() ||  description.isEmpty() ||  price.isEmpty()) {  // some or all fields are empty
                Toast.makeText(context, "Please fill all fields!", Toast.LENGTH_SHORT).show()
            }
            else if (sourceLocation.isNotEmpty() && destinationLocation.isNotEmpty() && departureDate.isNotEmpty() &&  departureTime.isNotEmpty() &&  carCapacity.isNotEmpty() &&  description.isNotEmpty() &&  price.isNotEmpty()) {  // all fields are filled
                submitCarpoolPostToServer(ParseUser.getCurrentUser(), sourceLocation, destinationLocation, departureDate, departureTime, carCapacity.toInt(), description, price.toFloat())
            }
        }
    }


    // function to submit created carpool post to server
    private fun submitCarpoolPostToServer(user: ParseUser, sourceLocation: String, destinationLocation: String, departureDate: String, departureTime: String, carCapacity: Int, description: String, price: Float) {

        /**
         * WHAT'S IN THE SERVER
         * --------------------
         * user: Pointer<User>
         * sourceLocation: String
         * destinationLocation: String
         * departureDate: Date
         * departureTime: String
         * carCapacity: Number
         * description: String
         * price: Number
         */

        // create a Carpool post object to send to server
        val carpoolPost = CarpoolPost()

        // set all fields to save
        carpoolPost.setUser(user)
        carpoolPost.setSourceLocation(sourceLocation)
        carpoolPost.setDestinationLocation(destinationLocation)
        carpoolPost.setDepartureDate(departureDate)
        carpoolPost.setDepartureTime(departureTime)
        carpoolPost.setCarCapacity(carCapacity)
        carpoolPost.setDescription(description)
        carpoolPost.setPrice(price)

        // save post to server
        carpoolPost.saveInBackground { exception ->
            if (exception != null) {  // then something's gone wrong
                Toast.makeText(context, "Something went wrong! Couldn't save post.", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Something went wrong! Couldn't save post.")
                exception.printStackTrace()
            }
            else {  // everything is good
                Toast.makeText(context, "Successfully saved post in server!", Toast.LENGTH_SHORT).show()
                Log.i(TAG, "Successfully saved post in server!")
            }
        }
    }
}

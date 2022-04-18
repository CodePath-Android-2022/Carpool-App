package com.example.carpool.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.carpool.R
import com.parse.ParseUser


/**
 * Add functionality so users can create a new Carpool
 */
class ComposeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // fill username view with current user's username
        container?.findViewById<TextView>(R.id.tv_username)?.text = ParseUser.getCurrentUser().username

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false)
    }


    // set onclick listeners and setup logic
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // get reference to the start location edit text view
        view.findViewById<EditText>(R.id.et_start_location).setOnClickListener {
            // grab the start location the user has inputted
        }

        // get reference to the destination location edit text view
        view.findViewById<EditText>(R.id.et_destination_location).setOnClickListener {
            // grab the destination location the user has inputted
        }

        // get reference to the departure date edit text view
        view.findViewById<EditText>(R.id.et_departure_date).setOnClickListener {
            // grab the departure date the user has inputted
        }

        // get reference to the car capacity edit text view
        view.findViewById<EditText>(R.id.et_car_capacity).setOnClickListener {
            // grab the car capacity the user has inputted
        }

        // get reference to the trip description edit text view
        view.findViewById<EditText>(R.id.et_trip_description).setOnClickListener {
            // grab the trip description the user has inputted
        }

        // get reference to the price edit text view
        view.findViewById<EditText>(R.id.et_trip_price).setOnClickListener {
            // grab the price the user has inputted
        }

        // get reference to the create carpool button view
        view.findViewById<Button>(R.id.btn_create_carpool).setOnClickListener {
            // todo: submit info to the home page after the user has filled all required information
            Toast.makeText(context, "Create Carpool Button Clicked!", Toast.LENGTH_SHORT).show()
        }
    }
}

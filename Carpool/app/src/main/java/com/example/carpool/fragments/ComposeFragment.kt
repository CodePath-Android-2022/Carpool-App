package com.example.carpool.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.carpool.CarpoolPost
import com.example.carpool.R
import com.parse.ParseUser
import java.text.SimpleDateFormat
import java.util.*


/**
 * Add functionality so users can create a new Carpool
 */

private const val TAG = "ComposeFragment"

class ComposeFragment : Fragment() {

    // declare variables for views in xml layout file
    private lateinit var tvUsername: TextView
    private lateinit var etSourceLocation: EditText
    private lateinit var etDestinationLocation: EditText
    private lateinit var btnDepartureDate: Button
    private lateinit var btnDepartureTime: Button
    private lateinit var etCarCapacity: EditText
    private lateinit var etDescription: EditText
    private lateinit var etPrice: EditText

    // get instance of the calendar object
    val calendar: Calendar = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false)
    }


    // set onclick listeners and setup logic
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get reference to all text views and edit text fields
        tvUsername = view.findViewById(R.id.tv_username)
        etSourceLocation = view.findViewById(R.id.et_start_location)
        etDestinationLocation =view.findViewById(R.id.et_destination_location)
        btnDepartureDate =view.findViewById(R.id.btn_departure_date)
        btnDepartureTime = view.findViewById(R.id.btn_departure_time)
        etCarCapacity = view.findViewById(R.id.et_car_capacity)
        etDescription = view.findViewById(R.id.et_trip_description)
        etPrice = view.findViewById(R.id.et_trip_price)

        // fill username view with current user's username
        tvUsername.text = ParseUser.getCurrentUser().username

        // to show a date selector dialogue box
        val dateSetListener = object: DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                updateDateInView()
            }
        }

        // set a click listener on the departureDate ET field
        btnDepartureDate.setOnClickListener {
            Toast.makeText(context, "departure date EditText field clicked!", Toast.LENGTH_SHORT).show()
            // set DatePickerDialog to point to today's date when it loads up
            DatePickerDialog(requireContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        } /*(object : View.OnClickListener {
            override fun onClick(view: View?) {

            }
        })*/


        // get reference to the create carpool button view
        view.findViewById<Button>(R.id.btn_create_carpool).setOnClickListener {

            // grab all the info the user has inputted
            val sourceLocation = etSourceLocation.text.toString()
            val destinationLocation = etDestinationLocation.text.toString()
            val departureDate = btnDepartureDate.text.toString()
            val departureTime = btnDepartureTime.text.toString()
            val carCapacity = etCarCapacity.text.toString()
            val description = etDescription.text.toString()
            val price = etPrice.text.toString()

            // check that all fields are filled & submit info to the home page after the user has filled all required information
            if (sourceLocation.isEmpty() || destinationLocation.isEmpty() || departureDate.isEmpty() ||  departureTime.isEmpty() ||  carCapacity.isEmpty() ||  description.isEmpty() ||  price.isEmpty()) {  // some or all fields are empty
                Toast.makeText(context, "Please fill all fields!", Toast.LENGTH_SHORT).show()
            }
            else if (sourceLocation.isNotEmpty() && destinationLocation.isNotEmpty() && departureDate.isNotEmpty() &&  departureTime.isNotEmpty() &&  carCapacity.isNotEmpty() &&  description.isNotEmpty() &&  price.isNotEmpty()) {  // all fields are filled

                // Toast & log the user's typed info to logcat before clearing the fields
                // Toast.makeText(context, "Submitting post to server!", Toast.LENGTH_SHORT).show()
                Log.i(TAG,  "$sourceLocation, $destinationLocation, $departureDate, $departureTime, $carCapacity, $description, $price")

                submitCarpoolPostToServer(ParseUser.getCurrentUser(), sourceLocation, destinationLocation, departureDate, departureTime, carCapacity.toInt(), description, price.toFloat())

                // empty all edit text fields after post is saved
                etSourceLocation.setText("")
                etDestinationLocation.setText("")
                btnDepartureDate.setText("")
                btnDepartureTime.setText("")
                etCarCapacity.setText("")
                etDescription.setText("")
                etPrice.setText("")
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

        // create a Carpool post object to be sent to the server
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
            if (exception == null) {  // everything is good
                Toast.makeText(context, "Successfully saved post in server!", Toast.LENGTH_SHORT).show()
                Log.i(TAG, "Successfully saved post in server!")
            }
            else {  // then something's gone wrong
                Toast.makeText(context, "Something went wrong! Couldn't save post.", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Something went wrong! Couldn't save post. Error message: ${exception.message}")  // todo: fix the ff error: "Something went wrong! Couldn't save post. Error message: schema mismatch for CarpoolPost.departureDate; expected Date but got String"
            }
        }
    }

    // set the format of the date
    private fun updateDateInView() {
        val format = "mm/dd/yyyy"
        val simpleDateFormat = SimpleDateFormat(format, Locale.US)
        btnDepartureDate.setText(simpleDateFormat.format(calendar.time))
    }
}

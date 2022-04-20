package com.example.carpool.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
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

class ComposeFragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    // declare variables for views in xml layout file
    private lateinit var tvUsername: TextView
    private lateinit var etSourceLocation: EditText
    private lateinit var etDestinationLocation: EditText
    private lateinit var btnDepartureDate: Button
    private lateinit var tvDepartureDate: TextView
    private lateinit var btnDepartureTime: Button
    private lateinit var tvDepartureTime: TextView
    private lateinit var etCarCapacity: EditText
    private lateinit var etDescription: EditText
    private lateinit var etPrice: EditText

    // get instance of the calendar object
    private val calendar: Calendar = Calendar.getInstance()

    // create variables for the date and time info
    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0


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
        etDestinationLocation = view.findViewById(R.id.et_destination_location)
        btnDepartureDate = view.findViewById(R.id.btn_departure_date)
        tvDepartureDate = view.findViewById(R.id.tv_departure_date)
        btnDepartureTime = view.findViewById(R.id.btn_departure_time)
        tvDepartureTime = view.findViewById(R.id.tv_departure_time)
        etCarCapacity = view.findViewById(R.id.et_car_capacity)
        etDescription = view.findViewById(R.id.et_trip_description)
        etPrice = view.findViewById(R.id.et_trip_price)


        pickDate()
        pickTime()


        // declare variables to hold the current time
        var currentHour: Int = 0
        var currentMinute: Int = 0
        var amPm: String = ""


        // fill username view with current user's username
        tvUsername.text = ParseUser.getCurrentUser().username

        /*
        // create an OnDateSetListener
        val dateSetListener = object: DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_WEEK, day)
                updateDateInView()
            }
        }

        // when the departureDate button is clicked, show DatePickerDialog that is set with OnDateSetListener
        btnDepartureDate.setOnClickListener (object : View.OnClickListener {
            override fun onClick(view: View?) {
                // set DatePickerDialog to point to today's date when it loads up
                val datePickerDialog = DatePickerDialog(requireContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                datePickerDialog.show()
            }
        })
        */

        /*
        // when the departureTime button is clicked, show the TimePickerDialog that is set with OnTimeSetListener
        btnDepartureTime.setOnClickListener (object : View.OnClickListener {
            override fun onClick(view: View?) {

                // set the time chosen by the user
                currentHour = calendar.get(Calendar.HOUR_OF_DAY)
                currentMinute = calendar.get(Calendar.MINUTE)

                // set TimePickerDialog to point to current time when it loads up
                val timePickerDialog: TimePickerDialog = TimePickerDialog(requireContext(), object: TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(timePicker: TimePicker, hourOfDay: Int, minutes: Int) {
                         calendar.set(Calendar.HOUR, hourOfDay)
                         calendar.set(Calendar.MINUTE, minutes)

                        if (hourOfDay >= 12) {
                            amPm = "pm"
                        }
                        else {
                            amPm = "am"
                        }

                        // set the selected time in the text view
                        val chosenTime = String.format("%02d:%02d", currentHour, currentMinute) + " " + amPm
                        tvDepartureTime.text = chosenTime
                    }
                }, currentHour, currentMinute, false)

                // display the time picker dialog
                timePickerDialog.show()
            }
        })
        */


        // get reference to the create carpool button view
        view.findViewById<Button>(R.id.btn_create_carpool).setOnClickListener {

            // grab all the info the user has inputted
            val sourceLocation = etSourceLocation.text.toString()
            val destinationLocation = etDestinationLocation.text.toString()
            val departureDate = tvDepartureDate.text.toString()
            val departureTime = tvDepartureTime.text.toString()
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
                tvDepartureDate.setText("")
                tvDepartureTime.setText("")
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
        tvDepartureDate.text = simpleDateFormat.format(calendar.time)
    }


    private fun getDateTimeCalendar() {
        val calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
    }

    private fun pickDate() {
        btnDepartureDate.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
    }

    private fun pickTime() {
        btnDepartureTime.setOnClickListener {
            getDateTimeCalendar()
            TimePickerDialog(requireContext(), this, hour, minute, true).show()
        }
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalendar()

        val monthNames = arrayOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
        tvDepartureDate.text = "${monthNames.get(savedMonth)} $savedDay $savedYear"
    }

    override fun onTimeSet(timePicker: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        getDateTimeCalendar()

        if (savedHour in 0..9 && savedMinute !in 0..9) {
            tvDepartureTime.text = "0"+savedHour+":"+savedMinute
        }
        else if (savedMinute in 0..9 && savedHour !in 0..9) {
            tvDepartureTime.text = ""+savedHour+":0"+savedMinute
        }
        else if (savedHour in 0..9 && savedMinute in 0..9) {
            tvDepartureTime.text = "0"+savedHour+":0"+savedMinute
        }
        else {
            tvDepartureTime.text = "$savedHour:$savedMinute"
        }
    }
}

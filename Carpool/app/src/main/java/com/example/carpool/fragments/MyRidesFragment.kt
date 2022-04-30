package com.example.carpool.fragments

import android.util.Log
import android.view.ViewGroup
import com.example.carpool.CarpoolRide
import com.example.carpool.R
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser


class MyRidesFragment : ExploreFragment() {

    override fun getListOfAvailableRides() {
        // specify which class to query
        val query: ParseQuery<CarpoolRide> = ParseQuery.getQuery(CarpoolRide::class.java)

        query.include(CarpoolRide.KEY_USER)
        query.whereEqualTo("members", ParseUser.getCurrentUser().objectId);
        query.addDescendingOrder("createdAt");

        query.findInBackground(object : FindCallback<CarpoolRide> {
            override fun done(rides: MutableList<CarpoolRide>?, e: ParseException?) {
                if (e != null) {
                    Log.e(TAG, "Error fetching message " + e)
                } else {
                    if (rides != null) {
                        for (ride in rides) {
                            Log.i(TAG, ride.toString())
                        }
                        listOfRides.addAll(rides)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

        })

    }

    companion object {
        val TAG = "RidesFragment"
    }

}
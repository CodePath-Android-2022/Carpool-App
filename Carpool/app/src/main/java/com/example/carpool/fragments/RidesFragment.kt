package com.example.carpool.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.carpool.CarpoolPost
import com.example.carpool.R
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser


class RidesFragment : ExploreFragment() {

    override fun getListOfAvailableRides() {
        // specify which class to query
        val query: ParseQuery<CarpoolPost> = ParseQuery.getQuery(CarpoolPost::class.java)

        query.include(CarpoolPost.KEY_USER)
        //only return posts from currently signed in user
        query.whereEqualTo(CarpoolPost.KEY_USER, ParseUser.getCurrentUser())

        //return posts in descending order: newer posts first.
        query.addDescendingOrder("createdAt");

        query.findInBackground(object : FindCallback<CarpoolPost> {
            override fun done(rides: MutableList<CarpoolPost>?, e: ParseException?) {
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
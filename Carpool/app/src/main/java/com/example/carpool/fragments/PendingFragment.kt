package com.example.carpool.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carpool.R
import com.example.carpool.RideRequest
import com.example.carpool.adapters.PendingPostAdapter
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser


class PendingFragment : Fragment() {


    lateinit var pendingRecyclerView: RecyclerView
    lateinit var adapter: PendingPostAdapter
    var listOfRides: MutableList<RideRequest> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pendingRecyclerView = view.findViewById(R.id.pendingRecyclerView)
        adapter = PendingPostAdapter(requireContext(), listOfRides)
        pendingRecyclerView.adapter = adapter
        pendingRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        getListOfPendingRides()
    }

    private fun getListOfPendingRides() {
        val query: ParseQuery<RideRequest> = ParseQuery.getQuery(RideRequest::class.java)
        //query.include(RideRequest.KEY_HOST)
        query.whereEqualTo("hostID", ParseUser.getCurrentUser().objectId)
        query.addDescendingOrder("createdAt")
        //query.whereEqualTo("availability", true)
        query.findInBackground(object : FindCallback<RideRequest> {
            override fun done(rides: MutableList<RideRequest>?, e: ParseException?) {
                if (e != null) {
                    Log.e(TAG, "Error fetching pending rides "+e)
                } else {
                    if (rides != null) {
                        for (ride in rides) {
                            Log.i(TAG, ride.toString() + (ride.getHost()?.objectId))
                        }
                        listOfRides.addAll(rides)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

        })
    }

    companion object{
        val TAG = "PendingFragment"
    }
}
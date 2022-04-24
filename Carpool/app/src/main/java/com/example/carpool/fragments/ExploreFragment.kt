package com.example.carpool.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carpool.CarpoolPost
import com.example.carpool.CarpoolPostAdapter
import com.example.carpool.R
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery


class ExploreFragment : Fragment() {
    lateinit var ridesRecyclerView: RecyclerView
    lateinit var adapter: CarpoolPostAdapter
    var listOfRides: MutableList<CarpoolPost> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ridesRecyclerView = view.findViewById(R.id.rideRecyclerView)
        adapter = CarpoolPostAdapter(requireContext(), listOfRides)
        ridesRecyclerView.adapter = adapter
        ridesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        getListOfAvailableRides()
    }

    private fun getListOfAvailableRides() {
        // specify which class to query
        val query: ParseQuery<CarpoolPost> = ParseQuery.getQuery(CarpoolPost::class.java)

        query.include("user")
        query.findInBackground(object : FindCallback<CarpoolPost>{
            override fun done(rides: MutableList<CarpoolPost>?, e: ParseException?) {
                if (e != null) {
                    Log.e(TAG, "Error fetching message")
                } else {
                    if (rides != null) {
                        listOfRides.addAll(rides)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

        })

    }

    companion object {
        private const val TAG = "ExploreFragment"
    }

}
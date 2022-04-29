package com.example.carpool.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carpool.CarpoolRide
import com.example.carpool.R
import com.example.carpool.adapters.CarpoolPostAdapter
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery


open class ExploreFragment : Fragment() {

    lateinit var ridesRecyclerView: RecyclerView
    lateinit var adapter: CarpoolPostAdapter

    var listOfRides: MutableList<CarpoolRide> = mutableListOf()

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
//
//        //Allowing user to navigate backward
//        if (fragmentManager.backStackEntryCount > 0) {
//            fragmentManager.popBackStack()
//

//        if (fromLocation == "" && toLocation == "" && priceMin == "" && priceMax == "") {
//            getListOfAvailableRides()
//        } else {
//            getListOfAvailableRides(fromLocation, toLocation, priceMin, priceMax)
//        }
        getListOfAvailableRides()


        //TODO: Implement the join feature


    }

    //need to have this return list of rides (object/dictionary)
    open fun getListOfAvailableRides(fromLocation: String?, toLocation: String?, priceMin: String?, priceMax: String?) {
        // specify which class to query
        val query: ParseQuery<CarpoolRide> = ParseQuery.getQuery(CarpoolRide::class.java)

        //check if location is not empty then add to query
        //Add price to query if not empty
        query.include(CarpoolRide.KEY_USER)
        query.addDescendingOrder("createdAt")
        //TODO: Return only most recent 20 posts and implement infinite scroll
        query.whereEqualTo("availability", true);
        query.findInBackground(object : FindCallback<CarpoolRide>{
            override fun done(rides: MutableList<CarpoolRide>?, e: ParseException?) {
                if (e != null) {
                    Log.e(TAG, "Error fetching message "+e)
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

    open fun getListOfAvailableRides() {

        val query: ParseQuery<CarpoolRide> = ParseQuery.getQuery(CarpoolRide::class.java)
        query.include(CarpoolRide.KEY_USER)
        query.addDescendingOrder("createdAt")
        query.whereEqualTo("availability", true)
        query.findInBackground(object : FindCallback<CarpoolRide>{
            override fun done(rides: MutableList<CarpoolRide>?, e: ParseException?) {
                if (e != null) {
                    Log.e(TAG, "Error fetching message "+e)
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
        private const val TAG = "ExploreFragment"
    }

}
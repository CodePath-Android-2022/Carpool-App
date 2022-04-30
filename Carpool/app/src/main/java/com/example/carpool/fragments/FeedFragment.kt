package com.example.carpool.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.carpool.R
import com.example.carpool.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class FeedFragment : Fragment() {
    private var fromLocation: String = ""
    private var toLocation: String = ""
    private var priceMin: String = ""
    private var priceMax: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fromLocation = requireArguments().getString("fromLocation").toString()
        toLocation = requireArguments().getString("toLocation").toString()
        priceMin = requireArguments().getString("priceMin").toString()
        priceMax = requireArguments().getString("priceMax").toString()
        Log.i(TAG, "DATA GAINED FROM INTENT ${fromLocation}")


        val tabLayout = view.findViewById<TabLayout>(R.id.tabs)
        val viewPager2 = view.findViewById<ViewPager2>(R.id.viewPager)


        val adapter = ViewPagerAdapter(childFragmentManager, tabLayout.tabCount, lifecycle)
        viewPager2.adapter = adapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager2.currentItem = tab!!.position
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect

            }
        })
    }

    companion object{
        val TAG = "FeedFragment"
    }
}
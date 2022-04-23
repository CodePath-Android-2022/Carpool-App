package com.example.carpool.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.carpool.fragments.ExploreFragment
import com.example.carpool.fragments.PendingFragment
import com.example.carpool.fragments.RidesFragment

class ViewPagerAdapter(supportFragmentManager: FragmentManager, val totalTabs: Int, lifecycle: Lifecycle) : FragmentStateAdapter(supportFragmentManager, lifecycle){

    override fun getItemCount(): Int {
        return totalTabs
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                ExploreFragment()
            }
            1 -> {
                RidesFragment()
            }
            2 -> {
                PendingFragment()
            }
            else -> {
                Fragment()
            }
        }
    }

}
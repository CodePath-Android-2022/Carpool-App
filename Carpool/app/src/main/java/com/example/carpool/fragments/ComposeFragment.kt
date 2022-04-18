package com.example.carpool.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
}

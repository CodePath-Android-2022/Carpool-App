package com.example.carpool.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.carpool.MainActivity
import com.example.carpool.R

class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchButton = view.findViewById<Button>(R.id.btnSearch)
        var fromLocation: String
        var toLocation: String
        var priceMin: String
        var priceMax: String

        searchButton.setOnClickListener{

            fromLocation = view.findViewById<EditText>(R.id.et_searchFrom).text.toString()
            toLocation = view.findViewById<EditText>(R.id.et_SearchTo).text.toString()
            priceMin = view.findViewById<EditText>(R.id.et_searchPriceMin).text.toString()
            priceMax = view.findViewById<EditText>(R.id.et_searchPriceMax).text.toString()
            Log.i(TAG,"${fromLocation}, ${toLocation}")
            Toast.makeText(requireContext(),"Search initiated",Toast.LENGTH_SHORT).show()

            goToMainActivity(fromLocation, toLocation, priceMin, priceMax)
        }
    }

    private fun goToMainActivity(fromLocation: String, toLocation: String, priceMin: String, priceMax: String) {
        val intent = Intent(activity, MainActivity::class.java)
        intent.putExtra("fromLocation",fromLocation)
        intent.putExtra("toLocation",toLocation)
        intent.putExtra("priceMin",priceMin)
        intent.putExtra("priceMax",priceMax)
        startActivity(intent)
    }

    companion object{
        val TAG = "searchFragment"
    }
}
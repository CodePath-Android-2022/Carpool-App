package com.example.carpool.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.carpool.DetailedRideView
import com.example.carpool.R
import com.example.carpool.RideRequest
import com.google.android.material.card.MaterialCardView
import java.text.DateFormat


const val PENDING_POST_EXTRA = "PENDING_POST_EXTRA"

class PendingPostAdapter(val context: Context, val carpoolRides: List<RideRequest>): RecyclerView.Adapter<PendingPostAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Specify Layout file to use for this item
        val view = LayoutInflater.from(context).inflate(R.layout.item_pending_host, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val carpoolRide = carpoolRides.get(position)
        holder.bind(carpoolRide)
    }

    override fun getItemCount(): Int {
        return carpoolRides.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val ibProfilePicture : ImageView = itemView.findViewById(R.id.ibProfilePicture)
        private val tvClientName: TextView = itemView.findViewById(R.id.tvRideCreatorName)
        private val tv_createdAt: TextView = itemView.findViewById(R.id.tv_createdAt)
        private val tv_RequestDetails: TextView = itemView.findViewById(R.id.tvRequestDetails)
        lateinit var cvCard: MaterialCardView

        fun bind(ride: RideRequest) {
            //change the carpool class to contain the user's first and last name
            val fullname = "${ride.getClientName()}"
            tvClientName.text = fullname
            tv_RequestDetails.text = "Wants to join you on your trip to NEED TO REPLACE"
            val format: DateFormat = DateFormat.getDateInstance()
            val date = ride.createdAt
            //tv_createdAt.text = format.format(date)
            tv_createdAt.text = date.toLocaleString()
            // Find the ride creator
            val userParseImage = ride.getClientImage()
            // Populate Image Button using User IMAGE
            Glide.with(itemView.context).load(userParseImage?.url).transform(RoundedCorners(100)).into(ibProfilePicture);
        }

        init {
            cvCard = itemView.findViewById(R.id.cvRideContainer)
            cvCard.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            //1. Get notified of the particular ride which was clicked
            val ride = carpoolRides[adapterPosition]
            Log.i(TAG,"Ride clicked ${ride}")
            
            //2. Use the intent system to navigate to new activity
//            val intent = Intent(context, DetailedRideView::class.java)
//            intent.putExtra(CARPOOL_POST_EXTRA, ride)
//            context.startActivity(intent)
        }
    }

    companion object{
        val TAG = "CarpoolPostAdapter"
    }
}
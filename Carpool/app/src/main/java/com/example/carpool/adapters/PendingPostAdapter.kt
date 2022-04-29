package com.example.carpool.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.carpool.R
import com.example.carpool.RideRequest
import com.google.android.material.card.MaterialCardView
import com.parse.GetCallback
import com.parse.ParseObject
import com.parse.ParseQuery
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
        lateinit var tv_btnAcceptRide: Button
        lateinit var btnDeclineRide: Button
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
            tv_btnAcceptRide = itemView.findViewById(R.id.btnAcceptRide)
            btnDeclineRide = itemView.findViewById(R.id.btnDeclineRide)


            cvCard.setOnClickListener(this)
            tv_btnAcceptRide.setOnClickListener(this)
            btnDeclineRide.setOnClickListener(this)
        }

        override fun onClick(view: View?) {

            //1. Get notified of the particular ride which was clicked
            val ride = carpoolRides[adapterPosition]
            Log.i(TAG,"Ride clicked ${ride}")

            //get Specific ride's post and then update it.
//            val query = ParseQuery.getQuery<ParseObject>("RideRequest")
//            // Retrieve the object by id
//            query.getInBackground(ride.objectId, object : GetCallback<ParseObject?> {
//                fun done(gameScore: ParseObject, e: ParseException?) {
//                    if (e == null) {
//                        // Now let's update it with some new data. In this case, only cheatMode and score
//                        // will get sent to your Parse Server. playerName hasn't changed.
//                        gameScore.put("score", 1338)
//                        gameScore.put("cheatMode", true)
//                        gameScore.saveInBackground()
//                    }
//                }
//            })
            if (view == cvCard) {
                //perhaps navigate to a detailed view with specifics on the ride
            } else if (view == tv_btnAcceptRide) {
                Log.i(TAG, "Accepted Clicked")
                //Go back to post and increase the capacity
            } else if (view == btnDeclineRide) {
                Log.i(TAG, "Declined Clicked")
                //Update the rideRequest for "declined" to true.
                //NOTE: Other user will pull the request and display a message that their request is declined.
            }

            
        }
    }

    companion object{
        val TAG = "CarpoolPostAdapter"
    }
}
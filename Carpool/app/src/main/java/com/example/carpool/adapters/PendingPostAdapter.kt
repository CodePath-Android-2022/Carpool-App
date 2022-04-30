package com.example.carpool.adapters

import android.content.Context
import android.content.Intent
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
import com.example.carpool.MainActivity
import com.example.carpool.R
import com.example.carpool.RideRequest
import com.google.android.material.card.MaterialCardView
import com.parse.*
import java.text.DateFormat
import java.util.*


const val PENDING_POST_EXTRA = "PENDING_POST_EXTRA"

class PendingPostAdapter(val context: Context, val carpoolRequest: List<RideRequest>): RecyclerView.Adapter<PendingPostAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Specify Layout file to use for this item
        val view = LayoutInflater.from(context).inflate(R.layout.item_pending_host, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val carpoolRide = carpoolRequest.get(position)
        holder.bind(carpoolRide)
    }

    override fun getItemCount(): Int {
        return carpoolRequest.size
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
            val client: ParseUser? = ride.getClient()?.fetchIfNeeded()
            val fullname = "${client?.get("firstName")} ${client?.get("lastName")}"
            tvClientName.text = fullname
            val format: DateFormat = DateFormat.getDateInstance()
            val date = ride.createdAt
            //tv_createdAt.text = format.format(date)
            tv_createdAt.text = date.toLocaleString()
            // Find the ride creator
            val userParseImage = ride.getClientImage()
            // Populate Image Button using User IMAGE
            Glide.with(itemView.context).load(userParseImage?.url).transform(RoundedCorners(100)).into(ibProfilePicture);

            //disabling the buttons and updating the message
            if (ride.getDeclined()!!) {
                tv_btnAcceptRide.setEnabled(false)
                btnDeclineRide.setEnabled(false)
                tv_RequestDetails.text = "Carpool Request Declined"
            } else if (ride.getAccepted()!!){
                tv_btnAcceptRide.setEnabled(false)
                btnDeclineRide.setEnabled(false)
                tv_RequestDetails.text = "Carpool Request Accepted"
            } else if (ride.getHostID() == ParseUser.getCurrentUser().objectId){
                tv_RequestDetails.text = "Wants to join you on your trip to NEED TO REPLACE"
            } else {
                tv_RequestDetails.text = "waiting for ${ride.getHostName()} to accept your request"
                tv_btnAcceptRide.visibility = View.GONE
                btnDeclineRide.visibility = View.GONE
            }
        }

        init {
            cvCard = itemView.findViewById(R.id.cvRideContainer)
            tv_btnAcceptRide = itemView.findViewById(R.id.btnAcceptRideDetailed)
            btnDeclineRide = itemView.findViewById(R.id.btnDeclineRide)


            cvCard.setOnClickListener(this)
            tv_btnAcceptRide.setOnClickListener(this)
            btnDeclineRide.setOnClickListener(this)
        }

        override fun onClick(view: View?) {

            //1. Get notified of the particular ride which was clicked
            val rideReq = carpoolRequest[adapterPosition]

            if (view == cvCard) {
                //perhaps navigate to a detailed view with specifics on the ride
            } else if (view == tv_btnAcceptRide) {
                rideReq.setPending(false)
                val userID = rideReq.get("clientID")
                val query = ParseQuery.getQuery<ParseObject>("CarpoolPost")
                // Retrieve the object by id
                query.getInBackground(rideReq.getCarpoolID()!!.objectId, object : GetCallback<ParseObject?> {
                    override fun done(carpoolRide: ParseObject?, e: ParseException?) {
                        if (e == null) {
                            // Now let's update it with some new data. In this case, only cheatMode and score
                            // will get sent to your Parse Server. playerName hasn't changed.
                            Log.i(TAG,carpoolRide.toString())
                            val members = carpoolRide!!.get("members") as ArrayList<String>

                            //TODO: If ride is at MAXCAPACITY then set availability of CARPOOL POST to FALSE
                            if (members.size ==  carpoolRide.get("carCapacity")) {
                                carpoolRide.put("availability", false)
                                return
                            } else {
                                rideReq.setAccepted(true)
                                if (!members.contains(userID)) {
                                    carpoolRide.addUnique("members", userID)
                                    carpoolRide.increment("currCapacity", 1)
                                } else {
                                    Log.i(TAG, "User is already part of the ride")
                                    return
                                }
                            }

                            carpoolRide.saveInBackground{ exception ->
                                if (exception == null) {
                                    Log.i(TAG, "Ride updated in server!")
                                }
                                else {
                                    Log.e(TAG, "Something went wrong! Couldn't update post. Error message: ${exception.message}")
                                }
                            }
                        }
                    }
                })

                Log.i(TAG, "Accepted Clicked")
            } else if (view == btnDeclineRide) {
                Log.i(TAG, "Declined Clicked")
                // TODO: change status (RideRequest) for DECLINED to TRUE & PENDING to FALSE
                rideReq.setDeclined(true)
                rideReq.setPending(false)

                val clientID = rideReq.getClientID()
                rideReq.getCarpoolID()?.removeAll("members", Arrays.asList(clientID))
            }
            rideReq.saveInBackground{ exception ->
                if (exception == null) {
                    Log.i(TAG, "Ride Request updated in server!")
                }
                else {
                    Log.e(TAG, "Something went wrong! Couldn't update Ride Request. Error message: ${exception.message}")
                }
            }
            refreshPage()
        }
        fun refreshPage(){
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }

    }

    companion object{
        val TAG = "PendingPostAdapter"
    }
}
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
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.carpool.CarpoolRide
import com.example.carpool.DetailedRideView
import com.example.carpool.R
import com.example.carpool.RideRequest
import com.google.android.material.card.MaterialCardView
import com.parse.ParseUser
import java.text.DateFormat


const val CARPOOL_POST_EXTRA = "CARPOOL_POST_EXTRA"

class CarpoolPostAdapter(val context: Context, val carpoolRides: List<CarpoolRide>): RecyclerView.Adapter<CarpoolPostAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Specify Layout file to use for this item
        val view = LayoutInflater.from(context).inflate(R.layout.item_ride, parent, false)
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
        private val tvHostName: TextView = itemView.findViewById(R.id.tvRideCreatorName)
        private val tvRideSource: TextView = itemView.findViewById(R.id.tvRideSource)
        private val tvRideDestination: TextView = itemView.findViewById(R.id.tvRideDestination)
        private val tvRideAmount: TextView = itemView.findViewById(R.id.tvRideAmount)
        private val tvMaxCapacity: TextView = itemView.findViewById(R.id.tvMaxCapacity)
        private val tvCurrentCapacity: TextView = itemView.findViewById(R.id.tvCurrentCapacity)
        private val tv_createdAt: TextView = itemView.findViewById(R.id.tv_createdAt)
        lateinit var  joinBtn: Button
        lateinit var cvCard: MaterialCardView

        fun bind(ride: CarpoolRide) {
            //change the carpool class to contain the user's first and last name
            val fullname = "${ride.getFirstName()} ${ride.getLastName()}"
            tvHostName.text = fullname
            tvRideSource.text = ride.getSourceLocation()
            tvRideDestination.text = ride.getDestinationLocation()
            tvRideAmount.text = ride.getPrice().toString()
            tvMaxCapacity.text = ride.getCarCapacity().toString()
            val capacity = ride.getcurrCapacity().toString() + "/"
            tvCurrentCapacity.text = capacity
            val format: DateFormat = DateFormat.getDateInstance()
            val date = ride.createdAt
            //tv_createdAt.text = format.format(date)
            tv_createdAt.text = date.toLocaleString()
            // Find the ride creator
            val user = ride.getUser()

            val ride = carpoolRides[adapterPosition]
            val rideUserID = ParseUser.getCurrentUser().objectId
            val members = ride.get("members") as ArrayList<String>
            if (members.contains(rideUserID)) {
                joinBtn.setEnabled(false)
            } else {
                Log.i(TAG, "User is already part of the ride")
            }

            // Populate Image Button using User Info
            val userParseImage = user?.getParseFile("profileImg")
            Glide.with(itemView.context).load(userParseImage?.url).transform(RoundedCorners(100)).into(ibProfilePicture);
        }

        init {
            cvCard = itemView.findViewById(R.id.cvRideContainer)
            joinBtn = itemView.findViewById(R.id.btnAcceptRide)
            joinBtn.setOnClickListener(this)
            cvCard.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            if (view == joinBtn) {
                Log.i(TAG, "Join Button Click, should be disabled")
                //TODO: GREY OUT the join button for rides created by the user. If they are part of the Array then reject join (return)
                val ride = carpoolRides[adapterPosition]
                val rideUserID = ParseUser.getCurrentUser().objectId.toString()
                val members = ride.get("members") as ArrayList<String>
                if (!members.contains(rideUserID)) {
                    ride.addUnique("members", rideUserID)
                } else {
                    Log.i(TAG, "User is already part of the ride")
                    return
                }

                ride.saveInBackground{ exception ->
                    if (exception == null) {
                        Log.i(TAG, "Ride updated in server!")
                    }
                    else {
                        Log.e(TAG, "Something went wrong! Couldn't update post. Error message: ${exception.message}")
                    }
                }

                if(ride.getUser() != null) {
                    Log.i(TAG, ride.getUser()!!.objectId)
                    // create a Ride Request post object to be sent to the server
                    val requestRide = RideRequest()

                    // set all fields to save
                    val client = ParseUser.getCurrentUser()
                    requestRide.put("client", client)
                    requestRide.put("host", ride.getUser()!!)
                    requestRide.put("hostID", ride.getUser()!!.objectId)
                    requestRide.put("carpoolID", ride)
                    requestRide.put("clientname", client.username)
                    if (client.get("profileImg") != null) {
                        requestRide.put("clientImage", client.get("profileImg")!!)
                    }
                    requestRide.saveInBackground{ exception ->
                        if (exception == null) {
                            Log.i(TAG, "Successfully saved post in server!")
                        }
                        else {
                            Log.e(TAG, "Something went wrong! Couldn't save post. Error message: ${exception.message}")
                        }
                    }
                }


                //TODO: Create a new request and send it to parse backend.
            } else if (view == cvCard) {
                //1. Get notified of the particular ride which was clicked
                val ride = carpoolRides[adapterPosition]
                Log.i(TAG,"Ride clicked ${ride}")

                //2. Use the intent system to navigate to new activity
                val intent = Intent(context, DetailedRideView::class.java)
                intent.putExtra(CARPOOL_POST_EXTRA, ride)
                context.startActivity(intent)
            }

        }
    }

    companion object{
        val TAG = "CarpoolPostAdapter"
    }
}
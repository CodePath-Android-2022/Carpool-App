package com.example.carpool

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
import com.google.android.material.card.MaterialCardView


const val CARPOOL_POST_EXTRA = "CARPOOL_POST_EXTRA"

class CarpoolPostAdapter(val context: Context, val carpoolRides: List<CarpoolPost>): RecyclerView.Adapter<CarpoolPostAdapter.ViewHolder>() {
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
        lateinit var cvCard: MaterialCardView

        fun bind(ride: CarpoolPost) {
            //change the carpool class to contain the user's first and last name
            val fullname = "${ride.getFirstName()} ${ride.getLastName()}"
            tvHostName.text = fullname
            tvRideSource.text = ride.getSourceLocation()
            tvRideDestination.text = ride.getDestinationLocation()
            tvRideAmount.text = ride.getPrice().toString()
            tvMaxCapacity.text = ride.getCarCapacity().toString()
            val capacity = ride.getcurrCapacity().toString() + "/"
            tvCurrentCapacity.text = capacity
            // Find the ride creator
            val user = ride.getUser()

            // Populate Image Button using User Info
            val userParseImage = user?.getParseFile("profileImg")
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
            val intent = Intent(context, DetailedRideView::class.java)
            intent.putExtra(CARPOOL_POST_EXTRA, ride)
            context.startActivity(intent)
        }
    }

    companion object{
        val TAG = "CarpoolPostAdapter"
    }
}
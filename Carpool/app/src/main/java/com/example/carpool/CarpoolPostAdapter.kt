package com.example.carpool

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ibProfilePicture : ImageButton = itemView.findViewById(R.id.ibProfilePicture)
        private val tvUserName: TextView = itemView.findViewById(R.id.tvRideCreatorName)
        private val tvRideSource: TextView = itemView.findViewById(R.id.tvRideSource)
        private val tvRideDestination: TextView = itemView.findViewById(R.id.tvRideDestination)
        private val tvRideAmount: TextView = itemView.findViewById(R.id.tvRideAmount)
        private val tvRideCapacity: TextView = itemView.findViewById(R.id.tvRideCapacity)

        fun bind(ride: CarpoolPost) {
            tvUserName.text = ride.getUser()?.username
            tvRideSource.text = ride.getSourceLocation()
            tvRideDestination.text = ride.getDestinationLocation()
            tvRideAmount.text = ride.getPrice().toString()
            tvRideCapacity.text = ride.getCarCapacity().toString()

            // Find the ride creator
            val user = ride.getUser()

            // Populate Image Button using User Info
            val userParseImage = user?.getParseFile("profileImg")
            Glide.with(itemView.context).load(userParseImage?.url).into(ibProfilePicture);
        }

    }
}
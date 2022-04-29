package com.example.carpool

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser

@ParseClassName("RideRequest")
class RideRequest : ParseObject () {

    companion object {
        const val KEY_CLIENT: String = "client"
        const val KEY_HOST: String = "host"
        const val KEY_PENDING: String = "pending"
        const val KEY_ACCEPTED: String = "accepted"
        const val KEY_CARPOOL_ID: String = "carpoolID"
        const val KEY_CLIENT_NAME: String = "clientname"
        const val KEY_CLIENT_IMAGE: String = "clientImage"
    }

    /**
     *  create the getters and setter for the following:
     *  -   user: Pointer<User>
     *  -   sourceLocation: String
     *  -   destinationLocation: String
     *  -   departureDate: String
     *  -   departureTime: String
     *  -   carCapacity: Number
     *  -   description: String
     *  -   price: Number
     */

    /*  Setters  */

    fun setClient(client: ParseUser){
        put(KEY_CLIENT, client)
    }

    fun setHost(host: ParseUser) {
        put(KEY_HOST, host)
    }

    fun setPending(pending: Boolean) {
        put(KEY_PENDING, pending)
    }

    fun setAccepted(accepted: Boolean) {
        put(KEY_ACCEPTED, accepted)
    }

    fun setCarpoolID(carpoolID: CarpoolPost) {
        put(KEY_CARPOOL_ID, carpoolID)
    }

    fun setClientName(clientname: String) {
        put(KEY_CLIENT_NAME, clientname)
    }

    fun setClientImage(image: ParseFile) {
        put(KEY_CLIENT_IMAGE, image)
    }




    /*  Getters  */

    fun getClient() : ParseUser? {
        return getParseUser(KEY_CLIENT)
    }

    fun getHost() : ParseUser? {
        return getParseUser(KEY_HOST)
    }

    fun getPending() : Boolean? {
        return getBoolean(KEY_PENDING)
    }

    fun getAccepted() : Boolean? {
        return getBoolean(KEY_ACCEPTED)
    }

    fun getCarpoolID() : ParseObject? {
        return getParseObject(KEY_CARPOOL_ID)
    }

    fun getClientName() : String? {
        return getString(KEY_CLIENT_NAME)
    }

    fun getClientImage() : ParseFile? {
        return getParseFile(KEY_CLIENT_IMAGE)
    }


}
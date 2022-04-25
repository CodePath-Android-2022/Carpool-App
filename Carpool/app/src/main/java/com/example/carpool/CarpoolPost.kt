package com.example.carpool

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser

@ParseClassName("CarpoolPost")
class CarpoolPost : ParseObject () {

    companion object {
        const val KEY_USER: String = "user"
        const val KEY_SOURCE_LOCATION: String = "sourceLocation"
        const val KEY_DESTINATION_LOCATION: String = "destinationLocation"
        const val KEY_DEPARTURE_DATE: String = "departureDate"
        const val KEY_DEPARTURE_TIME: String = "departureTime"
        const val KEY_CAR_CAPACITY: String = "carCapacity"
        const val KEY_DESCRIPTION: String = "description"
        const val KEY_PRICE: String = "price"
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

    fun setUser(user: ParseUser){
        put(KEY_USER, user)
    }

    fun setSourceLocation(sourceLocation: String) {
        put(KEY_SOURCE_LOCATION, sourceLocation)
    }

    fun setDestinationLocation(destinationLocation: String) {
        put(KEY_DESTINATION_LOCATION, destinationLocation)
    }

    fun setDepartureDate(departureDate: String) {
        put(KEY_DEPARTURE_DATE, departureDate)
    }

    fun setDepartureTime(departureTime: String) {
        put(KEY_DEPARTURE_TIME, departureTime)
    }

    fun setCarCapacity(carCapacity: Int) {
        put(KEY_CAR_CAPACITY, carCapacity)
    }

    fun setDescription(description: String) {
        put(KEY_DESCRIPTION, description)
    }

    fun setPrice(price: Float) {
        put(KEY_PRICE, price)
    }



    /*  Getters  */

    fun getUser() : ParseUser? {
        return getParseUser(KEY_USER)
    }

    fun getSourceLocation() : String? {
        return getString(KEY_SOURCE_LOCATION)
    }

    fun getDestinationLocation() : String? {
        return getString(KEY_DESTINATION_LOCATION)
    }

    fun getDepartureDate() : String? {
        return getString(KEY_DEPARTURE_DATE)
    }

    fun getDepartureTime() : String? {
        return getString(KEY_DEPARTURE_TIME)
    }

    fun getCarCapacity() : Int {
        return getInt(KEY_CAR_CAPACITY)
    }

    fun getDescription() : String? {
        return getString(KEY_DESCRIPTION)
    }

    fun getPrice() : Int {
        return getInt(KEY_PRICE)
    }

}
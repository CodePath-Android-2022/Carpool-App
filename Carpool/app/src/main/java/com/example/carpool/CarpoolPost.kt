package com.example.carpool

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser

@ParseClassName("CarpoolPost")
class CarpoolPost : ParseObject () {

    companion object {
        private const val KEY_START_LOCATION: String = "startLocation"
        private const val KEY_DESTINATION: String = "destination"
        private const val KEY_DESCRIPTION: String = "description"
        private const val KEY_DEPARTURE_DATE: String = "departureDate"
        private const val KEY_CAR_CAPACITY: String = "carCapacity"
        private const val KEY_PRICE: String = "price"
        private const val KEY_USER: String = "user"
    }

    /**
     *  create the getters and setter for the following:
     *      - startLocation
     *      - destination
     *      - departureDate
     *      - description
     *      - seats
     *      - price
     *      - user
     */

    /*  Setters  */

    fun setStartLocation(startLocation: String) {
        put(KEY_START_LOCATION, startLocation)
    }

    fun setDestination(destination: String) {
        put(KEY_DESTINATION, destination)
    }

    fun setDescription(description: String) {
        put(KEY_DESCRIPTION, description)
    }

    fun setDepartureDate(departureDate: String) {
        put(KEY_DEPARTURE_DATE, departureDate)
    }

    fun setCarCapacity(seats: Int) {
        put(KEY_CAR_CAPACITY, seats)
    }

    fun setPrice(price: String) {
        put(KEY_PRICE, price)
    }

    fun setUser(user: User){
        put(KEY_USER, user)
    }



    /*  Getters  */

    fun getStartLocation() : String? {
        return getString(KEY_START_LOCATION)
    }

    fun getDestination() : String? {
        return getString(KEY_DESTINATION)
    }

    fun getDescription() : String? {
        return getString(KEY_DESCRIPTION)
    }

    fun getDepartureDate() : String? {
        return getString(KEY_DEPARTURE_DATE)
    }

    fun getCarCapacity() : Int {
        return getInt(KEY_CAR_CAPACITY)
    }

    fun getPrice() : String? {
        return getString(KEY_PRICE)
    }

    fun getUser() : ParseUser? {
        return getParseUser(KEY_USER)
    }

}

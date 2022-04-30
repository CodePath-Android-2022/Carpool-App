package com.example.carpool

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser

@ParseClassName("CarpoolPost")
class CarpoolRide : ParseObject () {

    companion object {
        const val KEY_USER: String = "user"
        const val KEY_SOURCE_LOCATION: String = "sourceLocation"
        const val KEY_DESTINATION_LOCATION: String = "destinationLocation"
        const val KEY_DEPARTURE_DATE: String = "departureDate"
        const val KEY_DEPARTURE_TIME: String = "departureTime"
        const val KEY_CAR_CAPACITY: String = "carCapacity"
        const val KEY_DESCRIPTION: String = "description"
        const val KEY_PRICE: String = "price"
        const val KEY_FIRSTNAME: String = "firstName"
        const val KEY_LASTNAME: String = "lastName"
        const val KEY_AVAILABILITY: String = "availability"
        const val KEY_CURRENT_CAPACITY: String = "currCapacity"
        const val KEY_MEMBERS: String = "members"
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
    fun setFirstName(firstname: String){
        return put(KEY_FIRSTNAME, firstname)
    }

    fun setLastName(lastname: String){
        return put(KEY_LASTNAME, lastname)
    }
    fun setAvailability(availability: Boolean) {
        return put(KEY_AVAILABILITY, availability)
    }

    fun setcurrCapacity(currentCapacity: Number) {
        return put(KEY_CURRENT_CAPACITY, currentCapacity)
    }

    fun setMembers(member: List<String>) {
        return put(KEY_MEMBERS,member)
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

    fun getFirstName() : String? {
        return getString(KEY_FIRSTNAME)
    }

    fun getLastName() : String? {
        return getString(KEY_LASTNAME)
    }

    fun getAvailability() : Boolean {
        return getBoolean(KEY_AVAILABILITY)
    }

    fun getcurrCapacity() : Number? {
        return getNumber(KEY_CURRENT_CAPACITY)
    }

    fun getMembers() : List<String>? {
        return getList<String>(KEY_MEMBERS)
    }

}
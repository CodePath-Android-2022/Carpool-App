package com.example.carpool

import com.parse.ParseClassName
import com.parse.ParseObject

@ParseClassName("User")
class User : ParseObject() {

    //branch to push to ***- gaurav -**
    //create the getters and setter for the following

    //first name
    fun getUserFirstName(): String? {
        return getString(USER_FIRST_NAME)
    }

    fun setUserFirstName(firstName: String) {
        put(USER_FIRST_NAME, firstName)
    }

    // last name
    fun getUserLastName(): String? {
        return getString(USER_LAST_NAME)
    }

    fun setUserLastName(lastName: String) {
        put(USER_LAST_NAME, lastName)
    }

    // location
    fun getUserLocation(): String? {
        return getString(USER_LOCATION)
    }

    fun setUserLocation(userLocation: String) {
        put(USER_LOCATION, userLocation)
    }

    // optional
    // User notifications
    fun getUserNotification(): Boolean {
        return getBoolean(USER_NOTIFICATION)
    }

    fun setUserNotification(userNotifications: Boolean) {
        put(USER_NOTIFICATION, userNotifications)
    }

    companion object {
        const val USER_FIRST_NAME = "firstName"
        const val USER_LAST_NAME = "lastName"
        const val USER_LOCATION = "location"

        // optional
        const val USER_NOTIFICATION = "userNotifications"
    }
}
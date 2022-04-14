package com.example.carpool

import android.app.Application
import com.parse.Parse
import com.parse.ParseObject

class CarpoolApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Registering parse models
        ParseObject.registerSubclass(CarpoolPost::class.java)
        ParseObject.registerSubclass(User::class.java)

        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());
    }
}
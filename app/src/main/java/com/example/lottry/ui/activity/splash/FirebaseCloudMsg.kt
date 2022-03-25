package com.example.lottry.ui.activity.splash

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.lottry.utils.Constant
import android.widget.Toast
import com.google.firebase.messaging.Constants
import com.google.firebase.messaging.FirebaseMessagingService

class FirebaseCloudMsg : FirebaseMessagingService() {



    override fun onNewToken(token: String) {
        Log.d("Token11111", "Refreshed token: $token")

        Constant.ApiConstant.DEVICE_TOKEN = token
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }




    private fun sendRegistrationToServer(token: String) {

      //  Toast.makeText(this, token, Toast.LENGTH_LONG).show()
    }
}


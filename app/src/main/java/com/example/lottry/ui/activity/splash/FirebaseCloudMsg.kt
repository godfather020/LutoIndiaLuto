package com.example.lottry.ui.activity.splash

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.example.lottry.R
import com.example.lottry.utils.Constant
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FirebaseCloudMsg : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        var title = message.notification?.title
        var text = message.notification?.body
        var CHANNEL_ID = "HEADS_UP_NOTIFICATION"

        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                CHANNEL_ID,
                "Heads up notification",
                NotificationManager.IMPORTANCE_HIGH
            )
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val notification: Notification.Builder = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.logo)
            .setAutoCancel(true)
        NotificationManagerCompat.from(this).notify(1, notification.build())
        super.onMessageReceived(message)
    }

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


package com.example.lottry.ui.activity.login

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.EditText
import com.example.lottry.ui.activity.login.OTPReceiver
import android.provider.Telephony
import android.util.Log
import com.example.lottry.development.ui.OtpTextView

class OTPReceiver : BroadcastReceiver() {

    fun setEditTextOtp(otpTextView: OtpTextView){
         editTextOtp = otpTextView
    }

    override fun onReceive(context: Context, intent: Intent) {
        val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        for (smsMessage in smsMessages) {
            val smsBody = smsMessage.messageBody
            Log.d("msgBody", smsBody)
            val getOtp = smsBody.split(":").toTypedArray()[1]
            editTextOtp?.otp = getOtp
            Log.d("otp", getOtp)
        }
    }

    companion object {
        private var editTextOtp: OtpTextView? = null
    }
}
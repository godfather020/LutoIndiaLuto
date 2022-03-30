package com.example.lottry.utils

import com.example.lottry.R
import com.google.android.gms.wallet.WalletConstants
import org.json.JSONArray
import org.json.JSONObject

class Constant {


    object url{

        internal const val API_DEVELOPMENT_URL = "developementURL"
        internal const val API_TESTING_URL = "testingURL"
        internal const val API_LIVE_URL = "liveURL"

    }

    object IntentDataKeys {

        const val VERIFICATIONID = "verification_id"
        const val RESENDTOKENOTP ="resend_token"
        const val PHONEWITHCOUNTRY ="phone_country"
        const val VERIFYNO ="verify_no"
        const val WITHDRAW ="Withdraw"
        const val KEY ="key"
    }

    object Intent {



        internal const val Splash = "LOTTREY_SPLASH"
        internal const val Get_Started = "LOTTREY_GET_STARTED"
        internal const val Login = "LOTTREY_LOGIN"
        internal const val Registration = "LOTTREY_REGISTRATION"
        internal const val Main = "LOTTREY_MAIN"

    }

    object BundelConstant{

        internal const val PHONE_NO="phone_no"
    }

    object ApiConstant{

        internal const val LOGIN="public/login"
        internal const val VERIFY="public/verify"
        internal const val REGISTER="public/register"
        internal const val LOTTERIES="v1/lotteries"
        internal const val TICKETS="v1/tickets"
        internal const val BUY_TICKETS="v1/buyTickets"
        internal const val MY_TICKETS="v1/myTickets"
        internal const val TOP_WINNERS="v1/topWinners"
        internal const val WALLET_BALANCE="v1/myWallet"
        internal const val PAYTM_TOKEN="v1/paytmChecksum"
        internal const val TRANS_HISTORY="v1/myTransactions"
        internal const val NOTIFICATION="v1/notifications"
        internal const val ADD_WALLET_BALANCE="v1/addWallet"
        internal const val DEDUCT_WALLET_BALANCE="v1/withdraw"
        internal const val SET_PROFILE_IMAGE="v1/uploadProfilePic"
        internal var DEVICE_TOKEN = ""

    }

    object sharedPrefrencesConstant{

        internal const val X_TOKEN="x-token"
        internal const val WALLET_BALANCE="wallet-balance"
        internal const val REFFERAL_AMOUNT="referral-balance"
        internal const val LOGIN="login"
        internal const val FIRST_TIME="first_time"

    }

    object paytm{

        internal const val  PAYTM_MID="diXiTG59984630060376"
        internal const val  PAYTM_MKEY="91FyYoTTnzOUF0Ga"
    }

    object googlePay{
        internal const val GATEWAY ="allpayments"
        internal const val GOOGLEPAY_MID ="BCR2DN6TY6U5RELQ"

        val GOOGLEPAY_M_INFO = JSONObject().put("merchantName", "Lottry")

        val PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS = mapOf(
            "gateway" to GATEWAY,
            "gatewayMerchantId" to GOOGLEPAY_MID)

        val CARD_NETWORK_ARRAY= JSONArray(listOf(
            "AMEX",
            "DISCOVER",
            "INTERAC",
            "JCB",
            "MASTERCARD",
            "VISA"))

        val CARD_AUTH_METHOD = JSONArray(listOf(
            "PAN_ONLY",
            "CRYPTOGRAM_3DS"))

        const val PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST

        const val COUNTRY_CODE ="US"
        const val CURRENCY_CODE="USD"

    }


}
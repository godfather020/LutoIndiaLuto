package com.example.lottry.utils.googlepay

import android.app.Activity
import com.example.lottry.utils.Constant
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode

object PaymentUtil {

    val MICROS = BigDecimal(1000000.0)

    private val merchantInfo: JSONObject
        @Throws(JSONException::class)
        get() = JSONObject().put("merchantName", "Example Merchant")

    private val baseRequest = JSONObject().apply {
        put("apiVersion", 2)
        put("apiVersionMinor", 0)
    }

    private fun gatewayTokenizationSpecification(): JSONObject {
        return JSONObject().apply {
            put("type", "PAYMENT_GATEWAY")
            put("parameters", JSONObject(Constant.googlePay.PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS))
        }
    }


    private fun baseCardPaymentMethod(): JSONObject {
        return JSONObject().apply {

            val parameters = JSONObject().apply {
                put("allowedAuthMethods", Constant.googlePay.CARD_AUTH_METHOD)
                put("allowedCardNetworks", Constant.googlePay.CARD_NETWORK_ARRAY)
                put("billingAddressRequired", true)
                put("billingAddressParameters", JSONObject().apply {
                    put("format", "FULL")
                })
            }

            put("type", "CARD")
            put("parameters", parameters)
        }
    }

    private fun cardPaymentMethod(): JSONObject {
        val cardPaymentMethod = baseCardPaymentMethod()
        cardPaymentMethod.put("tokenizationSpecification", gatewayTokenizationSpecification())

        return cardPaymentMethod
    }


    fun createPaymentsClient(activity: Activity): PaymentsClient {
        val walletOptions = Wallet.WalletOptions.Builder()
            .setEnvironment(Constant.googlePay.PAYMENTS_ENVIRONMENT)
            .build()

        return Wallet.getPaymentsClient(activity, walletOptions)
    }

   fun isReadyToPayRequest(): JSONObject? {
        return try {
            baseRequest.apply {
                put("allowedPaymentMethods", JSONArray().put(baseCardPaymentMethod()))
            }

        } catch (e: JSONException) {
            null
        }
    }

    private fun getTransactionInfo(price: String): JSONObject {
        return JSONObject().apply {
            put("totalPrice", price)
            put("totalPriceStatus", "FINAL")
            put("countryCode", Constant.googlePay.COUNTRY_CODE)
            put("currencyCode", Constant.googlePay.CURRENCY_CODE)
        }
    }

    fun getPaymentDataRequest(price: String): JSONObject? {
        try {
            return baseRequest.apply {
                put("allowedPaymentMethods", JSONArray().put(cardPaymentMethod()))
                put("transactionInfo", getTransactionInfo(price))
                put("merchantInfo", merchantInfo)

                // An optional shipping address requirement is a top-level property of the
                // PaymentDataRequest JSON object.
//                val shippingAddressParameters = JSONObject().apply {
//                    put("phoneNumberRequired", false)
//                    put("allowedCountryCodes", JSONArray(listOf("US", "GB")))
//                }
//                put("shippingAddressParameters", shippingAddressParameters)
//                put("shippingAddressRequired", true)
            }
        } catch (e: JSONException) {
            return null
        }
    }
    fun Long.microsToString() = BigDecimal(this)
        .divide(PaymentUtil.MICROS)
        .setScale(2, RoundingMode.HALF_EVEN)
        .toString()
}
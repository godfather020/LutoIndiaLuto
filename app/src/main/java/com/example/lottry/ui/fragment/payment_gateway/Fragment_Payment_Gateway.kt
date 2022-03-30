package com.example.lottry.ui.fragment.payment_gateway

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.lottry.R
import com.example.lottry.baseclass.fragment.Base_Fragment
import com.example.lottry.data.remote.retrofit.api.Apis
import com.example.lottry.databinding.FragmentPaymentGatewaytBinding
import com.example.lottry.ui.activity.main.MainActivity
import com.example.lottry.utils.Constant
import com.example.lottry.utils.googlepay.PaymentUtil
import com.example.lottry.utils.shared_prefrence.SharedPreferencesUtil
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class Fragment_Payment_Gateway : Base_Fragment(), View.OnClickListener {


    private lateinit var paymentsClient: PaymentsClient
    private var googlePayButton = false
    private val LOAD_GOOGLEPAY_PAYMENT_DATA_REQUEST_CODE = 991
    private val LOAD_PAYTM_PAYMENT_DATA_REQUEST_CODE = 990
    lateinit var binding: FragmentPaymentGatewaytBinding
    lateinit var viewModel: Fragment_Payment_Gateway_viewModel
    lateinit var orderId: String
    lateinit var amt: String
    lateinit var number: String
    lateinit var bundle: Bundle
    var withdraw = false
    var TAG = "Paytm"
    lateinit var sharedPreferences: SharedPreferencesUtil;

    @Inject
    lateinit var apis: Apis

    var activit = MainActivity()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activit = (activity as MainActivity)
        sharedPreferences= SharedPreferencesUtil(activit)

        component.inject(this@Fragment_Payment_Gateway)
        setInstance(requireActivity())

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_payment_gatewayt, container, false)
        viewModel =
            ViewModelProvider(this@Fragment_Payment_Gateway).get(Fragment_Payment_Gateway_viewModel::class.java)
        val view: View = binding.root

        init()
        return view

    }

    private fun init() {

        val date = getCurrentDateTime()
        val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")

        orderId = requireActivity().getString(R.string.app_name) + " " + dateInString
        orderId = orderId.replace(" ", "_")
        orderId = orderId.replace("/", "")
        orderId = orderId.replace(":", "_")

        paymentsClient = PaymentUtil.createPaymentsClient(requireActivity())

        possiblyShowGooglePayButton()

        bundle= requireArguments()

        if(bundle!=null){

            withdraw=bundle.getBoolean(Constant.IntentDataKeys.WITHDRAW)
        }

        if (withdraw){
            binding.tvTitle.text=" You can withdrawal Coins anytime. You need to send a request to us, we will manually transfer the coins into your requested mode. Minimum coins is 100, you can not withdraw less than this.";

        }else{
            binding.tvTitle.text="You can add Coins into wallet through options available and enjoy ticket purchasing experience and have a chance to win jackpot. Minimum coins is 100, you can not add less than this.";
        }
        binding.paymentGatewayBtnPaytm.setOnClickListener(this)
        binding.paymentGatewayBtnGooglepay.setOnClickListener(this)
        binding.paymentGatewayBtnPhonePe.setOnClickListener(this)

    }

    fun showResponseDialog(type: String){

        var dialog = Dialog(activit);
        dialog.setCancelable(false);

        var view = activit.layoutInflater.inflate(R.layout.toast_dialog, null)

        dialog.setContentView(view);
        if (dialog.getWindow() != null) {
            dialog.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        var btnOk = view.findViewById<Button>(R.id.dialog_response_ok)
        var response_txt = view.findViewById<TextView>(R.id.response_txt)

        response_txt.setText(type)

        btnOk.setOnClickListener(View.OnClickListener { view ->

            dialog.dismiss()
        })
        dialog.show();
    }

    fun showDialog(type: String) {

        var dialog = Dialog(activit);
        dialog.setCancelable(false);

        var view = activit.layoutInflater.inflate(R.layout.dialog_amt, null)

        dialog.setContentView(view);
        if (dialog.getWindow() != null) {
            dialog.getWindow()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        var btnSubmit = view.findViewById<Button>(R.id.dialog_amt_btn_submit)
        var btnCancel = view.findViewById<Button>(R.id.dialog_amt_btn_cancel)
        var edtAmt = view.findViewById<TextInputEditText>(R.id.dialog_amt_edt_amt)
        var edit_number = view.findViewById<TextInputLayout>(R.id.edit_number)
        var edtNumber = view.findViewById<TextInputEditText>(R.id.dialog_number)
        if (withdraw){
            edit_number.visibility=View.VISIBLE
            edtNumber.setText(sharedPreferences.getUserData()!!.phoneNumber.toString())
        }
        btnCancel.setOnClickListener(View.OnClickListener { view ->

            dialog.dismiss()

        })

        btnSubmit.setOnClickListener(View.OnClickListener { view ->

            amt=edtAmt.text.toString()
            number=edtNumber.text.toString()

            if(!amt.isNullOrEmpty()&&!amt.isNullOrBlank()){
                when(type){

                    activit.resources.getString(R.string.paytm)->{
                        if (amt.toInt()<100){
                            if (withdraw){
                            showResponseDialog("Minimum 100 Withdrawal.")
                            //activit.showToast("Minimum 100 Withdrawal.")
                            }else{
                                showResponseDialog("Please add Minimum 100 rs.")
                                //activit.showToast("Please add Minimum 100 rs.")
                            }
                            return@OnClickListener
                        }
                        if(withdraw){
                            deductWalletBalance(amt,number,"PayTM")
                        }else
                        requestPaymentPaytm(amt)
                        dialog.dismiss()

                    }
                    activit.resources.getString(R.string.google_pay)->{
                        if (amt.toInt()<100){
                            if (withdraw){
                                showResponseDialog("Minimum 100 Withdrawal.")
                               // activit.showToast("Minimum 100 Withdrawal.")
                            }else{
                                showResponseDialog("Please add Minimum 100 rs.")
                                //activit.showToast("Please add Minimum 100 rs.")
                            }
                            return@OnClickListener
                        }
                        if(withdraw){
                            deductWalletBalance(amt,number,"GooglePe")
                        }else{
                            requestPaymentGooglePay(amt)
                        }
                        dialog.dismiss()
                    }
                    activit.resources.getString(R.string.phone_pe)->{
                        if (amt.toInt()<100){
                            if (withdraw){
                                showResponseDialog("Minimum 100 Withdrawal.")
                               // activit.showToast("Minimum 100 Withdrawal.")
                            }else{
                                showResponseDialog("Please add Minimum 100 rs.")
                                //activit.showToast("Please add Minimum 100 rs.")
                            }
                            return@OnClickListener
                        }
                        if(withdraw){
                            deductWalletBalance(amt,number,"PhonePe")
                        }else{
                            requestPaymentPhonePay(amt);
                        }
                       // showToast("Under Process")
                        dialog.dismiss()
                    }
                }

            }else{
                showResponseDialog("Please Enter Amount")
                //activit.showToast("Please Enter Amount")
                edtAmt.isTextInputLayoutFocusedRectEnabled=true
            }

        })


        dialog.show();
    }

    override fun onClick(p0: View?) {

        if (p0 != null) {
            when (p0.id!!) {

                R.id.payment_gateway_btn_paytm -> {

                    showDialog(activit.resources.getString(R.string.paytm))
                }

                R.id.payment_gateway_btn_googlepay -> {

                    showDialog(activit.resources.getString(R.string.google_pay))
                }

                R.id.payment_gateway_btn_phone_pe -> {

                    showDialog(activit.resources.getString(R.string.phone_pe))
                }

            }
            true
        }
    }

    private fun requestPaymentPaytm(amt :String) {

//
       //val callbackUrl = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID="+ orderId
/*     val callbackUrl="https://securegw.paytm.in/theia/paytmCallback?ORDER_ID="+orderId  //productionurl

        viewModel.get_paytmToken(activit, binding, orderId, amt, callbackUrl).observe(activit,
            androidx.lifecycle.Observer {

                Log.d("Token", it.getData()?.response?.body?.txnToken.toString())

               if (it != null) {
                    if (it.getData()?.response?.body?.txnToken.isNullOrEmpty()){
                       showToast("Invalid token please try again")
                        return@Observer
                   }
                   val map = HashMap<String, String>()
                   map.put("order", orderId)
                   map.put("mid", Constant.paytm.PAYTM_MID)
                  it.getData()?.response?.body?.txnToken?.let { it1 -> map.put("txnToken", it1) }
                   map.put("amount", amt)
                    map.put("callbackurl", callbackUrl)

                   Log.d("OrderID", orderId)

                    var paytmOrder = PaytmOrder(
                        orderId,
                       Constant.paytm.PAYTM_MID,
                       it.getData()!!.response!!.body!!.txnToken,
                        amt,
                        callbackUrl
                    )

                    var transactionManager =
                        TransactionManager(paytmOrder, object : PaytmPaymentTransactionCallback {


                            override fun onTransactionResponse(p0: Bundle?) {
                                Log.e(TAG, "Response (onTransactionResponse) : ${p0!!.getString("STATUS")}")
                                if (p0!!.getString("STATUS").equals("TXN_SUCCESS")){
                                   p0!!.getString("TXNAMOUNT")?.let { it1 ->
                                        addWalletBalance(it1)
                                    }
                                }
                                p0!!.getString("TXNAMOUNT")?.let { it1 ->
                                   addWalletBalance(it1)
                               }
                            }

                           override fun networkNotAvailable() {
                                Log.e(TAG, "network not available ")
                            }

                            override fun onErrorProceed(s: String) {
                                Log.e(TAG, " onErrorProcess $s")
                            }

                            override fun clientAuthenticationFailed(s: String) {
                                Log.e(TAG, "Clientauth $s")
                            }

                          override fun someUIErrorOccurred(s: String) {
                                Log.e(TAG, " UI error $s")
                           }

                            override fun onErrorLoadingWebPage(i: Int, s: String, s1: String) {
                                Log.e(TAG, " error loading web $s--$s1")
                            }

                            override fun onBackPressedCancelTransaction() {
                                Log.e(TAG, "backPress ")
                            }

                            override fun onTransactionCancel(s: String, bundle: Bundle?) {
                                Log.e(TAG, " transaction cancel $s")
                           }
                        }) // code statement);

                    transactionManager.setAppInvokeEnabled(false)
                    transactionManager.setShowPaymentUrl("https://securegw-stage.paytm.in/theia/api/v1/showPaymentPage")
                    transactionManager.startTransaction(
                        requireActivity(),
                        LOAD_PAYTM_PAYMENT_DATA_REQUEST_CODE
                    );

                } else {


                }
            })*/
        val timeStamp: String =
            java.lang.String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()))

        val GOOGLE_PAY_PACKAGE_NAME = "net.one97.paytm"
        // qrString":"upi://pay?pa=9039313171@icici&pn=NICT+Bills&tr=EZV2022031015282639175214&am=883.0&cu=INR&mc=amount"
        //val newUri=Uri.parse("upi://pay?pa=8120274562@ybl&pn=Luto india luto&tr=EZV2022031015282639175&am=1.0&cu=INR&mc=amount")
        //val paytmuri1 = "upi://pay?pa=paytmqr2810050501011hiafbxiktzx@paytm&pn=Paytm%20Merchant&mode=02&am=1.0"
        //val paytmuri1 = "upi://pay?pa=Q510225757@ybl&pn=Merchant%20Name&am=1&cu=INR&mode=02&orgid=000000"
        val paytmuri1 = "upi://pay?pa=Q193681286@ybl&pn=Merchant%20Name&am="+amt+"&cu=INR&mode=02&orgid=000000&tn=Add%20Coins&tr="+timeStamp
        val intent = Intent(Intent.ACTION_VIEW,Uri.parse(paytmuri1))
        intent.data = Uri.parse(paytmuri1)
        intent.setPackage(GOOGLE_PAY_PACKAGE_NAME)
        val pm = context!!.packageManager
        try{
            val isInstalled = isPackageInstalled(GOOGLE_PAY_PACKAGE_NAME, pm)
            if (isInstalled){
                startActivityForResult(intent, LOAD_GOOGLEPAY_PAYMENT_DATA_REQUEST_CODE)
            }
            else{
                showToast("App not found in your device")
            }
        }catch (e:Error){
            showToast("App not found in your device")
        }

    }

    private fun requestPaymentGooglePay(amt :String ) {

        val timeStamp: String =
            java.lang.String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()))

       // qrString":"upi://pay?pa=9039313171@icici&pn=NICT+Bills&tr=EZV2022031015282639175214&am=883.0&cu=INR&mc=amount"
        val GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user"
        //val newUri=Uri.parse("upi://pay?pa=8120274562@ybl&pn=Luto india luto&tr=EZV2022031015282639175&am=1.0&cu=INR&mc=amount")
        //val gpayuri2 = "upi://pay?pa=Chouhanniranjan54@okaxis&pn=Merchant%20Name&am=$amt&cu=INR&mode=02&orgid=000000"
        //val gpayuri2 = "upi://pay?pa=Q510225757@ybl&pn=Merchant%20Name&am=1&cu=INR&mode=02&orgid=000000"
        val gpayuri2 = "upi://pay?pa=Q193681286@ybl&pn=Merchant%20Name&am="+amt+"&cu=INR&mode=02&orgid=000000&tn=Add%20Coins&tr="+timeStamp
        val intent = Intent(Intent.ACTION_VIEW,Uri.parse(gpayuri2))
        intent.data = Uri.parse(gpayuri2)
        intent.setPackage(GOOGLE_PAY_PACKAGE_NAME)
        val pm = context!!.packageManager
        try{
            val isInstalled = isPackageInstalled(GOOGLE_PAY_PACKAGE_NAME, pm)
            if (isInstalled){
                startActivityForResult(intent, LOAD_GOOGLEPAY_PAYMENT_DATA_REQUEST_CODE)
            }
            else{
                showToast("App not found in your device")
            }
        }catch (e:Error){
            showToast("App not found in your device")
        }
    }


    private fun requestPaymentPhonePay(amt: String) {

        val timeStamp: String =
            java.lang.String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()))

        val GOOGLE_PAY_PACKAGE_NAME = "com.phonepe.app"
        // qrString":"upi://pay?pa=9039313171@icici&pn=NICT+Bills&tr=EZV2022031015282639175214&am=883.0&cu=INR&mc=amount"
        //val newUri=Uri.parse("upi://pay?pa=8120274562@ybl&pn=Luto india luto&tr=EZV2022031015282639175&am=1.0&cu=INR&mc=amount")
        //val phonepeuri3 = "upi://pay?pa=Q962452870@ybl&pn=Paytm%20Merchant&mode=02&am=1.0"
        //val phonepeuri3 ="upi://pay?pa=Q510225757@ybl&pn=Merchant%20Name&am=1&cu=INR&mode=02&orgid=000000&tn=Add%20Coins&tr="+timeStamp
        val phonepeuri3 = "upi://pay?pa=Q193681286@ybl&pn=Merchant%20Name&am="+amt+"&cu=INR&mode=02&orgid=000000&tn=Add%20Coins&tr="+timeStamp
        Log.d("PhonepeUri", phonepeuri3)

        val intent = Intent(Intent.ACTION_VIEW,Uri.parse(phonepeuri3))
        intent.data = Uri.parse(phonepeuri3)
        intent.setPackage(GOOGLE_PAY_PACKAGE_NAME)
        val pm = context!!.packageManager
        try{
            val isInstalled = isPackageInstalled(GOOGLE_PAY_PACKAGE_NAME, pm)
            if (isInstalled){
                startActivityForResult(intent, LOAD_GOOGLEPAY_PAYMENT_DATA_REQUEST_CODE)
            }
            else{
                showToast("App not found in your device")
            }
        }catch (e:Error){
            showToast("App not found in your device")
        }
    }

    private fun possiblyShowGooglePayButton() {

        val isReadyToPayJson = PaymentUtil.isReadyToPayRequest() ?: return
        val request = IsReadyToPayRequest.fromJson(isReadyToPayJson.toString()) ?: return

        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        val task = paymentsClient.isReadyToPay(request)
        task.addOnCompleteListener { completedTask ->
            try {
                completedTask.getResult(ApiException::class.java)?.let(::setGooglePayAvailable)
            } catch (exception: ApiException) {
                // Process error
                Log.w("isReadyToPay failed", exception)
            }
        }
    }

    fun setGooglePayAvailable(available: Boolean) {

        if (available) {
            binding.paymentGatewayBtnGooglepay.isEnabled = true
        } else {
            binding.paymentGatewayBtnGooglepay.isEnabled = true
            Toast.makeText(
                requireActivity(),
                "Unfortunately, Google Pay is not available on this device",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        Log.d("RESPONSE", requestCode.toString() + resultCode + data.toString())
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {

            LOAD_GOOGLEPAY_PAYMENT_DATA_REQUEST_CODE -> if (RESULT_OK == resultCode || resultCode == 11) {

                if (data != null) {
                    val trxt = data.getStringExtra("response")
                    Log.d("UPI", "onActivityResult: " + trxt);
                    val dataList: ArrayList<String?> = ArrayList()
                    dataList.add(trxt)
                    upiPaymentDataOperation(dataList)
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null");
                    val dataList: ArrayList<String?> = ArrayList()
                    dataList.add("nothing")
                    upiPaymentDataOperation(dataList)
                }
            }
            else {
                Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                val dataList: ArrayList<String?> = ArrayList()
                dataList.add("nothing")
                upiPaymentDataOperation(dataList)
            }
        }
    }

    private fun upiPaymentDataOperation(data: ArrayList<String?>) {
        var str = data[0]
        Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
        var paymentCancel = ""
        if (str == null) str = "discard"
        var status = ""
        var approvalRefNo = ""
        val response = str.split("&".toRegex()).toTypedArray()
        for (i in response.indices) {
            val equalStr = response[i].split("=".toRegex()).toTypedArray()
            if (equalStr.size >= 2) {
                if (equalStr[0].toLowerCase() == "Status".toLowerCase()) {
                    status = equalStr[1].toLowerCase()
                } else if (equalStr[0].toLowerCase() == "ApprovalRefNo".toLowerCase() || equalStr[0].toLowerCase() == "txnRef".toLowerCase()) {
                    approvalRefNo = equalStr[1]
                }
            } else {
                paymentCancel = "Payment cancelled by user."
            }
        }
        if (status == "success") {
            //Code to handle successful transaction here.



            //Toast.makeText(activit, "Transaction successful.", Toast.LENGTH_SHORT).show()
            addWalletBalance(amt);

             Log.d("UPI", "responseStr: "+approvalRefNo);
        } else if ("Payment cancelled by user." == paymentCancel) {
            showResponseDialog("Payment cancelled by user.")
            //Toast.makeText(activit, "Payment cancelled by user.", Toast.LENGTH_SHORT).show()
        } else {
            showResponseDialog("Transaction failed.Please try again")
            //Toast.makeText(activit, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show()
        }
    }


    /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)
         when (requestCode) {
             // Value passed in AutoResolveHelper
             LOAD_GOOGLEPAY_PAYMENT_DATA_REQUEST_CODE -> {
                 when (resultCode) {
                     AppCompatActivity.RESULT_OK ->
                         data?.let { intent ->
                             PaymentData.getFromIntent(intent)?.let(::handlePaymentSuccess)
                         }

                     AppCompatActivity.RESULT_CANCELED -> {
                         // The user cancelled the payment attempt
                     }

                     AutoResolveHelper.RESULT_ERROR -> {
                         AutoResolveHelper.getStatusFromIntent(data)?.let {
                             handleError(it.statusCode)
                         }
                     }
                 }

                 // Re-enables the Google Pay payment button.
 //                googlePayButton.isClickable = true
             }
         }
     }

     private fun handlePaymentSuccess(paymentData: PaymentData) {
         val paymentInformation = paymentData.toJson() ?: return

         try {
             // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
             val paymentMethodData = JSONObject(paymentInformation).getJSONObject("paymentMethodData")

             // If the gateway is set to "example", no payment information is returned - instead, the
             // token will only consist of "examplePaymentMethodToken".
             if (paymentMethodData
                     .getJSONObject("tokenizationData")
                     .getString("type") == "PAYMENT_GATEWAY" && paymentMethodData
                     .getJSONObject("tokenizationData")
                     .getString("token") == "examplePaymentMethodToken") {

                 AlertDialog.Builder(requireActivity())
                     .setTitle("Warning")
                     .setMessage("Gateway name set to \"example\" - please modify " +
                             "Constants.java and replace it with your own gateway.")
                     .setPositiveButton("OK", null)
                     .create()
                     .show()
             }

             val billingName = paymentMethodData.getJSONObject("info")
                 .getJSONObject("billingAddress").getString("name")
             Log.d("BillingName", billingName)

             Toast.makeText(requireActivity(), requireActivity().resources.getString(R.string.payments_show_name, billingName), Toast.LENGTH_LONG).show()

             // Logging token string.
             Log.d("GooglePaymentToken", paymentMethodData
                 .getJSONObject("tokenizationData")
                 .getString("token"))

         } catch (e: JSONException) {
             Log.e("handlePaymentSuccess", "Error: " + e.toString())
         }

     }


     private fun handleError(statusCode: Int) {
         Log.w("loadPaymentData failed", String.format("Error code: %d", statusCode))
     }*/

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    fun addWalletBalance(amt: String){

        viewModel.add_walletBalance(activit,binding,amt)
    }
    fun deductWalletBalance(amt: String,number:String,type:String){

        var  totalwalletBalance=sharedPreferences.getInteger(Constant.sharedPrefrencesConstant.WALLET_BALANCE)!!.toInt()
        if (totalwalletBalance<amt.toInt()){

            showResponseDialog(resources.getString(R.string.insufficient_fund_with))
            //Toast.makeText(context,resources.getString(R.string.insufficient_fund_with), Toast.LENGTH_LONG).show()
            return
        }
        viewModel.deduct_walletBalance(activit,binding,amt,number,type)
    }

    private fun isPackageInstalled(packageName: String, packageManager: PackageManager): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

}
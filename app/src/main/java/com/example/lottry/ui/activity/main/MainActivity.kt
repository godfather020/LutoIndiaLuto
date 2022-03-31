package com.example.lottry.ui.activity.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.example.lottry.R
import com.example.lottry.databinding.ActivityMainBinding
import com.example.lottry.ui.fragment.buy_ticket.Fragment_Buy_Ticket
import com.example.lottry.ui.fragment.home.Fragment_Home
import com.example.lottry.ui.fragment.my_ticket.Fragment_My_Ticket
import com.example.lottry.ui.fragment.notification.Fragment_Notification
import com.google.android.material.navigation.NavigationView
import com.softs.meetupfellow.components.activity.CustomAppActivity
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.PopupMenu
import com.example.lottry.dragger.AppComponent
import com.softs.meetupfellow.components.activity.CustomAppActivityCompatViewImpl
import com.softs.meetupfellow.components.activity.CustomAppActivityImpl
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity

import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.lottry.ui.activity.help.Fragment_help
import com.example.lottry.ui.fragment.payment_gateway.Fragment_Payment_Gateway
import com.example.lottry.ui.fragment.tran_history.Tran_History_Fragment
import com.example.lottry.utils.Constant

import com.example.lottry.utils.shared_prefrence.SharedPreferencesUtil
import com.google.android.gms.wallet.*

import org.json.JSONObject
import org.json.JSONException


class MainActivity : CustomAppActivityCompatViewImpl() {

    lateinit var binding: ActivityMainBinding
    lateinit var toolbar: Toolbar
    lateinit var toolbarTitle: TextView
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var mypopupWindow: PopupWindow
    lateinit var resultLauncherCamera: ActivityResultLauncher<Intent>
    lateinit var resultLauncherGallery: ActivityResultLauncher<Intent>
    lateinit var popup: PopupMenu
    lateinit var totalwalletBalance: TextView
    lateinit var referralAmount: TextView
    lateinit var sharedPreferences: SharedPreferencesUtil;
    private val LOAD_GOOGLEPAY_PAYMENT_DATA_REQUEST_CODE = 991
    private val LOAD_PAYTM_PAYMENT_DATA_REQUEST_CODE = 990

    lateinit var UserName:TextView
    lateinit var PhoneNo:TextView
    lateinit var UserImg:CircleImageView

    lateinit var viewModel: MainViewModel
    private val CAMERA_REQUEST_CODE=90
    private val GALLERY_REQUEST_CODE=99




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        viewModel=ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)

        component.inject(this@MainActivity)
        setInstance(this@MainActivity)


        binding.nav.setItemIconTintList(null);
        toolbar = binding.toolbarLayout.findViewById(R.id.toolbar)
        toolbarTitle = binding.toolbarLayout.findViewById(R.id.txt_toolbar_title)

        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayShowTitleEnabled(false)
//        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
        totalwalletBalance= TextView(this)
        /*referralAmount= TextView(this)*/
        setupToolbar()
        setPopUpWindow(this)

        init()

    }

    


    fun setupToolbar() {



        toggle = object : ActionBarDrawerToggle(
            this@MainActivity,
            binding.drawerLayout,
            toolbar,
            R.string.close_Drawer,
            R.string.open_Drawer
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                mypopupWindow.dismiss()
                binding.SlidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)

            }
        }


        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState()


//        toolbar.navigationIcon=resources.getDrawable(R.drawable.help,null)


    }


    fun init(){

        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.READ_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS),
                101
            )
        }


        sharedPreferences=SharedPreferencesUtil(this)
        binding.dashboardTxtUserName.text="Hi, "+sharedPreferences.getUserData()!!.userName
        binding.referralCode.text="Referral Code: "+sharedPreferences.getUserData()!!.refferalcode
        Log.d("referral_code", sharedPreferences.getUserData()!!.refferalcode)
        binding.SlidingUpPanel.isTouchEnabled = false
        binding.SlidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
        loadFragment(
            supportFragmentManager,
            Fragment_Notification(),
            R.id.notificaion_container,
            true,
            "Notification",
            null
        )



        loadFragment(
            supportFragmentManager,
            Fragment_Home(),
            R.id.main_activity_main_container,
            true,
            "HOME",
            null
        )

        UserImg=binding.nav.getHeaderView(0).findViewById<CircleImageView>(R.id.nav_header_img_user_name)
        UserName=binding.nav.getHeaderView(0).findViewById<TextView>(R.id.nav_header_txt_user_name)
        PhoneNo=binding.nav.getHeaderView(0).findViewById<TextView>(R.id.nav_header_txt_sub_heading)

        UserName.text=sharedPreferences.getUserData()!!.userName
        PhoneNo.text=sharedPreferences.getUserData()!!.phoneNumber
        if(sharedPreferences.getUserData()!!.profilePic!=null) {

            setImage(sharedPreferences.getUserData()!!.phoneNumber,UserImg,resources.getDrawable(R.drawable.ic_launcher_background))
        }

        UserImg.setOnClickListener(View.OnClickListener {

            popup = PopupMenu(this@MainActivity, it);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.popup_menu_change_profile, popup.getMenu());

            //registering popup with OnMenuItemClickListener

            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->

                when (item.itemId) {

                    R.id.popup_menu_change_profile_camera -> {

                        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)




                        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.O){

                            startActivityForResult(takePicture,CAMERA_REQUEST_CODE)
                        }else{

                            resultLauncherCamera.launch(takePicture)
                        }



                    }

                    R.id.popup_menu_change_profile_gallery -> {


                        val pickPhoto = Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

                        resultLauncherGallery.launch(pickPhoto)
                    }
                }
                true
            })
            /*popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener() {
                  public boolean onMenuItemClick(MenuItem item) {
                      Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                      return true;
                  }
              });*/

            popup.show();//showing popup menu
            //closing the setOnClickListener method
        })

        binding.nav.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.nav_my_ticket -> {

                    closeDrawer()
                    loadFragment(
                        supportFragmentManager,
                        Fragment_My_Ticket(),
                        R.id.main_activity_main_container,
                        false,
                        resources.getString(R.string.my_ticket),
                        null
                    )

                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_notification -> {

                    closeDrawer()
                    binding.SlidingUpPanel.panelState = SlidingUpPanelLayout.PanelState.EXPANDED

                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_transaction_history -> {

                    closeDrawer()
                    loadFragment(
                        supportFragmentManager,
                        Tran_History_Fragment(),
                        R.id.main_activity_main_container,
                        false,
                        resources.getString(R.string.transaction_history),
                        null
                    )

                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_term_and_condition -> {
//                    var bundle= Bundle()
//                    bundle.putInt(Constant.IntentDataKeys.KEY,1)
                    closeDrawer()
                    val intent = Intent(this, Fragment_help::class.java).apply {
                        putExtra(Constant.IntentDataKeys.KEY, 3)
                    }
                    startActivity(intent)
//                    loadFragment(
//                        supportFragmentManager,
//                        Fragment_help(),
//                        R.id.main_activity_main_container,
//                        false,
//                        resources.getString(R.string.transaction_history),
//                        bundle
//                    )

                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_help -> {
//                    var bundle= Bundle()
//                    bundle.putInt(Constant.IntentDataKeys.KEY,2)
                    closeDrawer()
                    val intent = Intent(this, Fragment_help::class.java).apply {
                        putExtra(Constant.IntentDataKeys.KEY, 1)
                    }
                    startActivity(intent)

//                    loadFragment(
//                        supportFragmentManager,
//                        Fragment_help(),
//                        R.id.main_activity_main_container,
//                        false,
//                        resources.getString(R.string.transaction_history),
//                        bundle
//                    )

                    return@OnNavigationItemSelectedListener true
                }


                R.id.nav_private_policy -> {
//                    var bundle= Bundle()
//                    bundle.putInt(Constant.IntentDataKeys.KEY,3)
                    closeDrawer()
                    val intent = Intent(this, Fragment_help::class.java).apply {
                        putExtra(Constant.IntentDataKeys.KEY, 2)
                    }
                    startActivity(intent)
//                    loadFragment(
//                        supportFragmentManager,
//                        Fragment_help(),
//                        R.id.main_activity_main_container,
//                        false,
//                        resources.getString(R.string.transaction_history),
//                        bundle
//                    )

                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_logout -> {

                    closeDrawer()
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setCancelable(false)
                    builder.setTitle(resources.getString(R.string.are_you_want_to_logout))

                    builder.setPositiveButton(resources.getString(R.string.yes))
                    { dialogInterface, i ->

                        sign_out()
                    }
                    builder.setNegativeButton(resources.getString(R.string.no))
                    { dialogInterface, i ->

                        dialogInterface.dismiss()
                    }

                    val alertDialog = builder.create()
                    alertDialog.show()

                    return@OnNavigationItemSelectedListener true
                }

                else -> {
                    return@OnNavigationItemSelectedListener false
                }
            }
        })
        supportFragmentManager.addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener {

            if (supportFragmentManager.backStackEntryCount > 0) {

                lockDrawer()
                toggle.isDrawerIndicatorEnabled = false
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                toolbar.navigationIcon!!.setTint(resources.getColor(R.color.white))
                toolbar.setNavigationOnClickListener(View.OnClickListener {

                    supportFragmentManager.popBackStack()
                })

            } else {
                unlockDrawer()
                toggle.isDrawerIndicatorEnabled = true
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                toggle.syncState()
                toolbar.setNavigationOnClickListener(View.OnClickListener {

                    openDrawer()
                })

            }

        })

        resultLauncherCamera = registerForActivityResult(
            StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
//                popup.dismiss()
//
//                var bitmap: Bitmap? = null
//                var uri: Uri? = null
//                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
//
//
//                    setImage(
//                        result.data!!.data.toString(),
//                        binding.nav.get(0).findViewById(R.id.nav_header_img_user_name),
//                        resources.getDrawable(R.drawable.ic_launcher_background)
//                    )
//                    try {
//                        bitmap = MediaStore.Images.Media.getBitmap(
//                            this.getContentResolver(), result.data!!
//                                .data
//                        )
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }
//                } else {
//                    val bundle = result.data!!.extras
//                    bitmap = bundle!!.getParcelable("data")
//                    val file: File = createImageFile()!!
//                    if (file != null) {
//                        val fout: FileOutputStream
//                        try {
//                            fout = FileOutputStream(file)
//                            bitmap!!.compress(Bitmap.CompressFormat.PNG, 70, fout)
//                            fout.flush()
//                        } catch (e: Exception) {
//                            e.printStackTrace()
//                        }
//                        uri = Uri.fromFile(file)
//                    }
//                    setImage(
//                        uri.toString(),
//                        binding.nav.get(0).findViewById(R.id.nav_header_img_user_name),
//                        resources.getDrawable(R.drawable.ic_launcher_background)
//                    )
//
//                    setProfileImage(uri.toString())
//                    bitmap = result.data!!.extras!!["data"] as Bitmap?
//                    //                bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), Uri.parse(result.getData().getExtras().get("data").toString()));
//                }
//                /* imgBase64 = convert(bitmap)
//                 Log.d("bitmap", imgBase64)*/

                setImageByCamera(result.data!!)
            }


        }
        resultLauncherGallery = registerForActivityResult(
            StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                popup.dismiss()

                var bitmap: Bitmap? = null
                val uri: Uri? = null
                setImage(
                    result.data!!.data.toString(),
                    binding.nav.get(0).findViewById(R.id.nav_header_img_user_name),
                    resources.getDrawable(R.drawable.ic_launcher_background)
                )

                setProfileImage(result.data!!.data.toString())
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(
                        this.getContentResolver(), result.data!!
                           .data
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                /* imgBase64 = convert(bitmap)
                 Log.d("bitmap", imgBase64)*/

                setImageByGallery(result.data!!)
            }
        }

        totalwalletBalance.text=sharedPreferences.getInteger(Constant.sharedPrefrencesConstant.WALLET_BALANCE).toString()+" Coins | "+sharedPreferences.getInteger(Constant.sharedPrefrencesConstant.REFFERAL_AMOUNT).toString()+" Coins"
        /*referralAmount.text=sharedPreferences.getInteger(Constant.sharedPrefrencesConstant.REFFERAL_AMOUNT).toString()+" Coins"*/
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){

            R.id.option_notification ->{

                if(binding.SlidingUpPanel.panelState==SlidingUpPanelLayout.PanelState.EXPANDED){

                    binding.SlidingUpPanel.panelState=SlidingUpPanelLayout.PanelState.HIDDEN
                }else{

                    loadFragment(
                        supportFragmentManager,
                        Fragment_Notification(),
                        R.id.notificaion_container,
                        false,
                        "Notification",
                        null
                    )
                    supportFragmentManager.popBackStackImmediate()
                    binding.SlidingUpPanel.panelState=SlidingUpPanelLayout.PanelState.EXPANDED
                }
                return true
            }
            R.id.option_wallet ->{

               if( mypopupWindow.isShowing) {

                   mypopupWindow.dismiss()

               }else{
                   totalwalletBalance.text=sharedPreferences.getInteger(Constant.sharedPrefrencesConstant.WALLET_BALANCE).toString()+" Coins | "+sharedPreferences.getInteger(Constant.sharedPrefrencesConstant.REFFERAL_AMOUNT).toString()+" Coins"
                   /*referralAmount.text=sharedPreferences.getInteger(Constant.sharedPrefrencesConstant.REFFERAL_AMOUNT).toString()+" Coins"*/
                   mypopupWindow.showAsDropDown(toolbar,-10,-45,Gravity.END)
                   mypopupWindow.isOutsideTouchable=true
               }
//                genrateCheckSum()
//                requestPayment()

                return true
            }
            else-> {

                return false
            }
        }
    }



    fun  closeDrawer() {
        binding.drawerLayout.closeDrawer(Gravity.LEFT, true)
    }

    fun openDrawer() {
        binding.drawerLayout.openDrawer(Gravity.LEFT, true)
    }
    fun lockDrawer() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun unlockDrawer() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }
    fun setTitleName(name:String){

        toolbarTitle.setText(name)
    }
    fun setPopUpWindow(activity: Activity) {

        lateinit var inflater: LayoutInflater
        lateinit var view: View


        inflater= activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        view = inflater.inflate(R.layout.wallet_popup_menu, null);

//        Start = (RelativeLayout) view . findViewById (R.id.start_btn);
//        Pause = (RelativeLayout) view . findViewById (R.id.pause_btn);
//        Stop = (RelativeLayout) view . findViewById (R.id.stop_btn);

        val height:Int= activity.resources.getDimension(R.dimen._125sdp).toInt()

        val width:Int= activity.resources.getDimension(R.dimen._167sdp).toInt()

//        Toast.makeText(this,"width = "+width+" hieght= "+height,Toast.LENGTH_LONG).show()


        mypopupWindow = PopupWindow(view, width, height, false)
//        mypopupWindow = PopupWindow(view)
        val walletBalance=view.findViewById<TextView>(R.id.wallet_popup_txt_price_amt)
        /*val refferalBalance=view.findViewById<TextView>(R.id.referral_amount)*/
        val btn_addMoney=view.findViewById<TextView>(R.id.wallet_popup_txt_add_money)
        val btn_cashOut=view.findViewById<TextView>(R.id.wallet_popup_txt_cash_out)


        totalwalletBalance=walletBalance
       /* referralAmount=refferalBalance*/



        btn_addMoney.setOnClickListener(View.OnClickListener {
            var bundle= Bundle()
            bundle.putBoolean(Constant.IntentDataKeys.WITHDRAW,false)
            loadFragment(
                supportFragmentManager,
                Fragment_Payment_Gateway(),
                R.id.main_activity_main_container,
                false,
                "Payment Gateway",
                bundle
            )
            mypopupWindow.dismiss()
        })

        btn_cashOut.setOnClickListener(View.OnClickListener {


            var bundle= Bundle()
            bundle.putBoolean(Constant.IntentDataKeys.WITHDRAW,true)
            loadFragment(
                supportFragmentManager,
                Fragment_Payment_Gateway(),
                R.id.main_activity_main_container,
                false,
                "Payment Gateway",
                bundle
            )
          // Toast.makeText(this,"Clicked",Toast.LENGTH_LONG).show()
            mypopupWindow.dismiss()
        })

//        mypopupWindow.contentView.setOnClickListener(View.OnClickListener {
//
//            mypopupWindow.dismiss()
//        })
    }


    override fun onBackPressed() {

        if(binding.drawerLayout.isDrawerOpen(Gravity.LEFT)){

            closeDrawer()
            return
        }
        if(mypopupWindow.isShowing){

            mypopupWindow.dismiss()
            return
        }
        super.onBackPressed()
    }

    public fun addBalanceFragment(){
        setTitleName("")
        show_user_detail()
        hide_ticket_amt()
        var bundle= Bundle()
        bundle.putBoolean(Constant.IntentDataKeys.WITHDRAW,false)
        loadFragment(
            supportFragmentManager,
            Fragment_Payment_Gateway(),
            R.id.main_activity_main_container,
            false,
            "Payment Gateway",
            bundle
        )
    }

    public fun hide_user_detail(){

        binding.dashboardClUserDetail.visibility=View.GONE

    }
    public fun show_user_detail(){

        binding.dashboardClUserDetail.visibility=View.VISIBLE
    }
    public fun hide_ticket_amt(){

        binding.walletBalance.visibility= View.GONE
        binding.balanceAmount.visibility= View.GONE
        binding.dashboardTxtBuyTicketAmt.visibility=View.GONE

    }
    public fun hideHeader(flag:Boolean){

        if(flag){
            binding.dashboardClUserDetail.visibility=View.GONE
            binding.guideline.visibility=View.GONE
            binding.drawerLayout.background=null
        }else{
            binding.dashboardClUserDetail.visibility=View.VISIBLE
            binding.guideline.visibility=View.VISIBLE
            binding.drawerLayout.background=resources.getDrawable(R.drawable.main_bg)

        }

    }
    public fun backToHomeScreen(){

        supportFragmentManager.popBackStack();

    }
    public fun show_ticket_amt(amt:Int){
        binding.walletBalance.visibility= View.VISIBLE
        binding.balanceAmount.visibility= View.VISIBLE
        binding.dashboardTxtBuyTicketAmt.visibility=View.VISIBLE
        binding.dashboardTxtBuyTicketAmt.text=resources.getString(R.string.total_price_)+" "+amt
        binding.balanceAmount.text = sharedPreferences.getInteger(Constant.sharedPrefrencesConstant.WALLET_BALANCE).toString()+" Coins | "+sharedPreferences.getInteger(Constant.sharedPrefrencesConstant.REFFERAL_AMOUNT).toString()+" Coins"
    }

    fun createImageFile(): File? {

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        var mFileTemp: File? = null
        val root: String = this.getDir("my_sub_dir", MODE_PRIVATE).getAbsolutePath()
        val myDir = File("$root/Img")
        if (!myDir.exists()) {
            myDir.mkdirs()
        }
        try {
            mFileTemp = File.createTempFile(imageFileName, ".jpg", myDir.absoluteFile)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return mFileTemp
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
        when (requestCode) {

            // Value passed in AutoResolveHelper
            CAMERA_REQUEST_CODE->{

                setImageByCamera(data!!)
            }

            GALLERY_REQUEST_CODE->{

                setImageByGallery(data!!)
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

                AlertDialog.Builder(this)
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

            Toast.makeText(this, resources.getString(R.string.payments_show_name, billingName), Toast.LENGTH_LONG).show()

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
    }

   fun sign_out(){

       sharedPreferences.clearData()
       sharedPreferences.saveBoolean(Constant.sharedPrefrencesConstant.FIRST_TIME,false)
       switchActivityOnly(Constant.Intent.Login, true)
   }

    fun setProfileImage(imgUrl:String){

        viewModel.set_profileImage(this,binding,imgUrl).observe(this, androidx.lifecycle.Observer {

            Log.d("it", it.toString())

            if(it!=null){

               if( it.getSuccess()==true){

                showToast(resources.getString(R.string.upload_successfully))
               }
            }

        })


    }

    fun setImageByCamera(result :Intent){



            popup.dismiss()

            var bitmap: Bitmap? = null
            var uri: Uri? = null
        val file: File = createImageFile()!!
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {


                if (file != null) {
                    val fout: FileOutputStream
                    try {
                        fout = FileOutputStream(file)
                        bitmap!!.compress(Bitmap.CompressFormat.PNG, 70, fout)
                        fout.flush()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    uri = Uri.fromFile(file)
                }
                setImage(
                    result.data!!.toString(),
                    binding.nav.get(0).findViewById(R.id.nav_header_img_user_name),
                    resources.getDrawable(R.drawable.ic_launcher_background)
                )
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(
                        this.getContentResolver(), result.data!!
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                setProfileImage(uri.toString())
            } else {
                val bundle = result.extras
                bitmap = bundle!!.getParcelable("data")

                if (file != null) {
                    val fout: FileOutputStream
                    try {
                        fout = FileOutputStream(file)
                        bitmap!!.compress(Bitmap.CompressFormat.PNG, 70, fout)
                        fout.flush()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    uri = Uri.fromFile(file)
                }
                setImage(
                    uri.toString(),
                    binding.nav.get(0).findViewById(R.id.nav_header_img_user_name),
                    resources.getDrawable(R.drawable.ic_launcher_background)
                )
                Log.d("imgUri", uri.toString())
                setProfileImage(uri.toString())
                bitmap = result.extras!!["data"] as Bitmap?
                //                bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), Uri.parse(result.getData().getExtras().get("data").toString()));
            }
            /* imgBase64 = convert(bitmap)
             Log.d("bitmap", imgBase64)*/


    }

    fun setImageByGallery(result: Intent){

       popup.dismiss()

                var bitmap: Bitmap? = null
                val uri: Uri? = null
                setImage(
                    result.data!!.toString(),
                    binding.nav.get(0).findViewById(R.id.nav_header_img_user_name),
                    resources.getDrawable(R.drawable.ic_launcher_background)
                )

                setProfileImage(result.data!!.toString())
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(
                        this.getContentResolver(), result.data!!

                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                /* imgBase64 = convert(bitmap)
                 Log.d("bitmap", imgBase64)*/
            }




//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        for (fragment in supportFragmentManager.fragments) {
//            fragment.onActivityResult(requestCode, resultCode, data)
//        }
//    }


//    {"apiVersionMinor":0,"apiVersion":2,
//        "paymentMethodData":{"description":"Mastercard •••• 2523",
//        "tokenizationData":{"type":"PAYMENT_GATEWAY",
//            "token":"{\"signature\":\"MEUCIAfgSzhWE78xsmScCgcWbTeKBtJCWP8ckZhZ537DRC0uAiEAlb8gygdStmVrcANxWDnA7Yt2a3QKDfbgGsd4qOUZcOM\\u003d\",
//            \"intermediateSigningKey\":{\"signedKey\":\" +
//            ""{\\\"keyValue\\\":\\\"MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE1KfWModwkU88wA0GD83CieK4sz8RZam5EMePgAJ8UzjpycScYaK\/
//                6TaT8k7I2xpsNayYQXpsabdZXeqgT98ZHA\\\\u003d\\\\u003d\\\",\\\"keyExpiration\\\":\\\"1640222215809\\\"}\",
//                \"signatures\":[\"MEYCIQDvMNPhfMRHYHIgX+n9phpBun5zGeeeJW4vYSowI3kRMgIhANcN+97Km4yle6BIKrkFFshmxGwDYhAbet3QKRGHTkQd\"]}"
//                + ",\"protocolVersion\":\"ECv2\",\"signedMessage\":\"{\\\"encryptedMessage\\\":\\\" +
//                ""x1McYwYyaoR7sLIoBp2iUNKPQ06PAFYkcqi4sZLgXTfY0avtOUw2eZQDSRh2MDqGHBcaDCI3Bu08ZltkzkZzz9PWhtahzv1Xo515TlzUCOA4Xzf\/
//                        Fc1V2n9SOHyytt9U56sfjAS1kLeRpS1ntLQ+hAByvPcug7\/tvaKDj6zUaq9d1WjbGTblR\/vlpVKxNiUXPJvtfSvfgB2Jimkt\/15wJ2wOsTF1\/8eGO4ootKj7fZDWY9nKTooiT8p\/gJmp5zjm1\/Gzq2hru1GcG3OgQs1w8gIwyRa33i539RPHiE7E7PTnkbKzFMlhr7Qwf4Fg0qLLvgkyunJAZN8NvMYd3jKBpfwZmqB62il0DsxHdh0jC+llGLdGUp9ZPS\/xs2pDGUpxlHMcF04QEHHP0sis\/qf8g8oUslc4IQYGYa3Pvar9tGC4tinM58tKasa5VYklprocjBhtdOdBY0vGIYw+JnjgU7ReTWh3\/jZWdsRjPNq+WlDsBL4\\\\u003d\\\",
//                \\\"ephemeralPublicKey\\\":\\\"BIQ8Sqqk5EApbi932TALQUP66+LacbqjgRKZjfkGHHzOzNMXv1MoIVVno\/6FkBGdJniHNDF2wSvMw1hlnKEt1uE\\\\u003d\\\",\
//                \\"tag\\\":\\\"hnP9TDp1EQcafqFAVsQONeOcZFmPuD2wQbZMDsceaJk\\\\u003d\\\"}\"}"},"type":"CARD",
//        "info":{"cardNetwork":"MASTERCARD","cardDetails":"2523",
//            "billingAddress":{"address3":"","sortingCode":"","address2":"11","countryCode":"IN","address1":"Indore","postalCode":"452001","name":"Sawan Jaiswal","locality":"Indore","administrativeArea":"Madhya Pradesh"}}}}
}



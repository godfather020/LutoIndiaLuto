package com.softs.meetupfellow.components.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.lottry.application.WikiApplication
import com.example.lottry.dragger.AppComponent

//import com.g00fy2.versioncompare.Version
//import com.softs.meetupfellow.log.LogManager
//import com.softs.meetupfellow.R
//import com.softs.meetupfellow.application.ArchitectureApp
//import com.softs.meetupfellow.di.component.ApplicationComponent
//import com.softs.meetupfellow.service.LocationUpdatesService
//import com.softs.meetupfellow.utils.Connected
//import com.softs.meetupfellow.utils.constant.Constants
//import kotlinx.android.synthetic.main.layout_toolbar_with_image.*
//import org.json.JSONObject
//import org.jsoup.Jsoup

/**
 * Created by Maheshwar on 17-07-2017.
 */
@SuppressLint("Registered")
@Suppress("DEPRECATION")
open class CustomAppActivityImpl : CustomAppActivity() {

    protected val component: AppComponent
        get() = WikiApplication.component!!

   /* private val connectionReceiver: ConnectionReceiverListener =
        object : ConnectionReceiverListener {
            override fun onNetworkConnectionChanged(isConnected: Boolean) {
                Connected.connected(isConnected)
            }
        }

    private val notificationReceived = object : OnNotificationReceived {
        override fun onNotification(remoteMessage: RemoteMessage) {
            generateNotification(remoteMessage)
        }
    }*/

   /* private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            mService = null
            mBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as LocationUpdatesService.LocalBinder
            this@CustomAppActivityImpl.mService = binder.service
            mBound = true

        }

    }

    // A reference to the service used to get location updates.
    //private var mService: LocationUpdatesService? = null
    private var mService: LocationUpdatesService? = null

    // Tracks the bound state of the service.
    private var mBound = false

    protected val component: ApplicationComponent
        get() = ArchitectureApp.component!!

    protected val tag: String
        get() = ArchitectureApp().tag

    protected val connected: Boolean
        get() = Connected.isConnected()

    protected fun setupActionBar(title: String, backward: Boolean) {
        setSupportActionBar(toolbar)
        setActionBarTitle(title)
        supportActionBar!!.setDisplayHomeAsUpEnabled(backward)
        supportActionBar!!.setHomeAsUpIndicator(if (title == getString(R.string.title_create_profile)) R.drawable.bg else R.drawable.back_arrow)
    }

    protected fun setupActionBarNavigation(title: String, hideLogo: Boolean, resource: Int) {
        setSupportActionBar(toolbar)
        setActionBarTitle(title)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(resource)
        when (hideLogo) {
            true -> {
              //  tv_title.visibility = View.VISIBLE
               // ivLogo.visibility = View.GONE

            }

            false -> {
          //      tv_title.visibility = View.GONE
          //      ivLogo.visibility = View.VISIBLE

            }
        }

        if(title=="TimeLines"){
          //  ivTimeLineLogo.visibility = View.VISIBLE
          //  tv_title.visibility = View.GONE
        }else{
          //  ivTimeLineLogo.visibility = View.GONE
        }


    }

    protected fun setupActionBarNavigationTimeLine(title: String, hideLogo: Boolean, resource: Int) {
        setSupportActionBar(toolbar)
        setActionBarTitle(title)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(resource)
        when (hideLogo) {
            true -> {
              //  tv_title.visibility = View.VISIBLE
             //   ivLogo.visibility = View.GONE
             //   ivTimeLineLogo.visibility = View.GONE
            }

            false -> {
             //   tv_title.visibility = View.GONE
             //   ivLogo.visibility = View.GONE
             //   ivTimeLineLogo.visibility = View.VISIBLE
            }
        }
    }




    protected fun setupActionBarTransparent(backward: Boolean) {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(backward)
    }

    protected fun setupActionBarSearch(backward: Boolean) {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(backward)
    }

    protected fun setupActionBarChat(backward: Boolean) {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(backward)
    }

    protected fun setupActionBarTransparentNavigation() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setActionBarTitle(title: String) {
        var displayMetrics = DisplayMetrics();
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        var width = displayMetrics.widthPixels
        if(width <= 720){
           // tv_title.textSize = 13F
        }else{
           // tv_title.textSize = 18F
        }
      //  tv_title.text = title
    }

    override fun onResume() {
        super.onResume()
       // Connections.with(connectionReceiver)
       // Notifications.with(notificationReceived)
    }

    *//*override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }*//*

    private fun checkPermission(permission: String) =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.pointerCount > 1) {
            return true
        }
        val view = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action ==
                    MotionEvent.ACTION_MOVE) && view is EditText &&
            !view.javaClass.name.startsWith("android.webkit.")
        ) {
            val scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x = ev.rawX + view.left - scrcoords[0]
            val y = ev.rawY + view.top - scrcoords[1]
            if (x < view.left || x > view.right || y < view.top || y > view.bottom) {
                (this.getSystemService(Context.INPUT_METHOD_SERVICE) as
                        InputMethodManager).hideSoftInputFromWindow(
                    this.window.decorView.applicationWindowToken, 0
                )
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    protected fun locateView(v: View?): Rect? {
        val locInt = IntArray(2)
        if (v == null) return null
        try {
            v.getLocationOnScreen(locInt)
        } catch (e: NullPointerException) {
            //Happens when the view doesn't exist on screen anymore.
            return null
        }

        val location = Rect()
        location.left = locInt[0]
        location.top = locInt[1]
        location.right = location.left + v.width
        location.bottom = location.top + v.height
        return location
    }

    internal fun isLocationEnabled(context: Context): Boolean {
        val locationMode: Int
        val locationProviders: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode =
                    Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE)
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
                return false
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF
        } else {
            locationProviders =
                Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED
                )
            return !TextUtils.isEmpty(locationProviders)
        }
    }

    *//**
     * Checking device has camera hardware or not
     * *//*
    protected fun isDeviceSupportCamera(): Boolean {
        return applicationContext.packageManager.hasSystemFeature(
            PackageManager.FEATURE_CAMERA
        )
    }

   *//* protected fun checkGalleryAppAvailability(): Boolean {
        return EasyImage.canDeviceHandleGallery(this@CustomAppActivityImpl)
    }*//*

    *//**
     * Checking device has front camera hardware or not
     * *//*
    internal fun isDeviceSupportFrontCamera(): Boolean {
        return applicationContext.packageManager.hasSystemFeature(
            PackageManager.FEATURE_CAMERA_FRONT
        )
    }

    protected fun universalToast(message: String) {

        Toast.makeText(this@CustomAppActivityImpl, message, Toast.LENGTH_SHORT).show()
    }


    fun Toast.showCustomToast(message: String, activity: Activity)
    {
        val layout = activity.layoutInflater.inflate (
            R.layout.custom_toast,
            activity.findViewById(R.id.toast_container)
        )

        // set the text of the TextView of the message
        val textView = layout.findViewById<TextView>(R.id.toast_custom)
        textView.text = message

        // use the application extension function
        this.apply {
            setGravity(Gravity.BOTTOM, 0, 40)
            duration = Toast.LENGTH_LONG
            view = layout
            show()
        }
    }


    internal fun initializeApp() {





        if (!isMyServiceRunning(LocationUpdatesService::class.java)) {
            bindService(
                Intent(this, LocationUpdatesService::class.java), serviceConnection,
                Context.BIND_AUTO_CREATE
            )

            Handler(Looper.getMainLooper()).postDelayed({
                // Bind to the service. If the service is in foreground mode, this signals to the service
                // that since this activity is in the foreground, the service can exit foreground mode.
                if (mService != null)
                    mService!!.requestLocationUpdates()
            }, 1000)
        }

    }


    override fun onStop() {
        super.onStop()
        if (mBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            unbindService(serviceConnection)
            mBound = false
        }
    }

   *//* internal fun offlineNotification(remoteMessage: RemoteMessage) {
        Handler().postDelayed({
            performAction(remoteMessage)
        }, 1000)
    }*//*

   *//* private fun generateNotification(remoteMessage: RemoteMessage) {
        if (toolbar == null) {
            return
        }

        when (remoteMessage.data["type"]) {
            Constants.Notification.SessionExpired -> {
                sessionExpired("${remoteMessage.notification?.body}", "Alert")
                return
            }
        }

        val banner = Banner.make(
            toolbar,
            this@CustomAppActivityImpl,
            Banner.TOP,
            R.layout.item_banner_notification,
            false
        )
        val parent =
            banner.bannerView!!.findViewById<CardView>(R.id.cvParent)
        val tvMessage = banner.bannerView!!.findViewById<TextView>(R.id.tvMessage)
        val close = banner.bannerView!!.findViewById<ImageView>(R.id.ivClose)
        when (remoteMessage.data["type"]) {
            Constants.Notification.Chat -> tvMessage.text = remoteMessage.notification?.body
            else -> tvMessage.text = "${remoteMessage.data["alert"]}"
        }

        close.setOnClickListener {
            banner.dismissBanner()
        }
        parent.setOnClickListener {
            banner.dismissBanner()
            performAction(remoteMessage)
        }
        banner.duration = 5000
        if(remoteMessage.data["type"] != "open-profile"){
            banner.show()
        }


    }*//*

    private fun sessionExpired(message: String, title: String) {
        val alertDialog = AlertDialog.Builder(
            this@CustomAppActivityImpl, R.style.MyDialogTheme2
        )
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton(getString(R.string.text_okay)) { dialog, _ ->
            dialog.dismiss()
            logout()
        }
        alertDialog.setCancelable(false)

        alertDialog.show()
    }
*//*

    private fun performAction(remoteMessage: RemoteMessage) {
        when (remoteMessage.data["type"]) {


            Constants.Notification.ProfileLike, Constants.Notification.NewUser -> {
                otherUser(remoteMessage.data["userId"]!!.toInt())
            }
            Constants.Notification.EventApproved, Constants.Notification.EventInterest, Constants.Notification.EventLike,
            Constants.Notification.EventUnblock -> {
                eventDetails(remoteMessage.data["eventId"]!!.toInt())
            }
            Constants.Notification.PrivateAlbumShare -> {
                privateAlbum(remoteMessage.data["userId"]!!)
            }

            Constants.Notification.REEGUR_UPDATE -> {
                chatUser(
                    Gson().fromJson(
                        remoteMessage.data["otherSide"],
                        ConversationModel::class.java
                    ), "${remoteMessage.data["senderImageUrl"]}"
                )
            }


            Constants.Notification.Chat -> {
                chatUser(
                    Gson().fromJson(
                        remoteMessage.data["otherSide"],
                        ConversationModel::class.java
                    ), "${remoteMessage.data["senderImageUrl"]}"
                )
            }

            Constants.Notification.PostStatus, Constants.Notification.PostMention, Constants.Notification.PostNewComment,
            Constants.Notification.PostMentionComment,
            Constants.Notification.PostNewLike, Constants.Notification.PostNewUnlike
                ,Constants.Notification.LikeComment,Constants.Notification.UnlikeComment
            -> {
              timeLineRedirects(remoteMessage.data["postId"],
                  remoteMessage.data["type"]
              )
            }


        }
    }
*//*



    companion object InAppUpdate {
        private var currentVersion = ""
        private var latestVersion = ""
    }

    internal fun getCurrentVersion() {
        val packageManager = this.packageManager
        var packageInfo: PackageInfo? = null

        try {
            packageInfo = packageManager.getPackageInfo(this.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        currentVersion = packageInfo!!.versionName
//        universalToast(currentVersion)

        GetLatestVersion(this@CustomAppActivityImpl).execute()
    }

    class GetLatestVersion(val context: Activity) : AsyncTask<String, String, JSONObject>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): JSONObject {
            try {
//It retrieves the latest version by scraping the content of current version from play store at runtime
                val doc =
                    Jsoup.connect("https://play.google.com/store/apps/details?id=com.soft.meetupfellow")
                        .get()
                latestVersion = doc.getElementsByClass("htlgb")[6].text()

            } catch (e: Exception) {
                e.printStackTrace()

            }
            return JSONObject()
        }

        override fun onPostExecute(result: JSONObject?) {
            if (latestVersion.isNotEmpty() && currentVersion.isNotEmpty()) {
                LogManager.logger.i(
                    ArchitectureApp.instance!!.tag,
                    "Latest version is : $latestVersion, Current version $currentVersion, ${Version(
                        latestVersion
                    ).isHigherThan(Version(currentVersion))}"
                )
                if (Version(latestVersion).isHigherThan(Version(currentVersion))) {
//                    if (currentVersion < latestVersion) {
                    if (!isCancelled) { //This would help to prevent Error : BinderProxy@45d459c0 is not valid; is your activity running? error
//                        showUpdateDialog()
                        (context as CustomAppActivityImpl).inAppUpdate(
                            "To ensure all users are on the same version, please update your app to the newest version of ${context.getString(
                                R.string.app_name
                            )}",
                            "New Update Available!"
                        )
                        LogManager.logger.i(
                            ArchitectureApp.instance!!.tag,
                            "Latest version is : $latestVersion"
                        )
                    }
                }
            }
            super.onPostExecute(result)
        }

        private fun checkVersion(old: String, new: String): Boolean {
            var isLatestVersion = false
            val newVersion = convertVersion(new)
            val currentVersion = convertVersion(old)
            if (newVersion >= currentVersion)
                isLatestVersion = true
            return isLatestVersion
        }

        private fun convertVersion(version: String): Double {
            var convertedVersion = 0.0
            var ver = version.replace(".", "")
            val version1 = ver.substring(0, 1)
            val version2 = ver.substring(1, ver.length)
            ver = "$version1.$version2"
            convertedVersion = ver.toDouble()
            return convertedVersion
        }
    }

    internal fun convertVersion(version: String): Double {
        var convertedVersion = 0.0
        var ver = version.replace(".", "")
        val version1 = ver.substring(0, 1)
        val version2 = ver.substring(1, ver.length)
        ver = "$version1.$version2"
        convertedVersion = ver.toDouble()
        return convertedVersion
    }

    internal fun inAppUpdate(message: String, title: String) {
        val alertDialog = AlertDialog.Builder(
            this@CustomAppActivityImpl, R.style.MyDialogTheme2
        )
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton(getString(R.string.text_update)) { dialog, _ ->
            dialog.dismiss()
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse
                        ("market://details?id=$packageName")
                )
            )
        }
        alertDialog.setCancelable(false)

        if (!isFinishing)
            alertDialog.show()
    }

    private fun privateAlbum(id: String) {
        switchActivity(Constants.Intent.PrivateAlbumImages, false, Bundle().apply {
            putString(Constants.IntentDataKeys.UserId, id)
            putString(Constants.IntentDataKeys.Type, "${Constants.ImagePicker.View}")
        })
    }

    private fun otherUser(id: Int) {
        switchActivity(Constants.Intent.ProfileUser, false, Bundle().apply {
            putInt(Constants.IntentDataKeys.UserId, id)
        })
    }

    private fun eventDetails(id: Int) {
        startActivity(Intent(Constants.Intent.EventDetails).putExtras(Bundle().apply {
            putInt(Constants.IntentDataKeys.EventId, id)
        }))
    }

   *//* private fun chatUser(conversationModel: ConversationModel, imageUrl: String) {
        switchActivity(Constants.Intent.Chat, false, Bundle().apply {
            putString(Constants.IntentDataKeys.Conversation, Gson().toJson(conversationModel))
            putString(Constants.IntentDataKeys.UserImage, imageUrl)
        })
    }*//*

    private fun timeLineRedirects(pushId: String?, pushtype: String?) {








        Handler(Looper.getMainLooper()).postDelayed({

            startActivity(Intent(Constants.Intent.Home) .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK).putExtras(Bundle().apply {
                putString(Constants.IntentDataKeys.TimeLinePushId,pushId)
                putString(Constants.IntentDataKeys.TimeLinePost,pushtype)
            }))
   finish()

        }, 1000)


    }
*/




}
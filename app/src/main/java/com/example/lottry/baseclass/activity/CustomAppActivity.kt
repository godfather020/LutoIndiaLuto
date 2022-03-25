package  com.softs.meetupfellow.components.activity

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.graphics.drawable.BitmapDrawable

import android.graphics.Bitmap

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType
import okhttp3.RequestBody


/**
 * Created by xeemu on 07-10-2017.
 */
@SuppressLint("Registered")
@Suppress("DEPRECATION")
open class CustomAppActivity : AppCompatActivity() {

    private var instance: Context? = null
    protected var postId = ""
    protected var roomId = ""

    protected var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        initCustomAppActivityImpl()


    }

    private fun initCustomAppActivityImpl() {
//        context = this
    }

    protected fun setInstance(context: Context?) {
        instance = context
    }

    protected fun getInstance() = instance

    internal fun switchActivity(activity: String, finish: Boolean, extras: Bundle?) {
        startActivity(Intent(activity).putExtras(extras!!))
        when (finish) {
            true -> {
                finish()
            }
        }
    }


    internal fun switchActivityOnly(activity: String, finish: Boolean) {
        startActivity(Intent(activity))
        when (finish) {
            true -> {
                finish()
            }
        }
    }





    protected fun logout() {
//        startActivity(
//            Intent(Constants.Intent.Login)
//                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        )
//        finish()
    }

    internal fun showToast(message:String){

        Toast.makeText(instance,message,Toast.LENGTH_LONG).show()
    }

    internal fun showProgressView(layout: View) {
        layout.visibility = View.VISIBLE
    }

    internal fun hideProgressView(layout: View) {
        layout.visibility = View.GONE
    }


    internal fun gradientTextColor( start : Int , end : Int , txt:TextView) : Shader {

        val shader: Shader = LinearGradient(
            0f, 0f, 400f, txt.textSize,
            ContextCompat.getColor(applicationContext,
                start), ContextCompat.getColor(applicationContext, end), Shader.TileMode.REPEAT
        )
        return shader;

    }
//    open fun toRequestBody(jsonBody: String?): RequestBody? {
//        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonBody)
//    }

    /*private val textGradient  by lazy {
        findViewById<TextView>(R.id.text_gradient)
    }
    private val color0 by lazy {
        ContextCompat.getColor(applicationContext, R.color.bg_button_intro_start)
    }
    private val color1 by lazy {
        ContextCompat.getColor(applicationContext, R.color.bg_button_intro_end)
    }


    private val textGradientOnGlobalLayoutListener = object: ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            textGradient.paint.shader = LinearGradient(0f, 0f,
                textGradient.width.toFloat(),
                textGradient.height.toFloat(),
                color0, color1, Shader.TileMode.CLAMP)
            textGradient.viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    }*/






    fun indexExists(list: ArrayList<*>, index: Int): Boolean = index >= 0 && index < list.size

    private fun setColor(value: String, color: Int): String {
        return "<font color_preference_selector=" + color + ">" +
                value + "</font>"
    }

    internal fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString().trim())
            }
        })
    }

    internal fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.getRunningServices(Integer.MAX_VALUE)
            .any { serviceClass.name == it.service.className }
    }

    internal fun updateConversationList() {
//        updateDetails(Constants.BroadcastReceiver.UPDATE_CONVERSATION)
    }

    private fun updateDetails() {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
        intent.action = "com.oit.datingondl.BroadcastReceiver"
//        intent.putExtra(Constants.BroadcastReceiver.FLAG, flag)
        sendBroadcast(intent)
    }

    @SuppressLint("NewApi")
    protected fun isActivityRunning(activityClass: Class<*>): Boolean {
        val activityManager =
            baseContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val tasks = activityManager.getRunningTasks(Integer.MAX_VALUE)

        for (task in tasks) {
            if (activityClass.canonicalName!!.equals(
                    task.baseActivity!!.className,
                    ignoreCase = true
                )
            )
                return true
        }

        return false
    }

    protected fun clearNotifications() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }
    open fun setBackground(context: Context, view: View, drawableId: Int) {
        var bitmap = BitmapFactory.decodeResource(
            context.resources,
            drawableId
        )
        bitmap = Bitmap.createScaledBitmap(
            bitmap!!,
            Resources.getSystem().getDisplayMetrics().widthPixels,
            Resources.getSystem().getDisplayMetrics().heightPixels,
            true
        )
        val bitmapDrawable = BitmapDrawable(context.resources, bitmap)
        view.background = bitmapDrawable
    }

    open fun loadFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        containerId: Int,
        shouldRemovePreviousFragments: Boolean,
        currentTitle: CharSequence,
        arg: Bundle?
    ) {
        val transaction = fragmentManager.beginTransaction()
        if (shouldRemovePreviousFragments) {
            if (fragmentManager.backStackEntryCount > 0) {
                for (i in 0..fragmentManager.backStackEntryCount) {
                    fragmentManager.popBackStackImmediate()
                }
            }
        } else transaction.addToBackStack(currentTitle.toString())
        if (arg != null) {
            Log.d("bundle==", arg.toString())
            fragment.arguments = arg
        }
       /* transaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )*/
        transaction.replace(containerId, fragment, currentTitle.toString()).commit()
    }
    open fun hideKeyBoardOnTouchScreen(view: View){


            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)


    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        if (currentFocus != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    open fun setImage(url: String?, imageView: ImageView?, placeHolde: Drawable?) {
        if (placeHolde != null) {
            Picasso.get().load(url).placeholder(placeHolde).into(imageView)
        }
    }
    open fun setCircleImage(url: String?, imageView: CircleImageView?, placeHolde: Drawable?) {
        if (placeHolde != null) {
            Picasso.get().load(url).placeholder(placeHolde).into(imageView)
        }
    }
}
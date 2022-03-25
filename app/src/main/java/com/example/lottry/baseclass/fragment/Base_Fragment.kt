package com.example.lottry.baseclass.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.lottry.application.WikiApplication
import com.example.lottry.dragger.AppComponent
import okhttp3.MediaType
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*

open class Base_Fragment : Fragment(){

    private var instance: Context? = null

    protected val component: AppComponent
        get() = WikiApplication.component!!


    protected fun setInstance(context: Context?) {
        instance = context
    }

    protected fun getInstance() = instance

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

//    open fun toRequestBody(jsonBody: String?): RequestBody? {
//        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonBody)
//    }

    internal fun showToast(message:String){

        Toast.makeText(instance,message, Toast.LENGTH_LONG).show()
    }


    internal fun convrtDate(date:String):String{
        val f = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val d: Date =
            f.parse(date)
        val f2 = SimpleDateFormat("MMMM dd, yyyy")

        val date :Date=f2.parse(d.toString())

        return date.toString()

    }


}
package com.example.lottry.utils.shared_prefrence

import android.content.Context
import android.content.SharedPreferences
import com.example.lottry.data.remote.retrofit.response.UserDetail
import com.example.lottry.utils.Constant

class SharedPreferencesUtil(internal val context: Context) {



    private var sp: SharedPreferences? = null
    private val KEY_USER_DATA = "key_user_data"

    init  {

        sp = context!!.getSharedPreferences("My_Prefrence", Context.MODE_PRIVATE)
    }



    

      fun saveUserData(userDetail: UserDetail?) {
        SharedPrefrenceUtilImpl().addObjectInJsonString(
            sp!!,
            KEY_USER_DATA,
            userDetail,
            UserDetail::class.java
        )
    }

      fun getUserData(): UserDetail? {
        return SharedPrefrenceUtilImpl().getObjectFromJsonString(
            sp!!, KEY_USER_DATA,
            UserDetail::class.java
        ) as UserDetail
    }

       fun saveString(key: String?, value: String?) {
        SharedPrefrenceUtilImpl().addString(sp!!!!, key, value)
    }

      fun getString(key: String?): String? {
        return SharedPrefrenceUtilImpl().getString(sp!!, key)
    }

      fun saveBoolean(key: String?, value: Boolean) {
        SharedPrefrenceUtilImpl().addBoolean(sp!!, key, value)
    }

      fun getBoolean(key: String?): Boolean? {
        return SharedPrefrenceUtilImpl().getBoolean(sp!!, key)
    }

      fun clearData() {
        SharedPrefrenceUtilImpl().clearSharedPrefrence(sp!!)
    }

      fun saveInteger(key: String?, value: Int?) {
        SharedPrefrenceUtilImpl().addInteger(sp!!, key, value)
    }

      fun getInteger(key: String?): Int? {
        return SharedPrefrenceUtilImpl().getInteger(sp!!, key)
    }


      fun isFirstTimeLaunch(): Boolean {
        return SharedPrefrenceUtilImpl().isFirstTimeLaunch(sp!!, Constant.sharedPrefrencesConstant.FIRST_TIME)
    }
}
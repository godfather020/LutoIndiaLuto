package com.example.lottry.utils.shared_prefrence

import android.content.SharedPreferences
import com.google.gson.GsonBuilder

class SharedPrefrenceUtilImpl {

    private var editor: SharedPreferences.Editor? = null


    fun addObjectInJsonString(
        sp: SharedPreferences,
        key: String?,
        `object`: Any?,
        objectClass: Class<*>?
    ) {
        editor = sp.edit()
        val gsonString = GsonBuilder().create().toJson(`object`, objectClass)
        editor!!!!.putString(key, gsonString)
        editor!!.apply()
    }

    fun getObjectFromJsonString(sp: SharedPreferences, key: String?, objectClass: Class<*>?): Any? {
        var `object`: Any? = Any()
        val data = sp.getString(key, "")
        `object` = if (data!!.isEmpty()) {
            null
        } else GsonBuilder().create().fromJson<Any>(data, objectClass)
        return `object`
    }

    fun addString(sp: SharedPreferences, key: String?, value: String?) {
        editor = sp.edit()
        editor!!.putString(key, value)
        editor!!.apply()
    }

    fun getString(sp: SharedPreferences, key: String?): String? {
        return sp.getString(key, null)
    }

    fun addBoolean(sp: SharedPreferences, key: String?, value: Boolean) {
        editor = sp.edit()
        editor!!.putBoolean(key, value)
        editor!!.apply()
    }

    fun getBoolean(sp: SharedPreferences, key: String?): Boolean {
        return sp.getBoolean(key, false)
    }

    fun addInteger(sp: SharedPreferences, key: String?, value: Int?) {
        editor = sp.edit()
        editor!!.putInt(key, value!!)
        editor!!.apply()
    }

    fun getInteger(sp: SharedPreferences, key: String?): Int? {
        return sp.getInt(key, -1)
    }

    fun clearSharedPrefrence(sp: SharedPreferences) {
        editor = sp.edit()
        editor!!.clear()
        editor!!.apply()
    }

    fun isFirstTimeLaunch(sp: SharedPreferences, key: String?): Boolean {
        return sp.getBoolean(key, true)
    }

}

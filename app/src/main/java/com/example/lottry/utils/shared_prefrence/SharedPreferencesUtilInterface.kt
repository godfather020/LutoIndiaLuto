package com.example.lottry.utils.shared_prefrence

interface SharedPreferencesUtilInterface {

    fun saveString(key: String?, value: String?)

    fun getString(key: String?): String?

    fun saveBoolean(key: String?, value: Boolean)

    fun getBoolean(key: String?): Boolean?

    fun clearData()

    fun saveInteger(key: String?, value: Int?)

    fun getInteger(key: String?): Int?
}
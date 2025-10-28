package com.project.credmanager.utils

import android.content.Context
import android.content.SharedPreferences

object AppPreference {

    const val GENERATED_USER_ID = "generatedUserId"
    const val USERID = "userID"
    const val EMAIL_ID = "emailId"
    const val USER_PHONE = "userPhone"
    const val DEVICE_ID = "deviceId"
    const val INTERNAL_ID = "internalId"
    const val LOGIN_STATUS = "loginStatus"

    private fun putString(context: Context, key: String, value: String) {
        val sharedPref = context.getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun getString(context: Context, key: String): String? {
        val sharedPref = context.getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
        return sharedPref.getString(key, "0")
    }

    private fun putBoolean(context: Context, key: String, value: Boolean) {
        val sharedPref = context.getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun getBoolean(context: Context, key: String): Boolean {
        val sharedPref = context.getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
        return sharedPref.getBoolean(key, false)
    }

    fun setGeneratedUserId(context: Context, value: String) {
        putString(context, GENERATED_USER_ID, value)
    }

    fun getGeneratedUserId(context: Context): String? {
        return getString(context, GENERATED_USER_ID)
    }

    fun setUserId(context: Context, value: String) {
        putString(context, USERID, value)
    }

    fun getUserId(context: Context): String? {
        return getString(context, USERID)
    }

    fun setEmailId(context: Context, value: String) {
        putString(context, EMAIL_ID, value)
    }

    fun getEmailId(context: Context): String? {
        return getString(context, EMAIL_ID)
    }

    fun setUserPhone(context: Context, value: String) {
        putString(context, USER_PHONE, value)
    }

    fun getUserPhone(context: Context): String? {
        return getString(context, USER_PHONE)
    }

    fun setDeviceId(context: Context, value: String) {
        putString(context, DEVICE_ID, value)
    }

    fun getDeviceId(context: Context): String? {
        return getString(context, DEVICE_ID)
    }

    fun setInternalId(context: Context, value: String) {
        putString(context, INTERNAL_ID, value)
    }

    fun getInternalId(context: Context): String? {
        return getString(context, INTERNAL_ID)
    }

    fun setLoginStatus(context: Context, value: Boolean) {
        putBoolean(context, LOGIN_STATUS, value)
    }

    fun getLoginStatus(context: Context): Boolean {
        return getBoolean(context, LOGIN_STATUS)
    }

}
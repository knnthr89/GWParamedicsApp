package com.guelphwellingtonparamedicsapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import java.util.regex.Pattern

object Utils {
    fun hasInternetConnection(context: Context): Boolean {
        var hasInternet = false
        try {
            val connect = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val resultWifi = connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            if (resultWifi != null && resultWifi.isConnectedOrConnecting) {
                hasInternet = true
            } else {
                val resultMobile = connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                if (resultMobile != null && resultMobile.isConnectedOrConnecting) {
                    hasInternet = true
                }
            }
        } catch (ex: Exception) {
            Log.e(Utils::class.java.canonicalName, ex.message!!)
        }
        return hasInternet
    }

    fun validateEmail(email: String) : Boolean {
        return  EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }

    fun validatePassword(password : String) : Boolean{
        return password.length >= 8
    }

    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
}
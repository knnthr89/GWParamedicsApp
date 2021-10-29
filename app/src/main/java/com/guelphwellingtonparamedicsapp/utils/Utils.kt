package com.guelphwellingtonparamedicsapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

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
}
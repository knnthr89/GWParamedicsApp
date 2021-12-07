package com.guelphwellingtonparamedicsapp.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.media.effect.Effect
import android.net.ConnectivityManager
import android.os.Build
import android.os.VibrationEffect.*
import android.os.Vibrator
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import java.util.regex.Pattern
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentManager


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

    fun vibrate(application: Application){
        val v = application.getSystemService(VIBRATOR_SERVICE) as Vibrator?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            v!!.vibrate(createPredefined(EFFECT_CLICK))
        }
    }

    fun backButton(fragmentManager : FragmentManager, application: Application){
        vibrate(application = application)
        fragmentManager?.popBackStack()
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
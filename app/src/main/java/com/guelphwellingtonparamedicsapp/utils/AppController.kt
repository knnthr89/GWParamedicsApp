package com.guelphwellingtonparamedicsapp.utils

import android.app.Application
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class AppController : Application(){

    private var mRequestQueue : RequestQueue? = null
    private val requestQueue : RequestQueue
    get() {
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(applicationContext)
        }

        return mRequestQueue!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun <T> addToRequestQueue(req:Request<T>, tag : String = javaClass.canonicalName){
        req.tag = tag
        requestQueue.add(req)
    }

    fun cancelPendingRequest(tag: Any){
        if(mRequestQueue != null){
            mRequestQueue!!.cancelAll(tag)
        }
    }

    companion object {
        @get:Synchronized
        var instance : AppController? = null
        private set
    }
}
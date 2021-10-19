package com.guelphwellingtonparamedicsapp.utils

import android.content.Context
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import org.json.JSONObject
import java.net.CacheResponse
import com.guelphwellingtonparamedicsapp.R

class Communication(var context: Context?) {

    companion object {
        var SERVER = "http://fcf8-2607-fea8-1be0-1bcb-58d5-9ef0-9e80-c15e.ngrok.io/"
    }

    private var communicationListener: CommunicationListener? = null

    interface CommunicationListener {
        fun onCommunicationSuccess(path: CommunicationPath, response: JSONObject?)
        fun onCommunicationError(path: CommunicationPath, error: String, code: Int?)
    }

    fun getJSON(path: CommunicationPath, eParams: HashMap<String, String> = HashMap()) {
        val responseListener = Response.Listener<String> { response ->
            if (response.isNotBlank()) {
                when(path){
                    CommunicationPath.CONTACT_GROUPS -> {
                        val res = JSONArray(response)
                        val obj = JSONObject()
                        obj.put("data", res)
                        communicationListener?.onCommunicationSuccess(path, obj)
                    }
                    CommunicationPath.INTERACTIVE_FORMS -> {
                        val res = JSONArray(response)
                        val obj = JSONObject()
                        obj.put("data", res)
                        communicationListener?.onCommunicationSuccess(path, obj)
                    }
                    else -> {
                        val res = JSONObject(response)
                        communicationListener?.onCommunicationSuccess(path, res)
                    }
                }

            }
        }

        val errorListener = Response.ErrorListener { error ->
            val message: String?
            if (error is TimeoutError) {
                message = context?.getString(R.string.volley_timeout_error)
            } else if (error is NoConnectionError) {
                message = context?.getString(R.string.volley_no_connection_error)
            } else if (error is AuthFailureError) {
                message = context?.getString(R.string.volley_auth_failure_error)
            } else if (error is ServerError) {
                message = context?.getString(R.string.volley_server_error)
            } else if (error is NetworkError) {
                message = context?.getString(R.string.volley_network_error)
            } else if (error is ParseError) {
                message = context?.getString(R.string.volley_parse_error)
            } else {
                message = context?.getString(R.string.volley_unknown_error)
            }

            val code = error.networkResponse?.statusCode ?: 503
            communicationListener?.onCommunicationError(path, message!!, code)

        }

        var webServices = "$SERVER${path.path}"

        for ((key, value) in eParams) {
            webServices += "$key=$value&"
        }

        val req = object : StringRequest(Method.GET, webServices, responseListener, errorListener) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["content-type"] = "application/json"
                return headers
            }
        }
        req.retryPolicy = DefaultRetryPolicy(
            20000,
            0,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        AppController.instance!!.addToRequestQueue(req)

    }

    fun setCommunicationListener(communicationListener: CommunicationListener){
        this.communicationListener = communicationListener
    }
}

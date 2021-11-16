package com.guelphwellingtonparamedicsapp.utils

import android.content.Context
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import org.json.JSONObject
import java.net.CacheResponse
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.daos.UserDao

class Communication(var context: Context?) {

    private var mUserDao: UserDao? = null

    companion object {
        var SERVER = "http://10.0.2.2:5000/"
        //var SERVER = "http://1b68-2607-fea8-1be0-1bcb-514e-55cb-3a02-516.ngrok.io/"
        var TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6Ijk3MGExMjA5LTg4ZjAtNDAxOS04Mzk1LTBkZmFmNjVmNTA1MCIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL2VtYWlsYWRkcmVzcyI6ImFkbWluQGd1ZWxwaC5jYSIsImp0aSI6ImVkZjVlMjA0LTZlOWQtNDdjMy04N2RmLWViZjZiOGIyZTZkYiIsImh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vd3MvMjAwOC8wNi9pZGVudGl0eS9jbGFpbXMvcm9sZSI6IkFkbWluIiwiZXhwIjoxNjM3NjAwMzk2LCJpc3MiOiJQYXJhbWVkaWNBUEkiLCJhdWQiOiJQYXJhbWVkaWNBUEkifQ.i9n2l8VtYjRjnFkl_W_tRP4oaaTmd5cPm4xeVdwSHw8"
    }

    private var communicationListener: CommunicationListener? = null

    interface CommunicationListener {
        fun onCommunicationSuccess(path: CommunicationPath, response: JSONObject?)
        fun onCommunicationError(path: CommunicationPath, error: String, code: Int?)
    }

    fun getJSON(
        path: CommunicationPath,
        eParams: HashMap<String, String> = HashMap(),
        id: Int? = null
    ) {
        val responseListener = Response.Listener<String> { response ->
            if (response.isNotBlank()) {
                when (path) {
                    CommunicationPath.CONTACT_GROUPS -> {
                        val obj = JSONObject()
                        obj.put("data", response)
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
            when (error) {
                is TimeoutError -> {
                    message = context?.getString(R.string.volley_timeout_error)
                }
                is NoConnectionError -> {
                    message = context?.getString(R.string.volley_no_connection_error)
                }
                is AuthFailureError -> {
                    message = context?.getString(R.string.volley_auth_failure_error)
                }
                is ServerError -> {
                    message = context?.getString(R.string.volley_server_error)
                }
                is NetworkError -> {
                    message = context?.getString(R.string.volley_network_error)
                }
                is ParseError -> {
                    message = context?.getString(R.string.volley_parse_error)
                }
                else -> {
                    message = context?.getString(R.string.volley_unknown_error)
                }
            }

            val code = error.networkResponse?.statusCode ?: 503
            communicationListener?.onCommunicationError(path, message!!, code)

        }

        var webServices = if (id == null) {
            "$SERVER${path.path}"
        } else {
            "$SERVER${path.path}/$id"
        }

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
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        AppController.instance!!.addToRequestQueue(req)

    }

    fun postJSON(
        path: CommunicationPath,
        bodyData: String = "",
        eParams: HashMap<String, String> = HashMap(), id: Int? = null
    ) {
        val responseListener = Response.Listener<String> { response ->
            when (path) {
                    CommunicationPath.ASSESSMENTS -> {
                        val jsonObject = JSONObject()
                        jsonObject.put("message", "The patient's evaluation was successfully saved on the server.")
                        val obj = JSONObject()
                        obj.put("data", jsonObject)
                        communicationListener?.onCommunicationSuccess(path, obj)
                    }
                    else -> {
                        val res = JSONObject(response)
                        communicationListener?.onCommunicationSuccess(path, res)
                    }
            }
        }

        val errorListener = Response.ErrorListener { error ->
            val message: String?
            Log.e("ENTRA", "error ${error.networkResponse.statusCode}")
            when (error) {
                is TimeoutError -> {
                    message = context?.getString(R.string.volley_timeout_error)
                }
                is NoConnectionError -> {
                    message = context?.getString(R.string.volley_no_connection_error)
                }
                is AuthFailureError -> {
                    message = context?.getString(R.string.volley_auth_failure_error)
                }
                is ServerError -> {
                    message = context?.getString(R.string.volley_server_error)
                }
                is NetworkError -> {
                    message = context?.getString(R.string.volley_network_error)
                }
                is ParseError -> {
                    message = context?.getString(R.string.volley_parse_error)
                }
                else -> {
                    message = context?.getString(R.string.volley_unknown_error)
                }
            }

            val code = error.networkResponse?.statusCode ?: 503
            communicationListener?.onCommunicationError(path, message!!, code)

        }

        var webServices = if (id == null) {
            "$SERVER${path.path}"
        } else {
            "$SERVER${path.path}/$id"
        }

        for ((key, value) in eParams) {
            webServices += "$key=$value&"
        }
        val req = object :
            StringRequest(Method.POST, webServices, responseListener, errorListener) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["content-type"] = "application/json"
                headers["Authorization"] = "Bearer $TOKEN"
                return headers
            }


            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                for ((key, value) in eParams) {
                    params[key] = value
                }
                return params
            }

            override fun getBody(): ByteArray {
                return bodyData.toByteArray()
            }
        }
        req.retryPolicy = DefaultRetryPolicy(
            20000,
            0,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        AppController.instance!!.addToRequestQueue(req)

    }

    fun setCommunicationListener(communicationListener: CommunicationListener) {
        this.communicationListener = communicationListener
    }
}

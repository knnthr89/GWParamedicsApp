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
import com.guelphwellingtonparamedicsapp.database.AppDatabase

class Communication(var context: Context?) {


    private val db = AppDatabase.getDatabase(context = context!!)
    private var mUserDao: UserDao? = db?.userDao()

    companion object {
        //var SERVER = "http://10.0.2.2:5000/"
        var SERVER = "http://49d3-2607-fea8-1be0-1bcb-7c4a-f1b7-896e-7d6b.ngrok.io/"
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
                    CommunicationPath.RESOURCE_REGIONS -> {
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
                headers["Authorization"] = "Bearer ${if(path == CommunicationPath.LOGIN) "" else mUserDao!!.getUser()[0].token}"
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

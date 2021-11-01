package com.guelphwellingtonparamedicsapp.manager

import android.annotation.SuppressLint
import android.content.Context
import com.guelphwellingtonparamedicsapp.utils.Communication
import com.guelphwellingtonparamedicsapp.utils.CommunicationPath
import org.json.JSONObject

class SessionManager(var context: Context) : Communication.CommunicationListener {

    private var loginListener: LoginListener? = null

    interface LoginListener {
        fun onLoginSuccess()
        fun onLoginFail(message: String, code: Int?)
    }

    fun setLoginListener(listener: LoginListener){
        this.loginListener = listener
    }

    fun getlogin(email: String, password: String) {
        val body = JSONObject()
        body.put("email", email)
        body.put("password", password)
        val communication = Communication(context)
        communication.setCommunicationListener(this)
        communication.postJSON(path = CommunicationPath.LOGIN, bodyData = body.toString())
    }


    override fun onCommunicationSuccess(path: CommunicationPath, response: JSONObject?) {
        when (path) {
            CommunicationPath.LOGIN -> {
               processLogin(response!!)
            }
        }
    }

    override fun onCommunicationError(path: CommunicationPath, error: String, code: Int?) {
        when(path) {
            CommunicationPath.LOGIN -> {
                loginListener?.onLoginFail(error, code)
            }
        }
    }

    private fun processLogin(json : JSONObject){
        if (json.has("token")) {
            val token = json.getString("token")
            loginListener?.onLoginSuccess()
        }else{
            loginListener?.onLoginFail("JSON does not include user's token.", 201)
        }

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var mInstance: SessionManager? = null

        fun getInstance(context: Context): SessionManager {
            if (mInstance == null)
                mInstance = SessionManager(context)
            return mInstance!!
        }
    }
}
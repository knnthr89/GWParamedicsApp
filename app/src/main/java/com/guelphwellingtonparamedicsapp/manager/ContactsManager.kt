package com.guelphwellingtonparamedicsapp.manager

import android.annotation.SuppressLint
import android.content.Context
import com.guelphwellingtonparamedicsapp.utils.Communication
import com.guelphwellingtonparamedicsapp.utils.CommunicationPath
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.Console

class ContactsManager(var context: Context) : Communication.CommunicationListener {

    private var contactsGroupsListener: ContactsGroupsListener? = null


    interface ContactsGroupsListener {
        fun onContactsGroupsSuccess(message: String)
        fun onContactsGroupsFail(message: String, code: Int?)
    }

    fun setContactsGroupsListener(listener: ContactsGroupsListener?) {
        this.contactsGroupsListener = listener
    }

    fun getContactsGroups() {
        val communication = Communication(context)
        communication.setCommunicationListener(this)
        communication.getJSON(path = CommunicationPath.CONTACT_GROUPS)
    }

    override fun onCommunicationSuccess(path: CommunicationPath, response: JSONObject?) {
        when (path) {
            CommunicationPath.CONTACT_GROUPS -> {
                processContactGroups(response!!)
            }
        }
    }

    override fun onCommunicationError(path: CommunicationPath, error: String, code: Int?) {
        when(path) {
            CommunicationPath.CONTACT_GROUPS -> {
                contactsGroupsListener?.onContactsGroupsFail(error, code)
            }
        }
    }

    private fun processContactGroups(json: JSONObject) {
        contactsGroupsListener?.onContactsGroupsSuccess(json.toString())
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var mInstance: ContactsManager? = null

        fun getInstance(context: Context): ContactsManager {
            if (mInstance == null)
                mInstance = ContactsManager(context)
            return mInstance!!
        }
    }
}
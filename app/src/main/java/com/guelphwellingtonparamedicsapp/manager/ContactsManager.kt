package com.guelphwellingtonparamedicsapp.manager

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.guelphwellingtonparamedicsapp.models.AreaModel
import com.guelphwellingtonparamedicsapp.models.InteractiveFormModel
import com.guelphwellingtonparamedicsapp.models.RegionModel
import com.guelphwellingtonparamedicsapp.utils.Communication
import com.guelphwellingtonparamedicsapp.utils.CommunicationPath
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.Console

class ContactsManager(var context: Context) : Communication.CommunicationListener {

    private var contactsGroupsListener: ContactsGroupsListener? = null


    interface ContactsGroupsListener {
        fun onContactsGroupsSuccess(areasList: LiveData<List<AreaModel>>)
        fun onContactsGroupsFail(message: String, code: Int?)
    }

    fun setContactsGroupsListener(listener: ContactsGroupsListener?) {
        this.contactsGroupsListener = listener
    }

    fun getIndividualArea(id : Int) {
        val communication = Communication(context)
        communication.setCommunicationListener(this)
        communication.getJSON(path = CommunicationPath.CONTACT_GROUPS, id = id)
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
        when (path) {
            CommunicationPath.CONTACT_GROUPS -> {
                contactsGroupsListener?.onContactsGroupsFail(error, code)
            }
        }
    }

    private fun processContactGroups(json: JSONObject) {
        val gson = Gson()
        val areasList = ArrayList<AreaModel>()
        val areasListLiveData = MutableLiveData<List<AreaModel>>()
        var areaModel : AreaModel? = null

        if(json.has("data")){
            var jsonArray = json.getJSONArray("data")

            for (i in 0 until jsonArray.length()) {
                val o: JSONObject = jsonArray.getJSONObject(i)
                val objModel: AreaModel = gson.fromJson(
                    o.toString(),
                    AreaModel::class.java
                )
                areasList.add(objModel)
            }

            areasListLiveData.value = areasList
        }else{
           areaModel  = gson.fromJson(
                json.toString(),
                AreaModel::class.java
            )

            areasListLiveData.value = listOf(areaModel)
        }

        contactsGroupsListener?.onContactsGroupsSuccess(areasListLiveData)
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
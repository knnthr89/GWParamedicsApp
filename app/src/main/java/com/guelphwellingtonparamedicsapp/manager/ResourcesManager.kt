package com.guelphwellingtonparamedicsapp.manager

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson
import com.guelphwellingtonparamedicsapp.models.RegionModel
import com.guelphwellingtonparamedicsapp.utils.Communication
import com.guelphwellingtonparamedicsapp.utils.CommunicationPath
import org.json.JSONObject

class ResourcesManager(var context: Context) : Communication.CommunicationListener {

    private var getResourcesListener : GetResourcesListener? = null

    interface GetResourcesListener {
        fun onResourcesSuccess(regionModelList : ArrayList<RegionModel>)
        fun onResourcesFail(message: String, code: Int?)
    }

    fun setAllResourcesListener(getResourcesListener : GetResourcesListener){
        this.getResourcesListener = getResourcesListener
    }

    fun getAllResources(){
        val communication = Communication(context)
        communication.setCommunicationListener(this)
        communication.getJSON(path = CommunicationPath.RESOURCE_REGIONS)
    }

    override fun onCommunicationSuccess(path: CommunicationPath, response: JSONObject?) {
        when(path){
            CommunicationPath.RESOURCE_REGIONS -> {
                processGetAllResources(response!!)
            }
        }
    }

    override fun onCommunicationError(path: CommunicationPath, error: String, code: Int?) {
        when(path){
            CommunicationPath.RESOURCE_REGIONS -> {
                getResourcesListener?.onResourcesFail(error, code)
            }
        }
    }

    fun processGetAllResources(response: JSONObject){
        val gson = Gson()
        var arrayRegionModel : ArrayList<RegionModel> = ArrayList()
        var jsonArray= response.getJSONArray("data")

        for (i in 0 until jsonArray.length()) {
            val o: JSONObject = jsonArray.getJSONObject(i)
            val objModel : RegionModel = gson.fromJson(
                o.toString(),
                RegionModel::class.java
            )

            arrayRegionModel.add(objModel)
        }

        getResourcesListener?.onResourcesSuccess(arrayRegionModel)

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var mInstance: ResourcesManager? = null

        fun getInstance(context: Context): ResourcesManager {
            if (mInstance == null)
                mInstance = ResourcesManager(context)
            return mInstance!!
        }
    }
}
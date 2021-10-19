package com.guelphwellingtonparamedicsapp.manager

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson
import com.guelphwellingtonparamedicsapp.models.AssessmentModel
import com.guelphwellingtonparamedicsapp.models.InteractiveFormModel
import com.guelphwellingtonparamedicsapp.utils.Communication
import com.guelphwellingtonparamedicsapp.utils.Communication.CommunicationListener
import com.guelphwellingtonparamedicsapp.utils.CommunicationPath
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class AssessmentsManager (var context: Context) : CommunicationListener {

    private var interactiveFormsListener : InteractiveFormsListener? = null

    interface InteractiveFormsListener {
        fun onInteractiveFormsSuccess(interactiveFormsList : ArrayList<InteractiveFormModel>)
        fun onInteractiveFormsFail(message: String, code: Int?)
    }

    fun setInteractiveFormsListener(listener: InteractiveFormsListener){
        this.interactiveFormsListener = listener
    }

    fun getInteractiveForms(){
        val communication = Communication(context)
        communication.setCommunicationListener(this)
        communication.getJSON(path = CommunicationPath.INTERACTIVE_FORMS)
    }

    override fun onCommunicationSuccess(path: CommunicationPath, response: JSONObject?) {
        when (path) {
            CommunicationPath.INTERACTIVE_FORMS -> {
                processInteractiveForms(response!!)
            }
        }
    }

    override fun onCommunicationError(path: CommunicationPath, error: String, code: Int?) {
        when (path) {
            CommunicationPath.INTERACTIVE_FORMS -> {
                interactiveFormsListener?.onInteractiveFormsFail(error,code)
            }
        }
    }

    private fun processInteractiveForms(json: JSONObject) {
        val gson = Gson()
        var arrayInteractiveForms : ArrayList<InteractiveFormModel> = ArrayList()
        var jsonArray= json.getJSONArray("data")

        for (i in 0 until jsonArray.length()) {
            val o: JSONObject = jsonArray.getJSONObject(i)
            val objModel : InteractiveFormModel = gson.fromJson(
                o.toString(),
                InteractiveFormModel::class.java
            )
            arrayInteractiveForms.add(objModel)
        }
        interactiveFormsListener?.onInteractiveFormsSuccess(arrayInteractiveForms)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var mInstance: AssessmentsManager? = null

        fun getInstance(context: Context): AssessmentsManager {
            if (mInstance == null)
                mInstance = AssessmentsManager(context)
            return mInstance!!
        }
    }

    @Throws(JSONException::class)
    fun toJsonObject(jsonString: String): JSONObject {
        return JSONObject(jsonString)
    }
}


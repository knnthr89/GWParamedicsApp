package com.guelphwellingtonparamedicsapp.manager

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.guelphwellingtonparamedicsapp.fragments.AssessmentsFragment
import com.guelphwellingtonparamedicsapp.models.AssessmentModel
import com.guelphwellingtonparamedicsapp.models.IndividualFormModel
import com.guelphwellingtonparamedicsapp.models.InteractiveFormModel
import com.guelphwellingtonparamedicsapp.utils.Communication
import com.guelphwellingtonparamedicsapp.utils.Communication.CommunicationListener
import com.guelphwellingtonparamedicsapp.utils.CommunicationPath
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class AssessmentsManager (var context: Context) : CommunicationListener {

    private var interactiveFormsListener : InteractiveFormsListener? = null
    private var individualFormListener : IndividualFormListener? = null

    interface IndividualFormListener {
        fun onIndividualFormSuccess(individualFormModel: IndividualFormModel)
        fun onIndividualFormFail(message: String, code: Int?)
    }

    interface InteractiveFormsListener {
        fun onInteractiveFormsSuccess(interactiveFormsList : ArrayList<InteractiveFormModel>)
        fun onInteractiveFormsFail(message: String, code: Int?)
    }

    fun setInteractiveFormsListener(listener: InteractiveFormsListener){
        this.interactiveFormsListener = listener
    }

    fun setIndividualFormListener(listener: IndividualFormListener){
        this.individualFormListener = listener
    }

    fun getInteractiveForms(){
        val communication = Communication(context)
        communication.setCommunicationListener(this)
        communication.getJSON(path = CommunicationPath.INTERACTIVE_FORMS)
    }

    fun getUniqueInteractiveForm(id : Int){
        val communication = Communication(context)
        communication.setCommunicationListener(this)
        communication.getJSON(path = CommunicationPath.INDIVIDUAL_FORM,id = id)
    }

    override fun onCommunicationSuccess(path: CommunicationPath, response: JSONObject?) {
        when (path) {
            CommunicationPath.INTERACTIVE_FORMS -> {
                processInteractiveForms(response!!)
            }
            CommunicationPath.INDIVIDUAL_FORM -> {
                processIndividualForm(response!!)
            }
        }
    }

    override fun onCommunicationError(path: CommunicationPath, error: String, code: Int?) {
        when (path) {
            CommunicationPath.INTERACTIVE_FORMS -> {
                interactiveFormsListener?.onInteractiveFormsFail(error,code)
            }
            CommunicationPath.INDIVIDUAL_FORM -> {
                individualFormListener?.onIndividualFormFail(error, code)
            }
        }
    }

    private fun processInteractiveForms(json: JSONObject) {
        val gson = Gson()
        var arrayInteractiveForms : ArrayList<InteractiveFormModel> = ArrayList()
        var jsonArray= json.getJSONArray("data")

        System.err.println(json)

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

    fun processIndividualForm(json : JSONObject){
        System.err.println("information $json")
        val gson = Gson()
        val form = gson.fromJson(json.toString(), IndividualFormModel::class.java)
        individualFormListener?.onIndividualFormSuccess(form)
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


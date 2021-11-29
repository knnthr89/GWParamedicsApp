package com.guelphwellingtonparamedicsapp.manager

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.guelphwellingtonparamedicsapp.models.IndividualFormModel
import com.guelphwellingtonparamedicsapp.models.InteractiveFormModel
import com.guelphwellingtonparamedicsapp.utils.Communication
import com.guelphwellingtonparamedicsapp.utils.Communication.CommunicationListener
import com.guelphwellingtonparamedicsapp.utils.CommunicationPath
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AssessmentsManager (var context: Context) : CommunicationListener {

    private var interactiveFormsListener : InteractiveFormsListener? = null
    private var individualFormListener : IndividualFormListener? = null
    private var savePatientAssessment : SavePatientAssessment?= null

    interface SavePatientAssessment {
        fun onSavePatienAssessmentSuccess(message: String)
        fun onSavePatienAssessmentFail(message: String, code: Int?)
    }

    interface IndividualFormListener {
        fun onIndividualFormSuccess(individualFormModel: IndividualFormModel)
        fun onIndividualFormFail(message: String, code: Int?)
    }

    interface InteractiveFormsListener {
        fun onInteractiveFormsSuccess(interactiveFormsList : ArrayList<InteractiveFormModel>)
        fun onInteractiveFormsFail(message: String, code: Int?)
    }

    fun setSavePatientAssessment(listener : SavePatientAssessment){
        this.savePatientAssessment = listener
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

    fun savePatientAssessment(
        totalScore: Int,
        interactiveFormId: Int,
        patientId: String,
        date: String,
        answers: HashMap<Int, String>
    ){

        val jsonArrayAnswers = JSONArray()

        for(z in answers){
            val jsonArraySingleAnswer = JSONObject()
            jsonArraySingleAnswer.put("questionId", z.key.toString())
            jsonArraySingleAnswer.put("answer", z.value)
            jsonArrayAnswers.put(jsonArraySingleAnswer)
        }

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val body = JSONObject()
        body.put("totalScore", totalScore)
        body.put("interactiveFormId", interactiveFormId)
        body.put("totalScore", totalScore)
        body.put("patientID", patientId)
        body.put("assessmentDate", sdf.format(Date()))
        body.put("questionResults", jsonArrayAnswers)

        Log.e("json", body.toString())

        val communication = Communication(context)
        communication.setCommunicationListener(this)
        communication.postJSON(path = CommunicationPath.ASSESSMENTS, bodyData = body.toString())
    }

    override fun onCommunicationSuccess(path: CommunicationPath, response: JSONObject?) {
        when (path) {
            CommunicationPath.INTERACTIVE_FORMS -> {
                processInteractiveForms(response!!)
            }
            CommunicationPath.INDIVIDUAL_FORM -> {
                processIndividualForm(response!!)
            }
            CommunicationPath.ASSESSMENTS -> {
                processSavePatientAssessment(response!!)
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
            CommunicationPath.ASSESSMENTS -> {
                savePatientAssessment?.onSavePatienAssessmentFail(error, code)
            }
        }
    }

    private fun processSavePatientAssessment(json: JSONObject){
        if (json.has("data") && json.get("data") != "") {
            val data = json.getJSONObject("data")
            val message = data.getString("message")
            savePatientAssessment?.onSavePatienAssessmentSuccess(message = message)
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
}


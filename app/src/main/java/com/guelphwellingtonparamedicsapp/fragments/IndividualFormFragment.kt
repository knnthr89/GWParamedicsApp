package com.guelphwellingtonparamedicsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.adapters.IndividualFormAdapter
import com.guelphwellingtonparamedicsapp.adapters.IndividualFormAdapter.SaveAnswer
import com.guelphwellingtonparamedicsapp.adapters.IndividualFormAdapter.SelectedAnswer
import com.guelphwellingtonparamedicsapp.daos.UserDao
import com.guelphwellingtonparamedicsapp.database.AppDatabase
import com.guelphwellingtonparamedicsapp.databinding.FragmentIndividualFormBinding
import com.guelphwellingtonparamedicsapp.manager.AssessmentsManager
import com.guelphwellingtonparamedicsapp.manager.AssessmentsManager.SavePatientAssessment
import com.guelphwellingtonparamedicsapp.models.IndividualFormModel
import com.guelphwellingtonparamedicsapp.models.QuestionModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class IndividualFormFragment : Fragment(), SelectedAnswer, SaveAnswer, SavePatientAssessment {

    lateinit var form : IndividualFormModel
    private var questions: ArrayList<QuestionModel> = ArrayList()
    private var adapter : IndividualFormAdapter? = null
    private lateinit var fragmentIndividualFormBinding : FragmentIndividualFormBinding
    private var hashmapAnswers : HashMap<Int, String> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentIndividualFormBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_individual_form, container, false)

        val bundle = this.arguments
        if (bundle != null) {
            if (bundle.containsKey("individualForm") && bundle.getSerializable("individualForm") != null) {
                form = bundle.getSerializable("individualForm") as IndividualFormModel
            }
            if(form != null){
               fillRecyclerView(form)
            }
        }

        fragmentIndividualFormBinding.calculateBtn.setOnClickListener {
            if(hashmapAnswers.isNotEmpty()){
                AssessmentsManager.getInstance(requireContext()).setSavePatientAssessment(this)
                AssessmentsManager.getInstance(requireContext()).savePatientAssessment(totalScore = 0, interactiveFormId = form.id, patientId = fragmentIndividualFormBinding.patientIdEt.text.toString(), date = SimpleDateFormat("M/dd/yyyy hh:mm:ss").format(Date()), answers = hashmapAnswers)
            }else{
                Toast.makeText(context, R.string.login_email_error, Toast.LENGTH_SHORT).show()
            }
        }

       return fragmentIndividualFormBinding.root
    }

    override fun onStart() {
        super.onStart()

        fragmentIndividualFormBinding.dateEt.setText(SimpleDateFormat("M/dd/yyyy hh:mm:ss").format(Date()))
    }

    fun fillRecyclerView(individualFormModel: IndividualFormModel) {
        if (individualFormModel != null) {
            questions.clear()
            for (i in individualFormModel.sections) {
                for (y in i.questions) {
                    y.showIt = true
                    questions.add(y)
                }
            }
            adapter = IndividualFormAdapter(requireContext(), questions, this, this)
            var mLayoutManager = LinearLayoutManager(requireContext())
            mLayoutManager.orientation = LinearLayoutManager.VERTICAL
            fragmentIndividualFormBinding.formQuestionsRv.layoutManager = mLayoutManager
            fragmentIndividualFormBinding.formQuestionsRv.setHasFixedSize(true)
            fragmentIndividualFormBinding.formQuestionsRv.adapter = adapter
        }
    }

    override fun selected(answer: Boolean) {
        if(hashmapAnswers.size > 1){
            hashmapAnswers.clear()
        }
       if(answer){
           val q2 = questions[1]
           q2.showIt = false
           val q3 = questions[2]
           q3.showIt = true
           val q4 = questions[3]
           q4.showIt = true
       }else{
           val q2 = questions[1]
           q2.showIt = true
           val q3 = questions[2]
           q3.showIt = false
           val q4 = questions[3]
           q4.showIt = false
       }
        adapter?.notifyDataSetChanged()
    }

    override fun saveInArray(id: Int, answer: String, recordValue : Boolean) {
        if(recordValue){
            if(answer.length > 2){
                hashmapAnswers.put(id, answer)
            }else if(answer.length <= 2){
                hashmapAnswers.remove(id)
            }
        }else {
            hashmapAnswers.remove(id)
        }
    }

    override fun onSavePatienAssessmentSuccess(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        fragmentManager?.popBackStack()
    }

    override fun onSavePatienAssessmentFail(message: String, code: Int?) {
        Toast.makeText(context, "$message , $code", Toast.LENGTH_SHORT).show()
    }

}
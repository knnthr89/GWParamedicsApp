package com.guelphwellingtonparamedicsapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.adapters.IndividualFormAdapter
import com.guelphwellingtonparamedicsapp.adapters.IndividualFormAdapter.SaveAnswerList
import com.guelphwellingtonparamedicsapp.adapters.IndividualFormAdapter.SaveAnswer
import com.guelphwellingtonparamedicsapp.adapters.IndividualFormAdapter.SelectedAnswer
import com.guelphwellingtonparamedicsapp.databinding.FragmentIndividualFormBinding
import com.guelphwellingtonparamedicsapp.manager.AssessmentsManager
import com.guelphwellingtonparamedicsapp.manager.AssessmentsManager.SavePatientAssessment
import com.guelphwellingtonparamedicsapp.models.AnswerModel
import com.guelphwellingtonparamedicsapp.models.IndividualFormModel
import com.guelphwellingtonparamedicsapp.models.QuestionModel
import com.guelphwellingtonparamedicsapp.utils.Utils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class IndividualFormFragment : Fragment(), SelectedAnswer, SaveAnswer, SavePatientAssessment,
    SaveAnswerList {

    lateinit var form: IndividualFormModel
    private var questions: ArrayList<QuestionModel> = ArrayList()
    private var adapter: IndividualFormAdapter? = null
    private lateinit var fragmentIndividualFormBinding: FragmentIndividualFormBinding
    private var hashmapAnswers: HashMap<Int, String> = HashMap()
    private var assessmentId: Int = 0
    private var totalScore: Int = 0

    private var hashmapAnswersCheckBox: ArrayList<String> = ArrayList()
    private var answerScore : HashMap<Int, AnswerModel> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentIndividualFormBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_individual_form, container, false)

        arguments?.let {
            form = it.getSerializable("model") as IndividualFormModel
        }
        if (form != null) {
            fillRecyclerView(form)
        }

        fragmentIndividualFormBinding.calculateBtn.setOnClickListener {
            Utils.vibrate(application = requireActivity().application)
            if (hashmapAnswers.isNotEmpty() && fragmentIndividualFormBinding.patientIdEt.text.isNotBlank() && fragmentIndividualFormBinding.paramedicsEt.text.isNotBlank() && fragmentIndividualFormBinding.dateEt.text.isNotBlank()) {
                AssessmentsManager.getInstance(requireContext()).setSavePatientAssessment(this)
                AssessmentsManager.getInstance(requireContext()).savePatientAssessment(
                    totalScore = totalScore,
                    interactiveFormId = form.id,
                    patientId = fragmentIndividualFormBinding.patientIdEt.text.toString(),
                    date = SimpleDateFormat("M/dd/yyyy hh:mm:ss").format(Date()),
                    answers = hashmapAnswers
                )
            } else {
                Toast.makeText(context, R.string.login_email_error, Toast.LENGTH_SHORT).show()
            }
        }

        return fragmentIndividualFormBinding.root
    }

    override fun onStart() {
        super.onStart()
        fragmentIndividualFormBinding.dateEt.setText(
            SimpleDateFormat("M/dd/yyyy hh:mm:ss").format(
                Date()
            )
        )
    }

    fun fillRecyclerView(individualFormModel: IndividualFormModel) {
        if (individualFormModel != null) {
            assessmentId = individualFormModel.id
            questions.clear()
            for (i in individualFormModel.sections) {
                for (y in i.questions) {
                    y.showIt = true
                    questions.add(y)
                }
            }
            adapter =
                IndividualFormAdapter(requireContext(), questions, this, this, assessmentId, this)
            var mLayoutManager = LinearLayoutManager(requireContext())
            mLayoutManager.orientation = LinearLayoutManager.VERTICAL
            fragmentIndividualFormBinding.formQuestionsRv.layoutManager = mLayoutManager
            fragmentIndividualFormBinding.formQuestionsRv.setHasFixedSize(true)
            fragmentIndividualFormBinding.formQuestionsRv.adapter = adapter
        }
    }

    override fun selected(answer: Boolean) {
        if (assessmentId == 1) {
            if (hashmapAnswers.size > 1) {
                hashmapAnswers.clear()
            }
            if (answer) {
                val q2 = questions[1]
                q2.showIt = false
                val q3 = questions[2]
                q3.showIt = true
                val q4 = questions[3]
                q4.showIt = true
                adapter?.notifyItemChanged(1)
                adapter?.notifyItemChanged(2)
                adapter?.notifyItemChanged(3)
            } else {
                val q2 = questions[1]
                q2.showIt = true
                val q3 = questions[2]
                q3.showIt = false
                val q4 = questions[3]
                q4.showIt = false
                adapter?.notifyItemChanged(1)
                adapter?.notifyItemChanged(2)
                adapter?.notifyItemChanged(3)
            }

        }
    }

    override fun saveInArray(id: Int, answer: AnswerModel, recordValue: Boolean) {
        totalScore = 0

        Log.e("llega", answer.value.toString())

        if(recordValue){
            if (answer.description != "[]") {
                answerScore.put(id, answer)
            } else {
                answerScore.remove(id)
            }
        }else{
            hashmapAnswers.remove(id)
        }

        if (recordValue) {
            if (answer.description != "[]") {
                hashmapAnswers.put(id, answer.description)
            } else {
                hashmapAnswers.remove(id)
            }
        } else {
            hashmapAnswers.remove(id)
        }

        answerScore.forEach { t, answerModel ->
            totalScore += answerModel.value
        }

        Log.e("list", hashmapAnswers.toString())
        Log.e("score", totalScore.toString())
    }

    override fun onSavePatienAssessmentSuccess(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        fragmentManager?.popBackStack()
    }

    override fun onSavePatienAssessmentFail(message: String, code: Int?) {
        Toast.makeText(context, "$message , $code", Toast.LENGTH_SHORT).show()
    }

    override fun saveAnswers(id: Int, answers: ArrayList<AnswerModel>) {
        totalScore = 0

        answers.forEach {
            if (it.description != "[]") {
                answerScore.put(id, it)
            } else {
                answerScore.remove(id)
            }
        }

        answers.forEach {
            if (it.description != "[]") {
                if(!hashmapAnswersCheckBox.contains(it.description)){
                    hashmapAnswersCheckBox.add(it.description)
                }
            } else {
                if(hashmapAnswersCheckBox.contains(it.description)){
                    hashmapAnswers.remove(id)
                }
            }
        }

        if(hashmapAnswers.isNotEmpty()){
            hashmapAnswers.put(id, hashmapAnswersCheckBox.toString())
        }else{
            hashmapAnswers.remove(id)
        }

        answerScore.forEach { t, answerModel ->
            totalScore += answerModel.value
        }

        Log.e("list", hashmapAnswers.toString())
        Log.e("score", totalScore.toString())

    }

}
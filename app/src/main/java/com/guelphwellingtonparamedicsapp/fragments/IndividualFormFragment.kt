package com.guelphwellingtonparamedicsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.adapters.IndividualFormAdapter
import com.guelphwellingtonparamedicsapp.adapters.IndividualFormAdapter.SelectedAnswer
import com.guelphwellingtonparamedicsapp.models.IndividualFormModel
import com.guelphwellingtonparamedicsapp.models.QuestionModel

class IndividualFormFragment : Fragment(), SelectedAnswer {

    lateinit var form : IndividualFormModel
    lateinit var formQuestionsRv : RecyclerView
    private var questions: ArrayList<QuestionModel> = ArrayList()
    private var adapter : IndividualFormAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_individual_form, container, false)
        formQuestionsRv = rootView.findViewById(R.id.formQuestionsRv)

        val bundle = this.arguments
        if (bundle != null) {
            if (bundle.containsKey("individualForm") && bundle.getSerializable("individualForm") != null) {
                form = bundle.getSerializable("individualForm") as IndividualFormModel
            }
            if(form != null){
               fillRecyclerView(form)
            }
        }

       return rootView
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
            adapter = IndividualFormAdapter(requireContext(), questions, this)
            var mLayoutManager = LinearLayoutManager(requireContext())
            mLayoutManager.orientation = LinearLayoutManager.VERTICAL
            formQuestionsRv.layoutManager = mLayoutManager
            formQuestionsRv.setHasFixedSize(true)
            formQuestionsRv.adapter = adapter
        }
    }

    override fun selected(answer: Boolean) {
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

}
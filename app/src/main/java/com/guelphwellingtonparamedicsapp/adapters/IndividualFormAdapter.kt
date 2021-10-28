package com.guelphwellingtonparamedicsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.enums.AnswersEnum
import com.guelphwellingtonparamedicsapp.models.InteractiveFormModel
import com.guelphwellingtonparamedicsapp.models.QuestionModel
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager


class IndividualFormAdapter() : RecyclerView.Adapter<IndividualFormAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var questions: ArrayList<QuestionModel>
    private lateinit var answers : ArrayList<String>

    constructor(context: Context, questions : ArrayList<QuestionModel>) : this() {
        this.context = context
        this.questions = questions
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questions[position]

        holder.questionTv.text = question.title

        when(question.type){
            AnswersEnum.TF.path -> {
                holder.booleanAnswer.visibility = View.VISIBLE
            }
            AnswersEnum.MULTIPLE_SELECT.path -> {
                holder.recyclerviewAnswers.visibility = View.VISIBLE
                answers = ArrayList()
                if(question.content!!.items != null){
                    for(a in question.content!!.items){
                        answers.add(a)
                    }

                    val adapter = AnswersAdapter(context, answers)
                    var mLayoutManager = LinearLayoutManager(context)
                    mLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    holder.recyclerviewAnswers.layoutManager = mLayoutManager
                    holder.recyclerviewAnswers.setHasFixedSize(true)
                    holder.recyclerviewAnswers.adapter = adapter
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        var questionTv : TextView = v.findViewById(R.id.questionTv)
        var booleanAnswer : RadioGroup = v.findViewById(R.id.booleanAnswer)
        var recyclerviewAnswers : RecyclerView = v.findViewById(R.id.recyclerviewAnswers)
    }
}
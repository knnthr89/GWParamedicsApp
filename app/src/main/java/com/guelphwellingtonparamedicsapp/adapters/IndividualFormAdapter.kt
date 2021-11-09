package com.guelphwellingtonparamedicsapp.adapters

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.enums.AnswersEnum
import com.guelphwellingtonparamedicsapp.models.QuestionModel
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.RadioButton

import android.widget.RadioGroup
import androidx.core.widget.addTextChangedListener
import java.util.HashMap
import java.util.TimerTask

import java.util.Timer

import android.text.Editable

import android.text.TextWatcher




class IndividualFormAdapter() : RecyclerView.Adapter<IndividualFormAdapter.ViewHolder>(), AnswersAdapter.SelectMultipleAnswer {
    private lateinit var context: Context
    private lateinit var questions: ArrayList<QuestionModel>
    private lateinit var answers : ArrayList<String>
    private var multipleAnswer  = HashMap<Int, ArrayList<String>>()
    private var listener : SelectedAnswer? = null
    private var saveAnswer : SaveAnswer? = null

    interface SaveAnswer {
        fun saveInArray(id : Int, answer : String, recordValue : Boolean)
    }
    interface SelectedAnswer {
        fun selected(answer : Boolean)
    }

    constructor(context: Context, questions : ArrayList<QuestionModel>, listener : SelectedAnswer, saveAnswer : SaveAnswer) : this() {
        this.context = context
        this.questions = questions
        this.listener = listener
        this.saveAnswer = saveAnswer
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

        if(!question.showIt){
            holder.itemView.visibility = View.GONE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(0,0)
        }else{
            holder.itemView.visibility = View.VISIBLE
            holder.itemView.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            holder.itemView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            val layoutParams = (holder.itemView?.layoutParams as? ViewGroup.MarginLayoutParams)
            layoutParams?.setMargins(40, 40, 40, 40)
            holder.itemView.layoutParams = layoutParams
        }

        holder.booleanAnswer.setOnCheckedChangeListener { group, checkedId ->
            if(holder.rbYes.id == checkedId){
                listener?.selected(true)
                saveAnswer?.saveInArray(id = question.id, answer = "true", recordValue = true)
            }else if(holder.rbNo.id == checkedId){
                listener?.selected(false)
                saveAnswer?.saveInArray(id = question.id, answer = "false", recordValue = true)
            }
        }

       holder.itemView.setOnClickListener {
           Toast.makeText(context, "id: ${question.id}", Toast.LENGTH_SHORT).show()
       }

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

                    val adapter = AnswersAdapter(context, answers, question.id, this)
                    var mLayoutManager = LinearLayoutManager(context)
                    mLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    holder.recyclerviewAnswers.layoutManager = mLayoutManager
                    holder.recyclerviewAnswers.setHasFixedSize(true)
                    holder.recyclerviewAnswers.adapter = adapter
                }
            }
            AnswersEnum.FILL_IN.path -> {
                holder.timeScoreEt.visibility = View.VISIBLE
                holder.descriptionTv.visibility = View.VISIBLE
                holder.descriptionTv.text = Html.fromHtml(question.content?.description)
                holder.timeScoreEt.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence, start: Int, count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence, start: Int, before: Int,
                        count: Int
                    ) {

                    }

                    override fun afterTextChanged(s: Editable) {
                        saveAnswer?.saveInArray(id = question.id, answer = s.toString(), recordValue = true)
                    }
                })
            }
            AnswersEnum.RATE.path -> {
                holder.rateLy.visibility = View.VISIBLE
                holder.descriptionRateTv.text = if(question.content?.description != null) question.content?.description else ""
            }
        }
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        val questionTv : TextView = v.findViewById(R.id.questionTv)
        val booleanAnswer : RadioGroup = v.findViewById(R.id.booleanAnswer)
        val recyclerviewAnswers : RecyclerView = v.findViewById(R.id.recyclerviewAnswers)
        val timeScoreEt : EditText = v.findViewById(R.id.timeScoreEt)
        val descriptionTv : TextView = v.findViewById(R.id.descriptionTv)
        val rbYes : RadioButton = v.findViewById(R.id.rbYes)
        var rbNo : RadioButton = v.findViewById(R.id.rbNo)
        val rateLy : LinearLayout = v.findViewById(R.id.rateLy)
        val descriptionRateTv : TextView = v.findViewById(R.id.descriptionRateTv)
    }

    override fun selected(answers: ArrayList<String>, type: AnswersEnum, id: Int) {
        saveAnswer?.saveInArray(id = id, answer = answers.toString(), recordValue = true)

    }
}
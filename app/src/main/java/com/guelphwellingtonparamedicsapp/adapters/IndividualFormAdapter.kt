package com.guelphwellingtonparamedicsapp.adapters

import android.animation.ObjectAnimator
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
import android.widget.RadioButton

import android.widget.RadioGroup
import android.text.Editable

import android.text.TextWatcher
import android.util.Log
import android.widget.LinearLayout
import org.jetbrains.anko.find
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.guelphwellingtonparamedicsapp.models.AnswerModel


class IndividualFormAdapter() : RecyclerView.Adapter<IndividualFormAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var questions: ArrayList<QuestionModel>
    private var listener: SelectedAnswer? = null
    private var saveAnswer: SaveAnswer? = null
    private var answersSelected: ArrayList<AnswerModel> = ArrayList()
    private var assessmentId: Int = 0
    private var saveAnswersList : SaveAnswerList? = null

    interface SaveAnswerList {
        fun saveAnswers(id : Int, answers : ArrayList<AnswerModel>)
    }

    interface SaveAnswer {
        fun saveInArray(id: Int, answer: AnswerModel, recordValue: Boolean)
    }

    interface SelectedAnswer {
        fun selected(answer: Boolean)
    }

    constructor(
        context: Context,
        questions: ArrayList<QuestionModel>,
        listener: SelectedAnswer,
        saveAnswer: SaveAnswer,
        assessmentId: Int,
        saveAnswersList : SaveAnswerList
    ) : this() {
        this.context = context
        this.questions = questions
        this.listener = listener
        this.saveAnswer = saveAnswer
        this.assessmentId = assessmentId
        this.saveAnswersList = saveAnswersList
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

        if (!question.showIt) {
            holder.itemView.visibility = View.GONE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
        } else {
            holder.itemView.visibility = View.VISIBLE
            holder.itemView.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            holder.itemView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            val layoutParams = (holder.itemView?.layoutParams as? ViewGroup.MarginLayoutParams)
            layoutParams?.setMargins(40, 40, 40, 40)
            holder.itemView.layoutParams = layoutParams
        }

        holder.booleanAnswer.setOnCheckedChangeListener { group, checkedId ->
            if (holder.rbYes.id == checkedId) {
                var answerModel = AnswerModel(description = "true", value = 0)
                if(question.id == 1){
                    listener?.selected(true)
                }
                saveAnswer?.saveInArray(id = question.id, answer = answerModel, recordValue = true)
            } else if (holder.rbNo.id == checkedId) {
                var answerModel = AnswerModel(description = "false", value = 0)
                if(question.id == 1) {
                    listener?.selected(false)
                }
                saveAnswer?.saveInArray(id = question.id, answer = answerModel, recordValue = false)
            }
        }

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
                val answer = AnswerModel(description = s.toString(), value = 0)
                saveAnswer?.saveInArray(
                    id = question.id,
                    answer = answer,
                    recordValue = true
                )
            }
        })

        holder.textValueEt.addTextChangedListener(object : TextWatcher {
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
                var answer = AnswerModel(description = s.toString(), value = 0)

                saveAnswer?.saveInArray(
                    id = question.id,
                    answer = answer,
                    recordValue = true
                )
            }
        })

        when (question.type) {
            AnswersEnum.TF.path -> {
                holder.booleanAnswer.visibility = View.VISIBLE
            }
            AnswersEnum.MULTIPLE_SELECT.path -> {
                holder.rateLy.visibility = View.VISIBLE
                holder.descriptionRateTv.visibility = View.VISIBLE
                holder.descriptionRateTv.text = question.content?.description
                holder.checkboxAnswers.visibility = View.VISIBLE
                holder.checkboxAnswers.removeAllViews()
                holder.radioAnswers.visibility = View.VISIBLE
                holder.radioAnswers.removeAllViews()

                val params: LinearLayout.LayoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                params.setMargins(10, 10, 10, 10)

                for (a in question.content!!.items) {
                    val v = View(context)
                    v.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        5
                    )
                    v.setBackgroundColor(context.getColor(R.color.button_gray))

                    when {
                        assessmentId == 2 -> {
                            holder.descriptionTv.visibility = View.GONE
                            holder.descriptionRateTv.visibility = View.GONE
                            val radioButton = RadioButton(context)
                            radioButton.text =
                                if (!a.description.isNullOrBlank()) a.description else ""
                            radioButton.layoutDirection = View.LAYOUT_DIRECTION_RTL
                            radioButton.layoutParams = params
                            holder.radioAnswers.addView(radioButton)
                            holder.radioAnswers.addView(v)

                            holder.radioAnswers.setOnCheckedChangeListener { radioGroup, i ->
                                var rb = radioGroup.find<RadioButton>(i)
                                var answerModel : AnswerModel? = null
                                question!!.content!!.items.forEach {
                                    if (it.description == rb.text.toString()){
                                        answerModel = it
                                    }
                                }
                                if(rb.isChecked){
                                    saveAnswer?.saveInArray(
                                        id = question.id, answer = answerModel!!,
                                        recordValue = true
                                    )
                                }
                            }
                        }
                        question.title != "Please select the assistive device" -> {
                            val checkbox = CheckBox(context)
                            checkbox.text =
                                if (!a.description.isNullOrBlank()) a.description else ""
                            checkbox.layoutDirection = View.LAYOUT_DIRECTION_RTL
                            checkbox.layoutParams = params
                            holder.checkboxAnswers.addView(checkbox)
                            holder.checkboxAnswers.addView(v)

                            checkbox.setOnCheckedChangeListener { compoundButton, b ->
                                var answer : AnswerModel? = null
                                question!!.content!!.items.forEach {
                                    if(it.description == checkbox.text){
                                        answer = it
                                    }
                                }
                                if (b) {
                                    if (answersSelected.isNotEmpty()) {
                                        if (!answersSelected.contains(answer)) {
                                            answersSelected.add(answer!!)
                                        }
                                    } else {
                                        answersSelected.add(answer!!)
                                    }
                                } else {
                                    if (answersSelected.isNotEmpty()) {
                                        answersSelected.remove(answer)
                                    }
                                }
                                saveAnswersList?.saveAnswers(id = question.id, answers = answersSelected)
                            }

                        }
                        else -> {
                            val rb = RadioButton(context)
                            rb.text = if (!a.description.isNullOrBlank()) a.description else ""

                            rb.layoutDirection = View.LAYOUT_DIRECTION_RTL
                            rb.layoutParams = params
                            holder.radioAnswers.addView(rb)
                            holder.radioAnswers.addView(v)

                            rb.setOnCheckedChangeListener { compoundButton, b ->
                                if (b) {
                                    if (rb.text.toString() == "Another assistive device") {
                                        holder.textValueEt.visibility = View.VISIBLE
                                        holder.textValueEt.setText("")
                                    } else {
                                        var answer : AnswerModel? = null
                                        question!!.content!!.items.forEach {
                                            if(it.description == rb.text.toString()){
                                                answer = it
                                            }
                                        }
                                        holder.textValueEt.visibility = View.GONE
                                        saveAnswer?.saveInArray(
                                            id = question.id, answer = answer!!,
                                            recordValue = true
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            AnswersEnum.FILL_IN.path -> {
                holder.timeScoreEt.visibility = View.VISIBLE
                if (!question.content?.description.isNullOrEmpty()) {
                    holder.descriptionTv.visibility = View.VISIBLE
                    holder.descriptionTv.text = Html.fromHtml(question.content?.description)
                } else {
                    holder.descriptionTv.visibility = View.GONE
                }
            }
            AnswersEnum.RATE.path -> {
                if (!question.content?.description.isNullOrBlank()) {
                    holder.rateLy.visibility = View.VISIBLE
                    holder.descriptionRateTv.visibility = View.VISIBLE
                    holder.descriptionRateTv.text = question.content?.description
                } else {
                    holder.rateLy.visibility = View.GONE
                    holder.descriptionRateTv.visibility = View.GONE
                }
                holder.radioAnswers.visibility = View.VISIBLE

                val params: LinearLayout.LayoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                params.setMargins(10, 10, 10, 10)

                if (question.content?.items != null) {
                    if (assessmentId == 4 && question!!.content!!.items.size == 0) {
                        holder.seekElements.visibility = View.VISIBLE
                        holder.seekContent.visibility = View.VISIBLE

                        holder.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                            var pval = 0
                            override fun onProgressChanged(
                                seekBar: SeekBar,
                                progress: Int,
                                fromUser: Boolean
                            ) {
                                pval = progress
                            }

                            override fun onStartTrackingTouch(seekBar: SeekBar) {
                                //write custom code to on start progress
                            }

                            override fun onStopTrackingTouch(seekBar: SeekBar) {
                                holder.seekNumberTv.text = "Level : $pval"
                                var answer = AnswerModel(description = "$pval", value = 0)
                                saveAnswer?.saveInArray(
                                    id = question.id,
                                    answer = answer,
                                    recordValue = true
                                )
                            }
                        })
                    } else {
                        for (q in question!!.content!!.items) {
                            val v = View(context)
                            v.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                5
                            )
                            v.setBackgroundColor(context.getColor(R.color.button_gray))
                            if (question!!.content!!.items[0] != q && question!!.content!!.items.size > 1) {
                                holder.radioAnswers.addView(v)
                            }

                            if (q.description.length != 1 || q.description.length != 2) {
                                var rb = RadioButton(context)
                                rb.text = "${q.description}"
                                rb.layoutDirection = View.LAYOUT_DIRECTION_RTL
                                rb.layoutParams = params
                                holder.radioAnswers.addView(rb)
                            }
                        }
                    }
                }

                holder.radioAnswers.setOnCheckedChangeListener { radioGroup, i ->
                    var rb = radioGroup.find<RadioButton>(i)
                    var pos: Int = -1
                    for (i in 0 until question!!.content!!.items.size) {
                        if (question!!.content!!.items[i].description === rb.text) {
                            pos = i
                        }
                    }
                    var answer = AnswerModel(description = pos.toString(), value = 0)
                    saveAnswer?.saveInArray(
                        id = question.id,
                        answer = answer,
                        recordValue = true
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val questionTv: TextView = v.findViewById(R.id.questionTv)
        val booleanAnswer: RadioGroup = v.findViewById(R.id.booleanAnswer)
        val textValueEt: EditText = v.findViewById(R.id.textValueEt)
        val timeScoreEt: EditText = v.findViewById(R.id.timeScoreEt)
        val descriptionTv: TextView = v.findViewById(R.id.descriptionTv)
        val rbYes: RadioButton = v.findViewById(R.id.rbYes)
        var rbNo: RadioButton = v.findViewById(R.id.rbNo)
        val rateLy: LinearLayout = v.findViewById(R.id.rateLy)
        val descriptionRateTv: TextView = v.findViewById(R.id.descriptionRateTv)
        val radioAnswers: RadioGroup = v.findViewById(R.id.radioAnswers)
        val checkboxAnswers: LinearLayout = v.findViewById(R.id.checkboxAnswers)
        val seekElements : LinearLayout = v.findViewById(R.id.seekElements)
        val seekContent : LinearLayout = v.findViewById(R.id.seekContent)
        val seekNumberTv : TextView = v.findViewById(R.id.seekNumberTv)
        val seekBar : SeekBar = v.findViewById(R.id.seekBar)
    }
}
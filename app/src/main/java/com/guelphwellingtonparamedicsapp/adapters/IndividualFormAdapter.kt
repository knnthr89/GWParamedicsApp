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
import java.util.HashMap

import android.text.Editable

import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import androidx.core.view.marginStart
import android.widget.LinearLayout
import androidx.core.text.isDigitsOnly
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.find
import org.w3c.dom.Text
import android.R.id





class IndividualFormAdapter() : RecyclerView.Adapter<IndividualFormAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var questions: ArrayList<QuestionModel>
    private var listener: SelectedAnswer? = null
    private var saveAnswer: SaveAnswer? = null
    private var answersSelected: ArrayList<String> = ArrayList()
    private var assessmentId : Int = 0

    interface SaveAnswer {
        fun saveInArray(id: Int, answer: String, recordValue: Boolean)
    }

    interface SelectedAnswer {
        fun selected(answer: Boolean)
    }

    constructor(
        context: Context,
        questions: ArrayList<QuestionModel>,
        listener: SelectedAnswer,
        saveAnswer: SaveAnswer,
        assessmentId : Int
    ) : this() {
        this.context = context
        this.questions = questions
        this.listener = listener
        this.saveAnswer = saveAnswer
        this.assessmentId = assessmentId
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
                listener?.selected(true)
                saveAnswer?.saveInArray(id = question.id, answer = "true", recordValue = true)
            } else if (holder.rbNo.id == checkedId) {
                listener?.selected(false)
                saveAnswer?.saveInArray(id = question.id, answer = "false", recordValue = true)
            }
        }

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

                    if (question.title != "Please select the assistive device") {
                        val checkbox = CheckBox(context)
                        checkbox.text = a
                        checkbox.layoutDirection = View.LAYOUT_DIRECTION_RTL
                        checkbox.layoutParams = params
                        holder.checkboxAnswers.addView(checkbox)
                        holder.checkboxAnswers.addView(v)

                        checkbox.setOnCheckedChangeListener { compoundButton, b ->
                            if (b) {
                                if (answersSelected.isNotEmpty()) {
                                    if (!answersSelected.contains(checkbox.text)) {
                                        answersSelected.add(checkbox.text.toString())
                                    }
                                } else {
                                    answersSelected.add(checkbox.text.toString())
                                }
                            } else {
                                if (answersSelected.isNotEmpty()) {
                                    answersSelected.remove(checkbox.text.toString())
                                }
                            }
                            saveAnswer?.saveInArray(
                                id = question.id, answer = answersSelected.toString(),
                                recordValue = true
                            )
                        }

                    } else {
                        val rb = RadioButton(context)
                        rb.text = a
                        rb.layoutDirection = View.LAYOUT_DIRECTION_RTL
                        rb.layoutParams = params
                        holder.radioAnswers.addView(rb)
                        holder.radioAnswers.addView(v)

                        rb.setOnCheckedChangeListener { compoundButton, b ->
                            if(b){
                                if(rb.text.toString() == "Another assistive device"){
                                    holder.textValueEt.visibility = View.VISIBLE
                                    holder.textValueEt.setText("")
                                }else{
                                    holder.textValueEt.visibility = View.GONE
                                    saveAnswer?.saveInArray(
                                        id = question.id, answer = rb.text.toString(),
                                        recordValue = true
                                    )
                                }
                            }
                        }
                    }

                }

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
                        saveAnswer?.saveInArray(
                            id = question.id,
                            answer = s.toString(),
                            recordValue = true
                        )
                    }
                })
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
                if (question.content?.description != null) {
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
                    for (q in question!!.content!!.items) {
                        val v = View(context)
                        v.layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            5
                        )
                        v.setBackgroundColor(context.getColor(R.color.button_gray))
                        if (question!!.content!!.items[0] != q) {
                            holder.radioAnswers.addView(v)
                        }
                        var rb = RadioButton(context)
                        if (q.length == 1 || q.length == 2) {
                            rb.text = "Level $q "
                        } else {
                            rb.text = "$q"
                        }

                        rb.layoutDirection = View.LAYOUT_DIRECTION_RTL
                        rb.layoutParams = params
                        holder.radioAnswers.addView(rb)
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
                        saveAnswer?.saveInArray(
                            id = question.id,
                            answer = s.toString(),
                            recordValue = true
                        )
                    }
                })

                holder.radioAnswers.setOnCheckedChangeListener { radioGroup, i ->
                    var rb = radioGroup.find<RadioButton>(i)
                    var pos : Int = -1
                    var level = rb.text.toString().substringAfter(" ")
                    for (i in 0 until question!!.content!!.items.size) {
                        if (question!!.content!!.items[i] === rb.text) {
                            pos = i
                        }
                    }

                    saveAnswer?.saveInArray(
                        id = question.id,
                        answer = if(assessmentId == 4) level.trim().replace(" ", "") else pos.toString(),
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
    }
}
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
import androidx.databinding.DataBindingUtil
import com.guelphwellingtonparamedicsapp.databinding.QuestionItemBinding
import com.guelphwellingtonparamedicsapp.models.AnswerModel


class IndividualFormAdapter(private var context: Context,
                            private var questions: ArrayList<QuestionModel>,
                            private var listener: SelectedAnswer,
                            private var saveAnswer: SaveAnswer,
                            private var assessmentId: Int,
                            private var saveAnswersList: SaveAnswerList
) : RecyclerView.Adapter<IndividualFormAdapter.ViewHolder>() {

    private var answersSelected: ArrayList<AnswerModel> = ArrayList()
    private lateinit var questionItemBinding : QuestionItemBinding

    interface SaveAnswerList {
        fun saveAnswers(id : Int, answers : ArrayList<AnswerModel>)
    }

    interface SaveAnswer {
        fun saveInArray(id: Int, answer: AnswerModel, recordValue: Boolean)
    }

    interface SelectedAnswer {
        fun selected(answer: Boolean)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        questionItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.question_item, parent, false)
        return ViewHolder(questionItemBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questions[position]

        questionItemBinding.questionTv.text = question.title

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

        questionItemBinding.booleanAnswer.setOnCheckedChangeListener { group, checkedId ->
            if ( questionItemBinding.rbYes.id == checkedId) {
                var answerModel = AnswerModel(description = "true", value = 0)
                if(question.id == 1){
                    listener?.selected(true)
                }
                saveAnswer?.saveInArray(id = question.id, answer = answerModel, recordValue = true)
            } else if ( questionItemBinding.rbNo.id == checkedId) {
                var answerModel = AnswerModel(description = "false", value = 0)
                if(question.id == 1) {
                    listener?.selected(false)
                }
                saveAnswer?.saveInArray(id = question.id, answer = answerModel, recordValue = false)
            }
        }

        questionItemBinding.timeScoreEt.addTextChangedListener(object : TextWatcher {
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

        questionItemBinding.textValueEt.addTextChangedListener(object : TextWatcher {
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
                questionItemBinding.booleanAnswer.visibility = View.VISIBLE
            }
            AnswersEnum.MULTIPLE_SELECT.path -> {
                questionItemBinding.rateLy.visibility = View.VISIBLE
                questionItemBinding.descriptionRateTv.visibility = View.VISIBLE
                questionItemBinding.descriptionRateTv.text = question.content?.description
                questionItemBinding.checkboxAnswers.visibility = View.VISIBLE
                questionItemBinding.checkboxAnswers.removeAllViews()
                questionItemBinding.radioAnswers.visibility = View.VISIBLE
                questionItemBinding.radioAnswers.removeAllViews()

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
                            questionItemBinding.descriptionTv.visibility = View.GONE
                            questionItemBinding.descriptionRateTv.visibility = View.GONE
                            val radioButton = RadioButton(context)
                            radioButton.text =
                                if (!a.description.isNullOrBlank()) a.description else ""
                            radioButton.layoutDirection = View.LAYOUT_DIRECTION_RTL
                            radioButton.layoutParams = params
                            questionItemBinding.radioAnswers.addView(radioButton)
                            questionItemBinding.radioAnswers.addView(v)

                            questionItemBinding.radioAnswers.setOnCheckedChangeListener { radioGroup, i ->
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
                            questionItemBinding.checkboxAnswers.addView(checkbox)
                            questionItemBinding.checkboxAnswers.addView(v)

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
                            questionItemBinding.radioAnswers.addView(rb)
                            questionItemBinding.radioAnswers.addView(v)

                            rb.setOnCheckedChangeListener { compoundButton, b ->
                                if (b) {
                                    if (rb.text.toString() == "Another assistive device") {
                                        questionItemBinding.textValueEt.visibility = View.VISIBLE
                                        questionItemBinding.textValueEt.setText("")
                                    } else {
                                        var answer : AnswerModel? = null
                                        question!!.content!!.items.forEach {
                                            if(it.description == rb.text.toString()){
                                                answer = it
                                            }
                                        }
                                        questionItemBinding.textValueEt.visibility = View.GONE
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
                questionItemBinding.timeScoreEt.visibility = View.VISIBLE
                if (!question.content?.description.isNullOrEmpty()) {
                    questionItemBinding.descriptionTv.visibility = View.VISIBLE
                    questionItemBinding.descriptionTv.text = Html.fromHtml(question.content?.description)
                } else {
                    questionItemBinding.descriptionTv.visibility = View.GONE
                }
            }
            AnswersEnum.RATE.path -> {
                if (!question.content?.description.isNullOrBlank()) {
                    questionItemBinding.rateLy.visibility = View.VISIBLE
                    questionItemBinding.descriptionRateTv.visibility = View.VISIBLE
                    questionItemBinding.descriptionRateTv.text = question.content?.description
                } else {
                    questionItemBinding.rateLy.visibility = View.GONE
                    questionItemBinding.descriptionRateTv.visibility = View.GONE
                }
                questionItemBinding.radioAnswers.visibility = View.VISIBLE

                val params: LinearLayout.LayoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                params.setMargins(10, 10, 10, 10)

                if (question.content?.items != null) {
                    if (assessmentId == 4 && question!!.content!!.items.size == 0) {
                        questionItemBinding.seekElements.visibility = View.VISIBLE
                        questionItemBinding.seekContent.visibility = View.VISIBLE

                        questionItemBinding.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
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
                                questionItemBinding.seekNumberTv.text = "Level : $pval"
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
                                questionItemBinding.radioAnswers.addView(v)
                            }

                            if (q.description.length != 1 || q.description.length != 2) {
                                var rb = RadioButton(context)
                                rb.text = "${q.description}"
                                rb.layoutDirection = View.LAYOUT_DIRECTION_RTL
                                rb.layoutParams = params
                                questionItemBinding.radioAnswers.addView(rb)
                            }
                        }
                    }
                }

                questionItemBinding.radioAnswers.setOnCheckedChangeListener { radioGroup, i ->
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

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {}
}
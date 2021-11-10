package com.guelphwellingtonparamedicsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.enums.AnswersEnum

class CheckboxAnswersAdapter() : RecyclerView.Adapter<CheckboxAnswersAdapter.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var answers: ArrayList<String>
    private var selectMultipleAnswer: SelectMultipleAnswer? = null
    private var questionId: Int = 0
    private var answersSelected: ArrayList<String> = ArrayList()

    interface SelectMultipleAnswer {
        fun selected(
            answers: ArrayList<String>,
            type: AnswersEnum = AnswersEnum.MULTIPLE_SELECT,
            id: Int
        )
    }

    constructor(
        context: Context,
        answers: ArrayList<String>,
        id: Int,
        selectMultipleAnswer: SelectMultipleAnswer
    ) : this() {
        this.context = context
        this.answers = answers
        this.selectMultipleAnswer = selectMultipleAnswer
        this.questionId = id
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.checkbox_answer_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val answer = answers[position]

        holder.checkboxItem.text = answer.capitalize()

        if (answers.size == position + 1) {
            holder.lineView.visibility = View.GONE
            holder.relative.updatePadding(0, 0, 0, 5)
        }

        holder.checkboxItem.setOnClickListener {
            if (holder.checkboxItem.isChecked) {
                if (answersSelected.isNotEmpty()) {
                    if (!answersSelected.contains(answer)) {
                        answersSelected.add(answer)
                    }
                } else {
                    answersSelected.add(answer)
                }
            } else {
                if (answersSelected.isNotEmpty()) {
                    answersSelected.remove(answer)
                }
            }
            selectMultipleAnswer?.selected(answers = answersSelected, id = questionId)
        }

    }

    override fun getItemCount(): Int {
        return answers.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val checkboxItem: CheckBox = v.findViewById(R.id.checkboxItem)
        val lineView: View = v.findViewById(R.id.lineView)
        var relative: RelativeLayout = v.findViewById(R.id.relative)
    }
}
package com.guelphwellingtonparamedicsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import androidx.core.view.setPadding
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import com.guelphwellingtonparamedicsapp.R

class AnswersAdapter() : RecyclerView.Adapter<AnswersAdapter.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var answers: ArrayList<String>

    constructor(context: Context, answers : ArrayList<String>) : this() {
        this.context = context
        this.answers = answers
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.answer_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val answer = answers[position]

        holder.checkboxItem.text = answer.capitalize()

        if(answers.size == position + 1){
            holder.lineView.visibility = View.GONE
            holder.relative.updatePadding(0,0,0,5)
        }

    }

    override fun getItemCount(): Int {
        return answers.size
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        val checkboxItem : CheckBox = v.findViewById(R.id.checkboxItem)
        val lineView : View = v.findViewById(R.id.lineView)
        var relative : RelativeLayout = v.findViewById(R.id.relative)
    }
}
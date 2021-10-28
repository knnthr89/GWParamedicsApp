package com.guelphwellingtonparamedicsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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

        holder.checkboxItem.text = answer

    }

    override fun getItemCount(): Int {
        return answers.size
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        var checkboxItem : CheckBox = v.findViewById(R.id.checkboxItem)
    }
}
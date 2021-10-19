package com.guelphwellingtonparamedicsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.models.InteractiveFormModel

class InteractiveFormsAdapter() : RecyclerView.Adapter<InteractiveFormsAdapter.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var interactiveFormsList: ArrayList<InteractiveFormModel>

    constructor(context: Context, interactiveFormsList : ArrayList<InteractiveFormModel>) : this() {
        this.context = context
        this.interactiveFormsList = interactiveFormsList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.interactive_form_item, parent, false)
       return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val interactiveForm = interactiveFormsList[position]

        holder.titleTextView.text = interactiveForm.title
    }

    override fun getItemCount(): Int {
        return interactiveFormsList.size
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
       var titleTextView : TextView = v.findViewById(R.id.titleTextView)
    }
}
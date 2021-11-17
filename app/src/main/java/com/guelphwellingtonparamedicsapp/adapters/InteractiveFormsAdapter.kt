package com.guelphwellingtonparamedicsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.manager.AssessmentsManager
import com.guelphwellingtonparamedicsapp.models.InteractiveFormModel

class InteractiveFormsAdapter(private var context: Context,
                              private var interactiveFormsList: ArrayList<InteractiveFormModel>,
                              private var listener: SelectedInteractiveForm
) : RecyclerView.Adapter<InteractiveFormsAdapter.ViewHolder>() {

    interface SelectedInteractiveForm {
        fun selected(id: Int)
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

        holder.itemView.setOnClickListener {
            listener?.selected(interactiveForm.id)
        }

        holder.imageButton.setOnClickListener {
            listener?.selected(interactiveForm.id)
        }


    }

    override fun getItemCount(): Int {
        return interactiveFormsList.size
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
       var titleTextView : TextView = v.findViewById(R.id.titleTextView)
        var imageButton : ImageButton = v.findViewById(R.id.imageButton)
    }
}
package com.guelphwellingtonparamedicsapp.adapters

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.databinding.InteractiveFormItemBinding
import com.guelphwellingtonparamedicsapp.manager.AssessmentsManager
import com.guelphwellingtonparamedicsapp.models.InteractiveFormModel
import com.guelphwellingtonparamedicsapp.utils.Utils

class InteractiveFormsAdapter(private var context: Context,
                              private var interactiveFormsList: List<InteractiveFormModel>,
                              private var listener: SelectedInteractiveForm
) : RecyclerView.Adapter<InteractiveFormsAdapter.ViewHolder>() {

    private lateinit var interactiveFormItemBinding: InteractiveFormItemBinding
    private val application : Application = context.applicationContext as Application

    interface SelectedInteractiveForm {
        fun selected(id: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

       interactiveFormItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.interactive_form_item, parent, false)

       return ViewHolder(interactiveFormItemBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val interactiveForm = interactiveFormsList[position]

        interactiveFormItemBinding.titleTextView.text = interactiveForm.title

        holder.itemView.setOnClickListener {
            Utils.vibrate(application = application)
            listener?.selected(interactiveForm.id)
        }

        interactiveFormItemBinding.imageButton.setOnClickListener {
            listener?.selected(interactiveForm.id)
        }
    }

    override fun getItemCount(): Int {
        return interactiveFormsList.size
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v){}
}
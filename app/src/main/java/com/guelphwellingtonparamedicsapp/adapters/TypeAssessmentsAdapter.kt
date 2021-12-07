package com.guelphwellingtonparamedicsapp.adapters

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.activities.BottomNavigationActivity
import com.guelphwellingtonparamedicsapp.databinding.ResourceItemBinding
import com.guelphwellingtonparamedicsapp.fragments.AssessmentsFragment
import com.guelphwellingtonparamedicsapp.fragments.ListContactsFragment
import com.guelphwellingtonparamedicsapp.models.TypeAssessmentModel
import com.guelphwellingtonparamedicsapp.utils.Utils
import com.squareup.picasso.Picasso

class TypeAssessmentsAdapter(private var context: Context,
                             private var typeAssessmentList : ArrayList<TypeAssessmentModel>
) : RecyclerView.Adapter<TypeAssessmentsAdapter.ViewHolder>() {

    private var activity = context as Activity

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.area_item, parent
            , false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resource = typeAssessmentList[position]

        holder.nameTv.text = resource.name

        holder.itemView.setOnClickListener {
            (activity as BottomNavigationActivity).showFragment(AssessmentsFragment())
        }
    }

    override fun getItemCount(): Int {
        return typeAssessmentList.size
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        val nameTv : TextView = v.findViewById(R.id.titleTextView)
    }
}
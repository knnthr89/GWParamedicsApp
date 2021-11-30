package com.guelphwellingtonparamedicsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.activities.BottomNavigationActivity
import com.guelphwellingtonparamedicsapp.fragments.ParamedicDetailsFragment
import com.guelphwellingtonparamedicsapp.models.ParamedicModel

class ParamedicsAdapter(private var context: Context,
                        private var paramedicsList : ArrayList<ParamedicModel>
) : RecyclerView.Adapter<ParamedicsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParamedicsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.paramedic_item, parent
            , false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val paramedic = paramedicsList[position]

        holder.titleTextView.text = paramedic.name

        holder.itemView.setOnClickListener {
            (context as BottomNavigationActivity).showFragment(ParamedicDetailsFragment(), model = paramedic)
        }

    }

    override fun getItemCount(): Int {
        return  paramedicsList.size
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        var titleTextView : TextView = v.findViewById(R.id.titleTextView)
    }

}
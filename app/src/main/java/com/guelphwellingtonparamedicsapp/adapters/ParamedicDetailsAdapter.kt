package com.guelphwellingtonparamedicsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guelphwellingtonparamedicsapp.R

class ParamedicDetailsAdapter(private var context: Context,
                              private var services : ArrayList<String>
) : RecyclerView.Adapter<ParamedicDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParamedicDetailsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.service_item, parent
            , false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = services[position]

        holder.titleTv.text = service

    }

    override fun getItemCount(): Int {
        return  services.size
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        val titleTv : TextView = v.findViewById(R.id.serviceTv)
    }
}
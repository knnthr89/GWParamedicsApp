package com.guelphwellingtonparamedicsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.databinding.AreaItemBinding
import com.guelphwellingtonparamedicsapp.databinding.ContactItemBinding
import com.guelphwellingtonparamedicsapp.models.AreaModel

class AreasAdapter(var context : Context, var areasList : List<AreaModel>) : RecyclerView.Adapter<AreasAdapter.ViewHolder>(){

    private lateinit var areaItemBinding : AreaItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreasAdapter.ViewHolder {
        areaItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.area_item, parent, false)
        return ViewHolder(areaItemBinding.root)
    }

    override fun onBindViewHolder(holder: AreasAdapter.ViewHolder, position: Int) {
        val contact = areasList[position]

        areaItemBinding.titleTextView.text = contact.name

    }

    override fun getItemCount(): Int {
        return  areasList.size
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v)

}
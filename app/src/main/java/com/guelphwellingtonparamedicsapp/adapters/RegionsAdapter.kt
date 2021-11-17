package com.guelphwellingtonparamedicsapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.models.RegionModel
import com.guelphwellingtonparamedicsapp.models.ResourceModel

class RegionsAdapter (context: Context, regionList: ArrayList<RegionModel>) : RecyclerView.Adapter<RegionsAdapter.ViewHolder>(){

    private var context: Context = context
    private var regionList : ArrayList<RegionModel> = regionList

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.regions_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resource = regionList[position]

        holder.titleTv.text = resource.name

        showResources(resourcesList = resource.resources, holder)

    }

    override fun getItemCount(): Int {
        return regionList.size
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        val titleTv : TextView = v.findViewById(R.id.titleTv)
        val resourcesRv : RecyclerView = v.findViewById(R.id.resourcesRv)
    }

    private fun showResources(resourcesList : ArrayList<ResourceModel>, holder : ViewHolder){
        var adapter = ResourcesAdapter(context, resourcesList)
        var mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        holder.resourcesRv.layoutManager = mLayoutManager
        holder.resourcesRv.setHasFixedSize(true)
        holder.resourcesRv.adapter = adapter
    }
}
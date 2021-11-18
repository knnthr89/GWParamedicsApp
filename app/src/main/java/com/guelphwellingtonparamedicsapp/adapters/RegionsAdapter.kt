package com.guelphwellingtonparamedicsapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.databinding.RegionsItemBinding
import com.guelphwellingtonparamedicsapp.models.RegionModel
import com.guelphwellingtonparamedicsapp.models.ResourceModel

class RegionsAdapter (private var context: Context, private var regionList: List<RegionModel>) : RecyclerView.Adapter<RegionsAdapter.ViewHolder>(){

    private lateinit var regionsItemBinding: RegionsItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        regionsItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.regions_item, parent, false)

        return ViewHolder(regionsItemBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resource = regionList[position]

        regionsItemBinding.titleTv.text = resource.name

        showResources(resourcesList = resource.resources, holder)

    }

    override fun getItemCount(): Int {
        return regionList.size
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v){}

    private fun showResources(resourcesList : ArrayList<ResourceModel>, holder : ViewHolder){
        var adapter = ResourcesAdapter(context, resourcesList)
        var mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        regionsItemBinding.resourcesRv.layoutManager = mLayoutManager
        regionsItemBinding.resourcesRv.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        regionsItemBinding.resourcesRv.setHasFixedSize(true)
        regionsItemBinding.resourcesRv.adapter = adapter
    }
}
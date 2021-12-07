package com.guelphwellingtonparamedicsapp.adapters

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.activities.BottomNavigationActivity
import com.guelphwellingtonparamedicsapp.fragments.IndividualFormFragment
import com.guelphwellingtonparamedicsapp.models.IndividualFormModel
import com.guelphwellingtonparamedicsapp.models.ResourceModel
import com.squareup.picasso.Picasso
import android.app.Activity
import android.app.Application
import androidx.databinding.DataBindingUtil
import com.guelphwellingtonparamedicsapp.databinding.ResourceItemBinding
import com.guelphwellingtonparamedicsapp.fragments.ListContactsFragment
import com.guelphwellingtonparamedicsapp.utils.Utils


class ResourcesAdapter(private var context: Context,
                       private var resourcesList: ArrayList<ResourceModel>
) : RecyclerView.Adapter<ResourcesAdapter.ViewHolder>() {

    private var activity = context as Activity
    private lateinit var resourceItemBinding : ResourceItemBinding
    private val application : Application = context.applicationContext as Application

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        resourceItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.resource_item, parent, false)
        return ViewHolder(resourceItemBinding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resource = resourcesList[position]

        Picasso
            .get()
            .load(resource.logo)
            .into(resourceItemBinding.resourceImage)

        resourceItemBinding.resourceNameTv.text = resource.location

        holder.itemView.setOnClickListener {
            Utils.vibrate(application = application)
            if (resource.contacts != null){
                (activity as BottomNavigationActivity).showFragment(ListContactsFragment(), model = resource.contacts)
            }else{
                Toast.makeText(context, "null", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return resourcesList.size
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v){}
}
package com.guelphwellingtonparamedicsapp.adapters

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.activities.BottomNavigationActivity
import com.guelphwellingtonparamedicsapp.databinding.AreaItemBinding
import com.guelphwellingtonparamedicsapp.fragments.ContactsListFragment
import com.guelphwellingtonparamedicsapp.fragments.IndividualFormFragment
import com.guelphwellingtonparamedicsapp.manager.ContactsManager
import com.guelphwellingtonparamedicsapp.manager.ContactsManager.ContactsGroupsListener
import com.guelphwellingtonparamedicsapp.models.DepartmentModel
import com.guelphwellingtonparamedicsapp.utils.Utils

class AreasAdapter(var context : Context, var areasList : List<DepartmentModel>) : RecyclerView.Adapter<AreasAdapter.ViewHolder>() {

    private lateinit var areaItemBinding : AreaItemBinding
    private val application : Application = context.applicationContext as Application

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreasAdapter.ViewHolder {
        areaItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.area_item, parent, false)
        return ViewHolder(areaItemBinding.root)
    }

    override fun onBindViewHolder(holder: AreasAdapter.ViewHolder, position: Int) {
        val area = areasList[position]

        areaItemBinding.titleTextView.text = area.name

        holder.itemView.setOnClickListener {
            Utils.vibrate(application = application)
            (context as BottomNavigationActivity).showFragment(ContactsListFragment(), model = area.contacts)
        }

    }

    override fun getItemCount(): Int {
        return  areasList.size
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v)
}
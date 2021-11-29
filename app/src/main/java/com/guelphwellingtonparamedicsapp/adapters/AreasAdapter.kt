package com.guelphwellingtonparamedicsapp.adapters

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
import com.guelphwellingtonparamedicsapp.models.AreaModel

class AreasAdapter(var context : Context, var areasList : List<AreaModel>) : RecyclerView.Adapter<AreasAdapter.ViewHolder>(),
    ContactsGroupsListener {

    private lateinit var areaItemBinding : AreaItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreasAdapter.ViewHolder {
        areaItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.area_item, parent, false)
        return ViewHolder(areaItemBinding.root)
    }

    override fun onBindViewHolder(holder: AreasAdapter.ViewHolder, position: Int) {
        val contact = areasList[position]

        areaItemBinding.titleTextView.text = contact.name

        holder.itemView.setOnClickListener {
            getList(contact.id)
        }

    }

    private fun getList(id : Int){
       ContactsManager.getInstance(context = context).setContactsGroupsListener(this)
       ContactsManager.getInstance(context = context).getIndividualArea(id = id)
    }

    override fun getItemCount(): Int {
        return  areasList.size
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v)

    override fun onContactsGroupsSuccess(areasList: LiveData<List<AreaModel>>) {
        (context as BottomNavigationActivity).showFragment(ContactsListFragment())
    }

    override fun onContactsGroupsFail(message: String, code: Int?) {
        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
    }

}
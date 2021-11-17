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
import com.guelphwellingtonparamedicsapp.fragments.ListContactsFragment


class ResourcesAdapter(private var context: Context,
                       private var resourcesList: ArrayList<ResourceModel>
) : RecyclerView.Adapter<ResourcesAdapter.ViewHolder>() {

    var activity = context as Activity

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.resource_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resource = resourcesList[position]

        Picasso
            .get()
            .load(resource.logo)
            .into(holder.image)

        holder.title.text = resource.location

        holder.itemView.setOnClickListener {
            if (resource.contacts != null){
                (activity as BottomNavigationActivity).showFragment(ListContactsFragment(), model = resource.contacts)
            }else{
                Toast.makeText(context, "nulllll", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return resourcesList.size
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        val image : ImageView = v.findViewById(R.id.resourceImage)
        val title : TextView = v.findViewById(R.id.resourceNameTv)
    }
}
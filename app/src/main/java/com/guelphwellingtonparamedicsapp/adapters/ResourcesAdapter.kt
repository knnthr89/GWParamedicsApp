package com.guelphwellingtonparamedicsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.models.ResourceModel
import com.squareup.picasso.Picasso

class ResourcesAdapter(context : Context, resourcesList : ArrayList<ResourceModel>) : RecyclerView.Adapter<ResourcesAdapter.ViewHolder>() {

    private var context: Context = context
    private var resourcesList : ArrayList<ResourceModel> = resourcesList

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
            Toast.makeText(context, "${resource.websiteUrl}", Toast.LENGTH_SHORT).show()
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
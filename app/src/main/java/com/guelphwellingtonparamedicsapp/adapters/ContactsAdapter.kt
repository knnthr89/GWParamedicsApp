package com.guelphwellingtonparamedicsapp.adapters

import android.content.Context
import android.media.TimedText
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.guelphwellingtonparamedicsapp.models.ContactModel
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.databinding.ContactItemBinding
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import org.w3c.dom.Text


class ContactsAdapter(private var context: Context,
                      private var contactsList: ArrayList<ContactModel>
) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.contact_item, parent
            , false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ContactsAdapter.ViewHolder, position: Int) {
        val contact = contactsList[position]

        holder.nameTv.text = if(contact.name.isNullOrEmpty()) "" else contact.name
        holder.positionTv.text = if(contact.position.isNullOrEmpty()) "" else contact.position
        holder.emailTv.text = if(contact.email.isNullOrEmpty()) "" else contact.email
        holder.phoneTv.text = if(contact.phone.isNullOrEmpty()) "" else contact.phone
        holder.extensionTv.text = if(contact.ext.isNullOrEmpty()) "" else contact.ext
        holder.faxTv.text = if(contact.fax.isNullOrEmpty()) "" else contact.fax

    }

    override fun getItemCount(): Int {
       return  contactsList.size
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        val nameTv : TextView = v.findViewById(R.id.nameTv)
        val positionTv : TextView = v.findViewById(R.id.positionTv)
        val emailTv : TextView = v.findViewById(R.id.emailTv)
        val phoneTv : TextView = v.findViewById(R.id.phoneTv)
        val extensionTv : TextView = v.findViewById(R.id.extensionTv)
        val faxTv : TextView = v.findViewById(R.id.faxTv)
    }
}
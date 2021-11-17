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

class ContactsAdapter(private var context: Context,
                      private var contactsList: ArrayList<ContactModel>
) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>(){

    private lateinit var contactItemBinding : ContactItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ViewHolder {
        contactItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.contact_item, parent, false)
        return ViewHolder(contactItemBinding.root)
    }

    override fun onBindViewHolder(holder: ContactsAdapter.ViewHolder, position: Int) {
        val contact = contactsList[position]

        contactItemBinding.nameTv.text = if(contact.name.isNullOrEmpty()) "" else contact.name
        contactItemBinding.positionTv.text = if(contact.position.isNullOrEmpty()) "" else contact.position
        contactItemBinding.emailTv.text = if(contact.email.isNullOrEmpty()) "" else contact.email
        contactItemBinding.phoneTv.text = if(contact.phone.isNullOrEmpty()) "" else contact.phone
        contactItemBinding.extensionTv.text = if(contact.ext.isNullOrEmpty()) "" else contact.ext
        contactItemBinding.faxTv.text = if(contact.fax.isNullOrEmpty()) "" else contact.fax

    }

    override fun getItemCount(): Int {
       return  contactsList.size
    }

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v)
}
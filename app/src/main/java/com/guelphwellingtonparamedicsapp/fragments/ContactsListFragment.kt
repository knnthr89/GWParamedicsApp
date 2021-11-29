package com.guelphwellingtonparamedicsapp.fragments

import android.database.DatabaseUtils
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.databinding.FragmentContactsListBinding


class ContactsListFragment : Fragment() {

    lateinit var contactsListBinding : FragmentContactsListBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        contactsListBinding = DataBindingUtil.inflate(LayoutInflater.from(container?.context), R.layout.fragment_contacts_list, container, false)

        return contactsListBinding.root
    }

}
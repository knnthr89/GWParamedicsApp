package com.guelphwellingtonparamedicsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.manager.ContactsManager
import com.guelphwellingtonparamedicsapp.manager.ContactsManager.ContactsGroupsListener

class ContactsFragment : Fragment(), ContactsGroupsListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_contacts, container, false)
        ContactsManager.getInstance(requireContext()).setContactsGroupsListener(this)
        ContactsManager.getInstance(requireContext()).getContactsGroups()
        return rootView
    }

    override fun onContactsGroupsSuccess(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onContactsGroupsFail(message: String, code: Int?) {
        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
    }
}
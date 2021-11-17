package com.guelphwellingtonparamedicsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.databinding.FragmentListContactsBinding
import com.guelphwellingtonparamedicsapp.models.ContactModel

class ListContactsFragment : Fragment() {

    private var contactList: ArrayList<ContactModel>? = null
    private lateinit var fragmentListContactsBinding : FragmentListContactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            contactList = it.getSerializable("model") as ArrayList<ContactModel>
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentListContactsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_contacts, container, false)

        return fragmentListContactsBinding.root
    }
}
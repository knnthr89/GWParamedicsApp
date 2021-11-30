package com.guelphwellingtonparamedicsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.adapters.ParamedicsAdapter
import com.guelphwellingtonparamedicsapp.databinding.FragmentContactsListBinding
import com.guelphwellingtonparamedicsapp.models.ParamedicModel


class ContactsListFragment : Fragment(), View.OnClickListener {

    lateinit var contactsListBinding : FragmentContactsListBinding
    lateinit var contacts : ArrayList<ParamedicModel>
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments.let {
            contacts = it?.getSerializable("model") as ArrayList<ParamedicModel>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        contactsListBinding = DataBindingUtil.inflate(LayoutInflater.from(container?.context), R.layout.fragment_contacts_list, container, false)

        contactsListBinding.back.setOnClickListener(this)


       var adapter = ParamedicsAdapter(requireContext(), contacts)
        var mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        contactsListBinding.contactsRv.layoutManager = mLayoutManager
        contactsListBinding.contactsRv.setHasFixedSize(true)
        contactsListBinding.contactsRv.adapter = adapter

        return contactsListBinding.root
    }

    override fun onClick(v: View?) {
        when(v){
            contactsListBinding.back -> backButton()
        }


    }

    private fun backButton(){
        fragmentManager?.popBackStack()
    }

}
package com.guelphwellingtonparamedicsapp.fragments

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.activities.BottomNavigationActivity
import com.guelphwellingtonparamedicsapp.adapters.ContactsAdapter
import com.guelphwellingtonparamedicsapp.databinding.FragmentListContactsBinding
import com.guelphwellingtonparamedicsapp.models.ContactModel

class ListContactsFragment : Fragment(), View.OnClickListener {

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

        fragmentListContactsBinding.back.setOnClickListener(this)

        fragmentListContactsBinding.websiteUrlTv.text = "https://www.ontariocpsecretariat.ca"

        fragmentListContactsBinding.websiteUrlTv.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        fragmentListContactsBinding.websiteUrlTv.setOnClickListener {
            (activity as BottomNavigationActivity).showFragment(WebViewFragment(), url = fragmentListContactsBinding.websiteUrlTv.text.toString())
        }

        if(contactList?.size!! > 0){
            var adapter = ContactsAdapter(context = requireContext(), contactList!!)
            var mLayoutManager = LinearLayoutManager(requireContext())
            mLayoutManager.orientation = LinearLayoutManager.VERTICAL
            fragmentListContactsBinding.contactsList.layoutManager = mLayoutManager
            fragmentListContactsBinding.contactsList.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            fragmentListContactsBinding.contactsList.setHasFixedSize(true)
            fragmentListContactsBinding.contactsList.adapter = adapter
        }

        return fragmentListContactsBinding.root
    }

    override fun onClick(v : View?) {
       when(v){
           fragmentListContactsBinding.back -> backButton()
       }
    }

    private fun backButton(){
        fragmentManager?.popBackStack()
    }
}
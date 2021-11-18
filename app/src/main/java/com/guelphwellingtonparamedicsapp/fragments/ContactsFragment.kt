package com.guelphwellingtonparamedicsapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.adapters.AreasAdapter
import com.guelphwellingtonparamedicsapp.adapters.RegionsAdapter
import com.guelphwellingtonparamedicsapp.databinding.FragmentContactsBinding
import com.guelphwellingtonparamedicsapp.factories.AreaModelFactory
import com.guelphwellingtonparamedicsapp.manager.ContactsManager
import com.guelphwellingtonparamedicsapp.manager.ContactsManager.ContactsGroupsListener
import com.guelphwellingtonparamedicsapp.models.AreaModel
import com.guelphwellingtonparamedicsapp.viewModels.AreasViewModel

class ContactsFragment : Fragment() {

    private lateinit var fragmentContactsBinding : FragmentContactsBinding
    private var mAreasViewModel : AreasViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentContactsBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_contacts, container, false)

        return fragmentContactsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAreasViewModel =
            ViewModelProvider(this, AreaModelFactory(requireActivity().application)).get(AreasViewModel::class.java)

        mAreasViewModel?.results?.observe(viewLifecycleOwner, { area ->
            var adapter = AreasAdapter(requireContext(), area)
            var mLayoutManager = LinearLayoutManager(requireContext())
            mLayoutManager.orientation = LinearLayoutManager.VERTICAL
            fragmentContactsBinding.areasRv.layoutManager = mLayoutManager
            fragmentContactsBinding.areasRv.setHasFixedSize(true)
            fragmentContactsBinding.areasRv.adapter = adapter
        })
    }
}
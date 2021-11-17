package com.guelphwellingtonparamedicsapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.adapters.RegionsAdapter
import com.guelphwellingtonparamedicsapp.databinding.FragmentResourcesBinding
import com.guelphwellingtonparamedicsapp.manager.ResourcesManager
import com.guelphwellingtonparamedicsapp.manager.ResourcesManager.GetResourcesListener
import com.guelphwellingtonparamedicsapp.models.RegionModel
import androidx.recyclerview.widget.DividerItemDecoration


class ResourcesFragment : Fragment(), GetResourcesListener {

    private lateinit var fragmentResourcesBinding: FragmentResourcesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentResourcesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_resources, container, false)

        ResourcesManager.getInstance(context = requireContext()).setAllResourcesListener(this)
        ResourcesManager.getInstance(context = requireContext()).getAllResources()

        return fragmentResourcesBinding.root

    }

    override fun onResourcesSuccess(regionModelList: ArrayList<RegionModel>) {
        if (regionModelList.isNotEmpty()) {
            var adapter = RegionsAdapter(requireContext(), regionModelList)
            var mLayoutManager = LinearLayoutManager(requireContext())
            mLayoutManager.orientation = LinearLayoutManager.VERTICAL
            fragmentResourcesBinding.resourcesRv.layoutManager = mLayoutManager
            fragmentResourcesBinding.resourcesRv.setHasFixedSize(true)
            fragmentResourcesBinding.resourcesRv.adapter = adapter
        }
    }

    override fun onResourcesFail(message: String, code: Int?) {
        Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
    }
}
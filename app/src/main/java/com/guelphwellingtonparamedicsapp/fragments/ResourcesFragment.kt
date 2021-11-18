package com.guelphwellingtonparamedicsapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.adapters.RegionsAdapter
import com.guelphwellingtonparamedicsapp.databinding.FragmentResourcesBinding
import com.guelphwellingtonparamedicsapp.manager.ResourcesManager
import com.guelphwellingtonparamedicsapp.manager.ResourcesManager.GetResourcesListener
import com.guelphwellingtonparamedicsapp.models.RegionModel
import androidx.recyclerview.widget.DividerItemDecoration
import com.guelphwellingtonparamedicsapp.factories.RegionModelFactory
import com.guelphwellingtonparamedicsapp.viewModels.RegionsViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync


class ResourcesFragment : Fragment() {

    private lateinit var fragmentResourcesBinding: FragmentResourcesBinding
    private var mRegionsViewModel: RegionsViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentResourcesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_resources, container, false)

        return fragmentResourcesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRegionsViewModel =
            ViewModelProvider(this, RegionModelFactory(requireActivity().application)).get(
                RegionsViewModel::class.java
            )

        lifecycleScope.launch {
            mRegionsViewModel?.getAllRegionsAsync(requireContext())
                ?.observe(viewLifecycleOwner, { regions ->
                    var adapter = RegionsAdapter(requireContext(), regions)
                    var mLayoutManager = LinearLayoutManager(requireContext())
                    mLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    fragmentResourcesBinding.resourcesRv.layoutManager = mLayoutManager
                    fragmentResourcesBinding.resourcesRv.setHasFixedSize(true)
                    fragmentResourcesBinding.resourcesRv.adapter = adapter
                })
        }
    }
}
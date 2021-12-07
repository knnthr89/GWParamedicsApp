package com.guelphwellingtonparamedicsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.adapters.AreasAdapter
import com.guelphwellingtonparamedicsapp.adapters.TypeAssessmentsAdapter
import com.guelphwellingtonparamedicsapp.databinding.FragmentSectionsBinding
import com.guelphwellingtonparamedicsapp.models.TypeAssessmentModel


class SectionsFragment : Fragment() {

    private lateinit var fragmentSectionsBinding: FragmentSectionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentSectionsBinding = DataBindingUtil.inflate(LayoutInflater.from(inflater.context), R.layout.fragment_sections, container, false)

        var typeAssessmentModel = TypeAssessmentModel(id = 1, name = "Assessment Folder")
        var adapter = TypeAssessmentsAdapter(requireContext(), arrayListOf(typeAssessmentModel))
        var mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        fragmentSectionsBinding.sectionsRv.layoutManager = mLayoutManager
        fragmentSectionsBinding.sectionsRv.setHasFixedSize(true)
        fragmentSectionsBinding.sectionsRv.adapter = adapter

        // Inflate the layout for this fragment
        return fragmentSectionsBinding.root
    }
}
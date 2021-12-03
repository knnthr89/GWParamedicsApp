package com.guelphwellingtonparamedicsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.activities.BottomNavigationActivity
import com.guelphwellingtonparamedicsapp.adapters.InteractiveFormsAdapter
import com.guelphwellingtonparamedicsapp.adapters.InteractiveFormsAdapter.SelectedInteractiveForm
import com.guelphwellingtonparamedicsapp.databinding.FragmentAssessmentsBinding
import com.guelphwellingtonparamedicsapp.factories.AssessmentModelFactory
import com.guelphwellingtonparamedicsapp.viewModels.AssessmentsViewModel

class AssessmentsFragment : Fragment(),
    SelectedInteractiveForm {

    private lateinit var fragmentAssessmentsBinding: FragmentAssessmentsBinding
    private var mAssessmentsViewModel: AssessmentsViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAssessmentsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_assessments, container, false)

        return fragmentAssessmentsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAssessmentsViewModel =
            ViewModelProvider(this, AssessmentModelFactory(activity as BottomNavigationActivity)).get(
                AssessmentsViewModel::class.java
            )

        mAssessmentsViewModel?.results?.observe(viewLifecycleOwner, { assessments ->
            val adapter = InteractiveFormsAdapter(requireContext(), assessments, this)
            var mLayoutManager = LinearLayoutManager(requireContext())
            fragmentAssessmentsBinding.interactiveRecycler.layoutManager = mLayoutManager
            fragmentAssessmentsBinding.interactiveRecycler.adapter = adapter
        })

    }

    override fun selected(id: Int) {
        mAssessmentsViewModel?.getInteractiveForm(id)
    }
}
package com.guelphwellingtonparamedicsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guelphwellingtonparamedicsapp.activities.BottomNavigationActivity
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.adapters.InteractiveFormsAdapter
import com.guelphwellingtonparamedicsapp.adapters.InteractiveFormsAdapter.SelectedInteractiveForm
import com.guelphwellingtonparamedicsapp.databinding.FragmentAssessmentsBinding
import com.guelphwellingtonparamedicsapp.manager.AssessmentsManager
import com.guelphwellingtonparamedicsapp.manager.AssessmentsManager.IndividualFormListener
import com.guelphwellingtonparamedicsapp.manager.AssessmentsManager.InteractiveFormsListener
import com.guelphwellingtonparamedicsapp.models.IndividualFormModel
import com.guelphwellingtonparamedicsapp.models.InteractiveFormModel

class AssessmentsFragment : Fragment(), InteractiveFormsListener, SelectedInteractiveForm, IndividualFormListener {

    private lateinit var fragmentAssessmentsBinding: FragmentAssessmentsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAssessmentsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_assessments, container, false)

        return fragmentAssessmentsBinding.root
    }

    override fun onResume() {
        AssessmentsManager.getInstance(requireContext()).setInteractiveFormsListener(this)
        AssessmentsManager.getInstance(requireContext()).getInteractiveForms()
        super.onResume()
    }

    override fun onInteractiveFormsSuccess(interactiveFormsList : ArrayList<InteractiveFormModel>) {
        if(interactiveFormsList.isNotEmpty()){
            val adapter = InteractiveFormsAdapter(requireContext(), interactiveFormsList, this)
            var mLayoutManager = LinearLayoutManager(requireContext())
            fragmentAssessmentsBinding.interactiveRecycler.layoutManager = mLayoutManager
            fragmentAssessmentsBinding.interactiveRecycler.adapter = adapter
        }
    }

    override fun onInteractiveFormsFail(message: String, code: Int?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun selected(id: Int) {
        AssessmentsManager.getInstance(requireContext()).setIndividualFormListener(this)
        AssessmentsManager.getInstance(requireContext()).getUniqueInteractiveForm(id)

    }

    override fun onIndividualFormSuccess(individualFormModel: IndividualFormModel) {
        if(individualFormModel != null){
                (activity as BottomNavigationActivity).showFragment(IndividualFormFragment(), model = individualFormModel)
        }
    }

    override fun onIndividualFormFail(message: String, code: Int?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
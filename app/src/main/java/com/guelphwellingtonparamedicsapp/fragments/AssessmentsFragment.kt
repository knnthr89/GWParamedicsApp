package com.guelphwellingtonparamedicsapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guelphwellingtonparamedicsapp.BottomNavigationActivity
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.adapters.InteractiveFormsAdapter
import com.guelphwellingtonparamedicsapp.adapters.InteractiveFormsAdapter.SelectedInteractiveForm
import com.guelphwellingtonparamedicsapp.manager.AssessmentsManager
import com.guelphwellingtonparamedicsapp.manager.AssessmentsManager.IndividualFormListener
import com.guelphwellingtonparamedicsapp.manager.AssessmentsManager.InteractiveFormsListener
import com.guelphwellingtonparamedicsapp.models.IndividualFormModel
import com.guelphwellingtonparamedicsapp.models.InteractiveFormModel

class AssessmentsFragment : Fragment(), InteractiveFormsListener, SelectedInteractiveForm, IndividualFormListener {

    private lateinit var interactiveRecycler : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_assessments, container, false)

        interactiveRecycler = rootView.findViewById(R.id.interactiveRecycler)

        return rootView
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
            mLayoutManager.orientation = LinearLayoutManager.VERTICAL
            interactiveRecycler.layoutManager = mLayoutManager
            interactiveRecycler.setHasFixedSize(true)
            interactiveRecycler.adapter = adapter
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
                (activity as BottomNavigationActivity).showFragment(IndividualFormFragment())
        }
    }

    override fun onIndividualFormFail(message: String, code: Int?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
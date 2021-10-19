package com.guelphwellingtonparamedicsapp.fragments

import android.os.Bundle
import android.util.Log
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response.error
import com.android.volley.VolleyLog.e
import com.guelphwellingtonparamedicsapp.R
import com.guelphwellingtonparamedicsapp.adapters.InteractiveFormsAdapter
import com.guelphwellingtonparamedicsapp.manager.AssessmentsManager
import com.guelphwellingtonparamedicsapp.manager.AssessmentsManager.InteractiveFormsListener
import com.guelphwellingtonparamedicsapp.models.AssessmentModel
import com.guelphwellingtonparamedicsapp.models.InteractiveFormModel
import java.io.Console

class AssessmentsFragment : Fragment(), InteractiveFormsListener {
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
            val adapter = InteractiveFormsAdapter(requireContext(), interactiveFormsList)
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

}
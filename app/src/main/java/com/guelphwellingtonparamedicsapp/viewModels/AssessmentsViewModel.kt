package com.guelphwellingtonparamedicsapp.viewModels

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.guelphwellingtonparamedicsapp.adapters.InteractiveFormsAdapter.SelectedInteractiveForm
import com.guelphwellingtonparamedicsapp.manager.AssessmentsManager
import com.guelphwellingtonparamedicsapp.manager.AssessmentsManager.InteractiveFormsListener
import com.guelphwellingtonparamedicsapp.manager.AssessmentsManager.IndividualFormListener
import com.guelphwellingtonparamedicsapp.models.InteractiveFormModel
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.guelphwellingtonparamedicsapp.activities.BottomNavigationActivity
import com.guelphwellingtonparamedicsapp.fragments.IndividualFormFragment
import com.guelphwellingtonparamedicsapp.models.IndividualFormModel

class AssessmentsViewModel(activity: Activity) : AndroidViewModel(activity.application),
    InteractiveFormsListener, SelectedInteractiveForm, IndividualFormListener {

    private var mAllAssessments = MutableLiveData<List<InteractiveFormModel>>()
    private var activity = activity

    init {
        AssessmentsManager.getInstance(context = getApplication()).setInteractiveFormsListener(this)
        AssessmentsManager.getInstance(context = getApplication()).getInteractiveForms()
    }

    val results : LiveData<List<InteractiveFormModel>>
        get() = mAllAssessments


    override fun selected(id: Int) {
        AssessmentsManager.getInstance(context = getApplication()).getUniqueInteractiveForm(id)
    }

    override fun onInteractiveFormsSuccess(interactiveFormsList: ArrayList<InteractiveFormModel>) {
        mAllAssessments.value = interactiveFormsList
    }

    override fun onInteractiveFormsFail(message: String, code: Int?) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
    }

    fun getInteractiveForm(id : Int){
        AssessmentsManager.getInstance(context = getApplication()).setIndividualFormListener(this)
        AssessmentsManager.getInstance(context = getApplication()).getUniqueInteractiveForm(id)
    }

    override fun onIndividualFormSuccess(individualFormModel: IndividualFormModel) {
        if(individualFormModel != null){
            (activity as BottomNavigationActivity).showFragment(IndividualFormFragment(), model = individualFormModel)
        }
    }

    override fun onIndividualFormFail(message: String, code: Int?) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
    }
}
package com.guelphwellingtonparamedicsapp.viewModels

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.guelphwellingtonparamedicsapp.database.AppDatabase
import com.guelphwellingtonparamedicsapp.manager.ContactsManager.ContactsGroupsListener
import com.guelphwellingtonparamedicsapp.models.DepartmentModel
import com.guelphwellingtonparamedicsapp.utilities.ContactUtilities

class AreasViewModel(context : Context) : AndroidViewModel(context.applicationContext as Application),
    ContactsGroupsListener {

    private var mAllAreas = MutableLiveData<List<DepartmentModel>>()

    init {
        mAllAreas.value = ContactUtilities.initialStaticData().value
    }

    val results: LiveData<List<DepartmentModel>>
        get() = mAllAreas

    override fun onContactsGroupsSuccess(areasList: LiveData<List<DepartmentModel>>) {
        this.mAllAreas.value = areasList.value
    }

    override fun onContactsGroupsFail(message: String, code: Int?) {
        Toast.makeText(AppDatabase.context, "error", Toast.LENGTH_SHORT).show()
    }

}
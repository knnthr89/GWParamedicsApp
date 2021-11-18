package com.guelphwellingtonparamedicsapp.viewModels

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.guelphwellingtonparamedicsapp.database.AppDatabase
import com.guelphwellingtonparamedicsapp.manager.ContactsManager
import com.guelphwellingtonparamedicsapp.manager.ContactsManager.ContactsGroupsListener
import com.guelphwellingtonparamedicsapp.manager.ResourcesManager
import com.guelphwellingtonparamedicsapp.models.AreaModel
import com.guelphwellingtonparamedicsapp.models.RegionModel

class AreasViewModel(context : Context) : AndroidViewModel(context.applicationContext as Application),
    ContactsGroupsListener {

    private var mAllAreas = MutableLiveData<List<AreaModel>>()

    init {
        ContactsManager.getInstance(context = context).setContactsGroupsListener(this)
        ContactsManager.getInstance(context = context).getContactsGroups()
    }

    val results: LiveData<List<AreaModel>>
        get() = mAllAreas

    override fun onContactsGroupsSuccess(areasList: LiveData<List<AreaModel>>) {
        this.mAllAreas.value = areasList.value
    }

    override fun onContactsGroupsFail(message: String, code: Int?) {
        Toast.makeText(AppDatabase.context, "error", Toast.LENGTH_SHORT).show()
    }

}
package com.guelphwellingtonparamedicsapp.repositories

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.guelphwellingtonparamedicsapp.manager.ResourcesManager
import com.guelphwellingtonparamedicsapp.manager.ResourcesManager.GetResourcesListener
import com.guelphwellingtonparamedicsapp.models.ContactModel
import com.guelphwellingtonparamedicsapp.models.RegionModel
import com.guelphwellingtonparamedicsapp.models.ResourceModel

class RegionsRepository(context: Context) : GetResourcesListener {

    private var  mAllRegions : LiveData<List<RegionModel>> = MutableLiveData()
    private var context = context

    //generated method for obtain the data in the viewmodel from database
    init {
        ResourcesManager.getInstance(context = context.applicationContext).setAllResourcesListener(this)
        ResourcesManager.getInstance(context = context.applicationContext).getAllResources()
    }

    override fun onResourcesSuccess(regionModelList: LiveData<List<RegionModel>>) {
        mAllRegions = regionModelList
    }

    override fun onResourcesFail(message: String, code: Int?) {
        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
    }

    fun getAllRegions(): LiveData<List<RegionModel>> {
        return mAllRegions
    }
}
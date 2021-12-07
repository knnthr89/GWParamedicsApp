package com.guelphwellingtonparamedicsapp.viewModels

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.guelphwellingtonparamedicsapp.manager.ResourcesManager
import com.guelphwellingtonparamedicsapp.manager.ResourcesManager.GetResourcesListener
import com.guelphwellingtonparamedicsapp.models.RegionModel

class RegionsViewModel(context: Context) : AndroidViewModel(context.applicationContext as Application), GetResourcesListener {

    private var mAllRegions = MutableLiveData<List<RegionModel>>()
    private val context = context

    init {
        ResourcesManager.getInstance(context).setAllResourcesListener(this)
        ResourcesManager.getInstance(context).getAllResources()
    }

    val results: LiveData<List<RegionModel>>
        get() = mAllRegions

    override fun onResourcesSuccess(regionModelList: LiveData<List<RegionModel>>) {
        this.mAllRegions.value = regionModelList.value
    }

    override fun onResourcesFail(message: String, code: Int?) {
        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
    }
}
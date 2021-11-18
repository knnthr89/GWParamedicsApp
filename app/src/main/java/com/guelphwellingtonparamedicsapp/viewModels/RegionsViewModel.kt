package com.guelphwellingtonparamedicsapp.viewModels

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.guelphwellingtonparamedicsapp.database.AppDatabase.Companion.context
import com.guelphwellingtonparamedicsapp.manager.ResourcesManager
import com.guelphwellingtonparamedicsapp.manager.ResourcesManager.GetResourcesListener
import com.guelphwellingtonparamedicsapp.models.RegionModel
import kotlinx.coroutines.delay

class RegionsViewModel(application: Application) : AndroidViewModel(application), GetResourcesListener {

    private lateinit var mAllRegions : LiveData<List<RegionModel>>

    suspend fun getAllRegionsAsync(context: Context) : LiveData<List<RegionModel>> {
        ResourcesManager.getInstance(context).setAllResourcesListener(this)
        ResourcesManager.getInstance(context).getAllResources()

        delay(1000)

        return mAllRegions
    }

    override fun onResourcesSuccess(regionModelList: LiveData<List<RegionModel>>) {
        this.mAllRegions = regionModelList
    }

    override fun onResourcesFail(message: String, code: Int?) {
        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
    }
}
package com.guelphwellingtonparamedicsapp

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.guelphwellingtonparamedicsapp.database.AppDatabase.Companion.context
import com.guelphwellingtonparamedicsapp.factories.RegionModelFactory
import com.guelphwellingtonparamedicsapp.manager.ResourcesManager
import com.guelphwellingtonparamedicsapp.models.ContactModel
import com.guelphwellingtonparamedicsapp.models.RegionModel
import com.guelphwellingtonparamedicsapp.models.ResourceModel
import com.guelphwellingtonparamedicsapp.viewModels.RegionsViewModel
import junit.framework.TestCase
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.manipulation.Ordering

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest: ResourcesManager.GetResourcesListener {

    private lateinit var regionsList : MutableLiveData<List<RegionModel>>
    private var mRegionsViewModel: RegionsViewModel? = null

    @Before
    fun execute(){
    }

    @Test
    fun getRegionsList(){
        assertNotNull(regionsList)
    }

    override fun onResourcesSuccess(regionModelList: LiveData<List<RegionModel>>) {
        this.regionsList.value = regionModelList.value
    }

    override fun onResourcesFail(message: String, code: Int?) {
        Log.e("error","on resources fail")
    }
}
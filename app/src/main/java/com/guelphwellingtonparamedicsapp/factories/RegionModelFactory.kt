package com.guelphwellingtonparamedicsapp.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.guelphwellingtonparamedicsapp.viewModels.RegionsViewModel

class RegionModelFactory(private val mApplication: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegionsViewModel(mApplication) as T
    }
}
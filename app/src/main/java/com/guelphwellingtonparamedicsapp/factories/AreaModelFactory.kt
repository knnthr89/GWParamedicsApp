package com.guelphwellingtonparamedicsapp.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.guelphwellingtonparamedicsapp.viewModels.AreasViewModel
import com.guelphwellingtonparamedicsapp.viewModels.RegionsViewModel

class AreaModelFactory(private val mApplication: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AreasViewModel(mApplication) as T
    }
}
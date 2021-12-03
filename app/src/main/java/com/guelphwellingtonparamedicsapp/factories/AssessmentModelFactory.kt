package com.guelphwellingtonparamedicsapp.factories

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.guelphwellingtonparamedicsapp.viewModels.AssessmentsViewModel

class AssessmentModelFactory(private val activity: Activity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AssessmentsViewModel(activity) as T
    }
}
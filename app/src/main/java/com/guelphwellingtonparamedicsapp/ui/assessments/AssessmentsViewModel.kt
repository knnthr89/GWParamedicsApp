package com.guelphwellingtonparamedicsapp.ui.assessments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AssessmentsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is assessments Fragment"
    }
    val text: LiveData<String> = _text
}
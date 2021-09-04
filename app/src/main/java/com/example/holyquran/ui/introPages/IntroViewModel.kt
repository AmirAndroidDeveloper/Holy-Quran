package com.example.holyquran.ui.introPages

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class IntroViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _skipButton = MutableLiveData<Boolean>(false)
    val skipButton: LiveData<Boolean>
        get() = _skipButton


    fun skipButton() {
        _skipButton.value = true
    }

}

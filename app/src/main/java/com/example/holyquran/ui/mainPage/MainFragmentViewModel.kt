package com.example.holyquran.ui.mainPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.UserDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MainFragmentViewModel(
    val mUserInfoDAO: UserDAO,
    application: Application
    ) : AndroidViewModel(application) {

    var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _noNameTillKnow = MutableLiveData<Boolean>(false)
    val noNameTillKnow: LiveData<Boolean>
        get() = _noNameTillKnow


    fun noNameTillKnow() {
        _noNameTillKnow.value = true
    }


}


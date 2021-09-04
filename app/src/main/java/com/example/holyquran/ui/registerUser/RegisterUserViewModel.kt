package com.example.holyquran.ui.registerUser

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.UserInfo
import kotlinx.coroutines.*


class RegisterUserViewModel(
    val mUserInfoDAO: UserDAO,
    application: Application
) :
    AndroidViewModel(application) {
    var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun insertUser(
        name: String,
        personalCode: String,
        id: MutableList<UserInfo>,
    ) {
        uiScope.launch {
            //wait after 5 sec resum actions
            //delay(5_000)
            try {
                val category = UserInfo(0L, name, personalCode)
                withContext(Dispatchers.IO) {
                    val personalId = mUserInfoDAO.insert(category)
                    id.forEach {
                    }
                    mUserInfoDAO.insertList(id)
                }
            } catch (e: Exception) {
                Log.d("TAG", "insertContact: ${e.message}")
            }
        }
    }


    private val _goToMainPage = MutableLiveData<Boolean>(false)
    val goToMainPage: LiveData<Boolean>
        get() = _goToMainPage

    fun goToMainPageClicked() {
        _goToMainPage.value = true
    }
    }



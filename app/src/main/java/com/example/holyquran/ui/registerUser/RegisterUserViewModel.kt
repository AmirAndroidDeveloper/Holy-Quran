package com.example.holyquran.ui.registerUser

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.UserInfo
import kotlinx.coroutines.*
import java.util.*


class RegisterUserViewModel(
    val mUserInfoDAO: UserDAO,
    application: Application
) :
    AndroidViewModel(application) {
    var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun insertUser(
        fullName: String,
        phoneNumber: String,
        personalCode: String,
        address: String,
        id: MutableList<UserInfo>,
    ) {
        uiScope.launch {
            //wait after 5 sec resum actions
            //delay(5_000)
            try {
                val user = UserInfo(0L, fullName, phoneNumber,personalCode,address)
                withContext(Dispatchers.IO) {
                    val personalId = mUserInfoDAO.insert(user)
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



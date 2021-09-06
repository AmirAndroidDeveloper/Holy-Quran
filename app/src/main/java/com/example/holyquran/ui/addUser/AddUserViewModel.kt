package com.example.holyquran.ui.addUser

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.UserInfo
import kotlinx.coroutines.*

class AddUserViewModel(
    val mUserInfoDAO: UserDAO,
    application: Application
) :
    AndroidViewModel(application) {
    var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun insertUser(
        fullName: String,
        accountId:String,
        mobileNumber:String,
        phoneNumber: String,
        dateOfCreation: String,
        address: String,
        id: MutableList<UserInfo>,
    ) {
        uiScope.launch {
            try {
                Log.d("TAG", "insertTest")
                val user = UserInfo(0L, fullName, accountId,mobileNumber,phoneNumber,dateOfCreation,address)
                withContext(Dispatchers.IO) {
                    val personalId = mUserInfoDAO.insert(user)
                    id.forEach {
                    }
                    mUserInfoDAO.insertList(id)
                    Log.d("TAG", "insertUser: $fullName")
                }
            } catch (e: Exception) {
                Log.d("TAG", "insertContact: ${e.message}")
            }
        }
    }

    private val _addUser = MutableLiveData<Boolean>(false)
    val addUser: LiveData<Boolean>
        get() = _addUser
    fun addUser() {
        _addUser.value = true
    }


}

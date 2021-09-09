package com.example.holyquran.ui.userList

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.UserInfo
import kotlinx.coroutines.*


class UserListViewModel(
    val mUserInfoDAO: UserDAO,
   val dataSourceTransactions: TransactionsDAO,
    application: Application
) :
    AndroidViewModel(application) {
    var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _goTOAddUser = MutableLiveData<Boolean>(false)
    val goTOAddUser: LiveData<Boolean>
        get() = _goTOAddUser

    fun goTOAddUser() {
        _goTOAddUser.value = true
    }
    val userInfo = MutableLiveData<List<UserInfo>>()
    fun getUserList():LiveData<List<UserInfo>> {
        return mUserInfoDAO.getAllUserInfo()
    }
    fun deleteCategory(
        userInfo: UserInfo
    ) {
        uiScope.launch {
            try {
                mUserInfoDAO.deleteCategory(userInfo)
            } catch (e: Exception) {
                Log.d("TAG", "deleteContact: ${e.message}")
            }
        }
    }

    private val _popUpWindow = MutableLiveData<Boolean>(false)
    val popUpWindow: LiveData<Boolean>
        get() = _popUpWindow

    fun popUpWindow() {
        _popUpWindow.value = true
    }

}



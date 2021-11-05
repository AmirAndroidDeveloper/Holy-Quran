package com.example.holyquran.ui.userList

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.BankDAO
import com.example.holyquran.data.database.LoanDAO
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.UserInfo
import kotlinx.coroutines.*


class UserListViewModel(
    val mUserInfoDAO: UserDAO,
    val mTransactionsDAO: TransactionsDAO,
    val mLoan: LoanDAO,
    val mBankDAO: BankDAO,
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

    fun goToAddUserDone() {
        _goTOAddUser.value = false
    }

    val userInfo = MutableLiveData<List<UserInfo>>()
    fun getUserList(fullName: String): LiveData<List<UserInfo>> {
        return if (fullName.isEmpty()){
            mUserInfoDAO.getAllUserInfo()
        }else{
            mUserInfoDAO.searchUserName(fullName)
        }
    }

    fun deleteUser(
        userInfo: UserInfo
    ) {
        uiScope.launch {
            try {
                mUserInfoDAO.deleteUser(userInfo)
            } catch (e: Exception) {
                Log.d("TAG", "deleteContact: ${e.message}")
            }
        }
    }

    fun deleteAllUsers() {
        uiScope.launch {
            try {
                mUserInfoDAO.deleteAllUser()
            } catch (e: Exception) {
                Log.d("TAG", "deleteContact: ${e.message}")
            }
        }
    }

}



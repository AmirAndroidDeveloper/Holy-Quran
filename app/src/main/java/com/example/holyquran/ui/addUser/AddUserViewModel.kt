package com.example.holyquran.ui.addUser

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.LoanDAO
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.UserInfo
import kotlinx.coroutines.*

class AddUserViewModel(
    val mUserInfoDAO: UserDAO,
    val  mTransactionDAO: TransactionsDAO,
    val mLoan: LoanDAO,

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
    ) {
        uiScope.launch {
            try {
                Log.d("TAG", "insertTest")
                val user = UserInfo(0L, fullName, accountId,mobileNumber,phoneNumber,dateOfCreation,address)
                withContext(Dispatchers.IO) {
                  mUserInfoDAO.insert(user)
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

    private val _userName = MutableLiveData<UserInfo>()
    val userName: LiveData<UserInfo>
        get() = _userName

    fun setUserName(id: Long): LiveData<UserInfo>? {
        return mUserInfoDAO.get(id)
    }

    fun setUserName(mUser: UserInfo) {
        _userName.value = mUser
    }


}

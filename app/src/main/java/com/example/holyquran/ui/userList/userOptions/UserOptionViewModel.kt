package com.example.holyquran.ui.userList.userOptions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.Transaction
import com.example.holyquran.data.model.UserInfo
import kotlinx.coroutines.*

class UserOptionViewModel(
    private val mUserInfoDAO: UserDAO,
    val mTransactionsDAO: TransactionsDAO,
    application: Application,
) :
    AndroidViewModel(application) {
    private val _userName = MutableLiveData<UserInfo>()
    val userName: LiveData<UserInfo>
        get() = _userName

    fun setUserName(id: Long): LiveData<UserInfo>? {
        return mUserInfoDAO.get(id)
    }

    fun setUserName(mUserInfo: UserInfo) {
        _userName.value = mUserInfo
    }

    var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    fun insertMoney(
        amount: String,
        userId: Long,
    ) {
        uiScope.launch {
            mTransactionsDAO.insert(
                Transaction(
                    0L,
                    userId,
                    null,
                    null,
                    null,
                    null,
                    amount,
                    null,
                    null
                )
            )

        }
    }

}
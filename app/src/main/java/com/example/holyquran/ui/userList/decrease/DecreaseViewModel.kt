package com.example.holyquran.ui.userList.decrease

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.Transaction
import com.example.holyquran.data.model.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DecreaseViewModel(
    private val mUserInfoDAO: UserDAO,
    private val mTransactionsDAO: TransactionsDAO,

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

    private val _decrease = MutableLiveData<Transaction>()
    val decrease: LiveData<Transaction>
        get() = _decrease

    fun setDecrease(id: Long): LiveData<Transaction>? {
        return mTransactionsDAO.get(id)
    }

    fun setDecrease(mTransInfo: Transaction) {
        _decrease.value = mTransInfo
    }

    fun sumUserIncrease(id: Long): Int {
        return mTransactionsDAO.sumUserIncrease(id)
    }

    fun sumUserDecrease(id: Long): Int {
        return mTransactionsDAO.sumUserDecrease(id)
    }

    var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
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
                    null,
                    amount,
                    null,
                    sumUserIncrease(userId).minus(sumUserDecrease(userId))

                )
            )

        }
    }


}
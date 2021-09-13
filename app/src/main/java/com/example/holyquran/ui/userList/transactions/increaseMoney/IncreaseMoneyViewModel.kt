package com.example.holyquran.ui.userList.transactions.increaseMoney

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.LoanDAO
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.Transaction
import com.example.holyquran.data.model.UserInfo
import kotlinx.coroutines.*

class IncreaseMoneyViewModel(
    private val mUserInfoDAO: UserDAO,
    val mTransactionsDAO: TransactionsDAO,
    val mLoan: LoanDAO,

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

    private val _increase = MutableLiveData<Transaction>()
    val increase: LiveData<Transaction>
        get() = _increase

    fun setIncrease(id: Long): LiveData<Transaction>? {
        return mTransactionsDAO.get(id)
    }

    fun setIncrease(mTransInfo: Transaction) {
        _increase.value = mTransInfo
    }

    fun sumUserIncrease(id: Long): Int {
        return mTransactionsDAO.sumUserIncrease(id)
    }

    fun sumUserDecrease(id: Long): Int {
        return mTransactionsDAO.sumUserDecrease(id)
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
                    null,
                   sumUserIncrease(userId).minus(sumUserDecrease(userId))
                )
            )

        }
    }

    fun deleteTrans(
        transInfo: UserInfo
    ) {
        uiScope.launch {
            try {
                mUserInfoDAO.deleteCategory(transInfo)
            } catch (e: Exception) {
                Log.d("TAG", "deleteContact: ${e.message}")
            }
        }
    }
}
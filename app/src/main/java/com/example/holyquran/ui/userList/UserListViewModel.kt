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
import com.example.holyquran.data.model.Loan
import com.example.holyquran.data.model.Transaction
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

    fun goToAddUserDone(){
        _goTOAddUser.value=false
    }

    val userInfo = MutableLiveData<List<UserInfo>>()
    fun getUserList(): LiveData<List<UserInfo>> {
        return mUserInfoDAO.getAllUserInfo()
    }

    val userTotalMoney = MutableLiveData<List<Transaction>>()
    fun getUserTotalMoney(): LiveData<List<Transaction>> {
        return mTransactionsDAO.getAllTransaction()
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



package com.example.holyquran.ui.editUserInfo

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

class EditUserInfoViewModel(
    private val mUserInfoDAO: UserDAO,
    val mTransactionsDAO: TransactionsDAO,
    val mLoan: LoanDAO,
    val mBankDAO: BankDAO,
    application: Application,
) :
    AndroidViewModel(application) {
    var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _userName = MutableLiveData<UserInfo>()
    val userName: LiveData<UserInfo>
        get() = _userName

    fun setUserName(id: Long): LiveData<UserInfo>? {
        return mUserInfoDAO.get(id)
    }

    fun setUserName(mUserInfo: UserInfo) {
        _userName.value = mUserInfo
    }

    fun getPersonalInfo(id: Long): LiveData<UserInfo>? {
        return mUserInfoDAO.get(id)
    }

    private val _personalInfo = MutableLiveData<UserInfo>()
    val personalInfo: LiveData<UserInfo>
        get() = _personalInfo

    fun setPersonalInfo(mPersonalInfo: UserInfo) {
        _personalInfo.value = mPersonalInfo
    }

    private val _validInfo = MutableLiveData<Boolean>(false)
    val validInfo: LiveData<Boolean>
        get() = _validInfo

    fun validDone() {
        _validInfo.value = false
    }

    fun onSubmitBtnClick() {
        _validInfo.value = true
    }

    fun updateUser(
        userInfo: UserInfo
    ) {
        uiScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    mUserInfoDAO.update(userInfo)
                }
                _updateSuccess.value = true

            } catch (e: Exception) {
                Log.d("TAG", "insertContact: ${e.message}")
            }
        }
    }

    private val _updateSuccess = MutableLiveData<Boolean>(false)
    val updateSuccess: LiveData<Boolean>
        get() = _updateSuccess

}
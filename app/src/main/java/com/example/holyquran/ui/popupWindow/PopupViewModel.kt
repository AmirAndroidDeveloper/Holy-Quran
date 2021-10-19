package com.example.holyquran.ui.popupWindow

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.BankDAO
import com.example.holyquran.data.database.LoanDAO
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.Loan
import com.example.holyquran.data.model.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PopupViewModel(
    private val mUserInfoDAO: UserDAO,
    private val mTransactionsDAO: TransactionsDAO,
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

    private val _loan = MutableLiveData<Loan>()
    val loan: LiveData<Loan>
        get() = _loan

    fun setLoan(id: Long): LiveData<Loan>? {
        return mLoan.get(id)
    }

    fun setLoan(mLoan: Loan) {
        _loan.value = mLoan
    }

    fun setUserName(id: Long): LiveData<UserInfo>? {
        return mUserInfoDAO.get(id)
    }

    fun setUserName(mUserInfo: UserInfo) {
        _userName.value = mUserInfo
    }

    private val _goToIncreaseSubmit = MutableLiveData<Boolean>(false)
    val goToIncreaseSubmit: LiveData<Boolean>
        get() = _goToIncreaseSubmit

    fun goToIncrease() {
        _goToIncreaseSubmit.value = true
    }

    private val _goToPayPaymentsSubmit = MutableLiveData<Boolean>(false)
    val goToPayPaymentsSubmit: LiveData<Boolean>
        get() = _goToPayPaymentsSubmit

    fun goToPayPayments() {
        _goToPayPaymentsSubmit.value = true
    }

    fun goToPayPaymentsDone() {
        _goToPayPaymentsSubmit.value = false
    }

    fun goToIncreaseDone() {
        _goToIncreaseSubmit.value = false
    }

    private val _goToDecreaseSubmit = MutableLiveData<Boolean>(false)
    val goToDecreaseSubmit: LiveData<Boolean>
        get() = _goToDecreaseSubmit

    fun goToDecrease() {
        _goToDecreaseSubmit.value = true
    }

    fun goToDecreaseDone() {
        _goToDecreaseSubmit.value = false
    }

    private val _goToLoanSubmit = MutableLiveData<Boolean>(false)
    val goToLoanSubmit: LiveData<Boolean>
        get() = _goToLoanSubmit

    fun goToLoan() {
        _goToLoanSubmit.value = true
    }

    fun goToLoanDone() {
        _goToLoanSubmit.value = false
    }

    private val _goToLoanListSubmit = MutableLiveData<Boolean>(false)
    val goToLoanListSubmit: LiveData<Boolean>
        get() = _goToLoanListSubmit

    fun goToLoanList() {
        _goToLoanListSubmit.value = true
    }

    fun goToLoanListDone() {
        _goToLoanListSubmit.value = false
    }


    private val _goToUserTransactionHistory = MutableLiveData<Boolean>(false)
    val goToUserTransactionHistory: LiveData<Boolean>
        get() = _goToUserTransactionHistory

    fun goToUserTransactionHistory() {
        _goToUserTransactionHistory.value = true
    }

    private val _goToEditUserInfoSubmit = MutableLiveData<Boolean>(false)
    val goToEditUserInfoSubmit: LiveData<Boolean>
        get() = _goToEditUserInfoSubmit

    fun goToEditUserInfo() {
        _goToEditUserInfoSubmit.value = true
    }

    fun goToEditUserInfoDone() {
        _goToEditUserInfoSubmit.value = false
    }

    fun deleteUser(
        userInfo: UserInfo
    ) {
        uiScope.launch {
            try {
                mUserInfoDAO.deleteUser(userInfo)
            } catch (e: Exception) {
            }
        }
    }

    private val _deleteUser = MutableLiveData<Boolean>(false)
    val deleteUser: LiveData<Boolean>
        get() = _deleteUser

    fun deleteUser() {
        _deleteUser.value = true
    }

    fun deleteUserDone() {
        _deleteUser.value = false
    }


}
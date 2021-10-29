package com.example.holyquran.ui.mainPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.BankDAO
import com.example.holyquran.data.database.LoanDAO
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.Loan
import com.example.holyquran.data.model.Transaction
import com.example.holyquran.data.model.TransactionAndBank
import com.example.holyquran.data.model.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MainFragmentViewModel(
    val mUserInfoDAO: UserDAO,
    val mTransactionsDAO: TransactionsDAO,
    val mLoanDAO: LoanDAO,
    val mBankDAO: BankDAO,
    application: Application
) : AndroidViewModel(application) {

    var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val userInfo = MutableLiveData<List<UserInfo>>()
    fun getUserList(): LiveData<List<UserInfo>> {
        return mUserInfoDAO.getAllUserInfo()
    }

    val loan = MutableLiveData<List<Loan>>()
    fun getLoanList(): LiveData<List<Loan>> {
        return mLoanDAO.getAllLoans()
    }

    fun sumAllIncrease(): Long {
        return mTransactionsDAO.sumAllIncrease()
    }

    fun sumAllDecrease(): Long {
        return mTransactionsDAO.sumAllDecrease()
    }
    fun sumAllLoansAmount():Long{
        return mLoanDAO.sumAllLoans()
    }

    fun sumUserPayments(type: String): Long {
        return mTransactionsDAO.sumAllUserPayments(type)
    }

    fun sumUserDeposit(type: String): Long {
        return mTransactionsDAO.sumAllUserDeposit(type)
    }

    private val _increase = MutableLiveData<Loan>()
    val increase: LiveData<Loan>
        get() = _increase

    fun setIncrease(): LiveData<Loan>? {
        return mLoanDAO.getAll()
    }

    fun setIncrease(mLoan: Loan) {
        _increase.value = mLoan
    }



}


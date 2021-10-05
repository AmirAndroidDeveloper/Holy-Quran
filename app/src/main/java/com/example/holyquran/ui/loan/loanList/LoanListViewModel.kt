package com.example.holyquran.ui.loan.loanList

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
import com.example.holyquran.data.model.UserInfo

class LoanListViewModel(
    private val mUserInfoDAO: UserDAO,
    val mTransactionsDAO: TransactionsDAO,
    val mLoan: LoanDAO,
    val mBankDAO: BankDAO,
    application: Application,
) :
    AndroidViewModel(application) {


    private val _userName = MutableLiveData<Loan>()
    val userName: LiveData<Loan>
        get() = _userName

    fun setUserName(id: Long): LiveData<Loan>? {
        return mLoan.get(id)
    }

    fun setUserName(mUser: Loan) {
        _userName.value = mUser
    }

    val loanInfo = MutableLiveData<List<Loan>>()
    fun getAllLoanList(): LiveData<List<Loan>> {
        Log.d("TAG", "getLoanList: $loanInfo")
        return mLoan.getAllLoans()
    }
}
package com.example.holyquran.ui.loan.loanDetails

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

class LoanDetailViewModel(
    val mUserInfoDAO: UserDAO,
    val mTransactionsDAO: TransactionsDAO,
    val mLoan: LoanDAO,
    val mBankDAO: BankDAO,
    application: Application
) :
    AndroidViewModel(application) {

    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo>
        get() = _userInfo

    fun setUserInfo(id: Long): LiveData<UserInfo>? {
        return mUserInfoDAO.get(id)
    }

    fun setUserInfo(mUserInfo: UserInfo) {
        _userInfo.value = mUserInfo
    }



    private val _loanDetail = MutableLiveData<Loan>()
    val loanDetail: LiveData<Loan>
        get() = _loanDetail

    fun setLoanDetail(id: Long): LiveData<Loan>? {
        return mLoan.getLoanId(id)
    }

    fun setLoanDetail(mLoanDetail: Loan) {
        _loanDetail.value = mLoanDetail
    }

    val loanInfo = MutableLiveData<List<Loan>>()
    fun getAllLoans(id: Long): LiveData<List<Loan>> {
        Log.d("TAG", "getLoanList: $loanInfo")
        return mLoan.getAllLoanByUserPayment(id)
    }
}

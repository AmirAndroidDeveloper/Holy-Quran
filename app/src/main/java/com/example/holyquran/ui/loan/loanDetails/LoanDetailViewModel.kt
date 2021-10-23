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
import com.example.holyquran.data.model.LoanAndUserInfo
import com.example.holyquran.data.model.TransactionAndBank
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
        return mLoan.get(id)
    }

    fun setLoanDetail(mLoanDetail: Loan) {
        _loanDetail.value = mLoanDetail
    }

    private val _joinName = MutableLiveData<LoanAndUserInfo>()
    val joinName: LiveData<LoanAndUserInfo>
        get() = _joinName

    fun joinTables(id: Long): LiveData<LoanAndUserInfo>? {
        return mLoan.joinTables(id)
    }

    fun joinTables(mJoinInfo: LoanAndUserInfo) {
        _joinName.value = mJoinInfo
    }
}

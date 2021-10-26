package com.example.holyquran.ui.transactionHistory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.BankDAO
import com.example.holyquran.data.database.LoanDAO
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.Transaction
import com.example.holyquran.data.model.TransactionAndBank
import com.example.holyquran.data.model.UserInfo

class TransactionHistoryViewModel(
    val mUserInfoDAO: UserDAO,
     val mTransactionDAO: TransactionsDAO,
    val mLoan: LoanDAO,
    val mBankDAO: BankDAO,
    application: Application
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

    private val _singleTransaction = MutableLiveData<Transaction>()
    val singleTransaction: LiveData<Transaction>
        get() = _singleTransaction

    fun setTransaction(id: Long): LiveData<Transaction>? {
        return mTransactionDAO.get(id)
    }

    fun setTransaction(mTransaction: Transaction) {
        _singleTransaction.value = mTransaction
    }


    private val _transaction = MutableLiveData<List<Transaction>>()
    val transaction: LiveData<List<Transaction>>
        get() = _transaction


    private val _joinName = MutableLiveData<TransactionAndBank>()
    val joinName: LiveData<TransactionAndBank>
        get() = _joinName

    fun joinTables(id: Long): LiveData<TransactionAndBank>? {
        return mTransactionDAO.joinTables(id)
    }

    fun joinTables(mJoinInfo: TransactionAndBank) {
        _joinName.value = mJoinInfo
    }


    val transactionInfo = MutableLiveData<List<TransactionAndBank>>()
    fun getTransactionList(id: Long): LiveData<List<TransactionAndBank>> {
        return mTransactionDAO.joinAllTables(id)
    }

}

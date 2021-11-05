package com.example.holyquran.ui.transactionHistory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.BankDAO
import com.example.holyquran.data.database.LoanDAO
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.Transactions
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

    private val _singleTransaction = MutableLiveData<Transactions>()
    val singleTransactions: LiveData<Transactions>
        get() = _singleTransaction

    fun setTransaction(id: Long): LiveData<Transactions>? {
        return mTransactionDAO.get(id)
    }

    fun setTransaction(mTransactions: Transactions) {
        _singleTransaction.value = mTransactions
    }


    private val _transaction = MutableLiveData<List<Transactions>>()
    val transactions: LiveData<List<Transactions>>
        get() = _transaction
   private val _transactionList = MutableLiveData<List<Transactions>>()
    val transactionsList: LiveData<List<Transactions>>
        get() = _transactionList


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

    val transactionInfoList = MutableLiveData<List<Transactions>>()
    fun getTransactionListOrigin(id: Long): LiveData<List<Transactions>> {
        return mTransactionDAO.getAllTransactionByUserId(id)
    }

}

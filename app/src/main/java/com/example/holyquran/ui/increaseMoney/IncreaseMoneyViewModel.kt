package com.example.holyquran.ui.increaseMoney

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.BankDAO
import com.example.holyquran.data.database.LoanDAO
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.Bank
import com.example.holyquran.data.model.Transaction
import com.example.holyquran.data.model.UserInfo
import kotlinx.coroutines.*

class IncreaseMoneyViewModel(
    private val mUserInfoDAO: UserDAO,
    val mTransactionsDAO: TransactionsDAO,
    val mLoan: LoanDAO,
    val mBankDAO: BankDAO,
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

    fun sumUserIncrease(id: Long): Long {
        return mTransactionsDAO.sumUserIncrease(id)
    }

    fun sumUserDecrease(id: Long): Long {
        return mTransactionsDAO.sumUserDecrease(id)
    }

    private val _increaseMoney = MutableLiveData<Boolean>(false)
    val increaseMoney: LiveData<Boolean>
        get() = _increaseMoney

    fun increaseMoney() {
        _increaseMoney.value = true
    }

    fun increaseMoneyDone() {
        _increaseMoney.value = false
    }

    private val _gotToDecreaseMoney = MutableLiveData<Boolean>(false)
    val gotToDecreaseMoney: LiveData<Boolean>
        get() = _gotToDecreaseMoney

    fun gotToDecreaseMoney() {
        _gotToDecreaseMoney.value = true
    }

    fun goToIncreaseDone() {
        _gotToDecreaseMoney.value = false
    }

    var selectedItemPosition = 0
    fun onSelectItem(postion: Int) {
        selectedItemPosition = postion;
        Log.d("position", "onSelectItem: $postion")

    }



    val bankInfo = MutableLiveData<List<Bank>>()
    fun getBankList(): LiveData<List<Bank>> {
        return mBankDAO.getAllBanks()
    }

    var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    fun insertMoney(
        amount: String,
        userId: Long,
        increasePage: String?
    ) {
        var bankId : Long = bankInfo.value?.get(selectedItemPosition)?.bankId!!
        uiScope.launch {
            mTransactionsDAO.insert(
                Transaction(
                    0L,
                    userId,
                    null,
                    bankId,
                    null,
                    amount,
                    null,
                    null,
                    sumUserIncrease(userId).minus(sumUserDecrease(userId)),
                    increasePage
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
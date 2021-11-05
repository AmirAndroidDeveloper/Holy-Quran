package com.example.holyquran.ui.payPayments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.BankDAO
import com.example.holyquran.data.database.LoanDAO
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.Bank
import com.example.holyquran.data.model.Loan
import com.example.holyquran.data.model.Transactions
import com.example.holyquran.data.model.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PayPaymentsViewModel(
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

    private val _copyNumber = MutableLiveData<Boolean>(false)
    val copyNumber: LiveData<Boolean>
        get() = _copyNumber

    fun copyNumber() {
        _copyNumber.value = true
    }
    private val _submit = MutableLiveData<Boolean>(false)
    val submit: LiveData<Boolean>
        get() = _submit

    fun submit() {
        _submit.value = true
    }
    fun submitDone() {
        _submit.value = false
    }

    fun setUserName(id: Long): LiveData<UserInfo>? {
        return mUserInfoDAO.get(id)
    }

    fun setUserName(mUserInfo: UserInfo) {
        _userName.value = mUserInfo
    }

    private val _loan = MutableLiveData<Loan>()
    val loan: LiveData<Loan>
        get() = _loan

    fun setLoan(id: Long): LiveData<Loan>? {
        return mLoan.get(id)
    }

    fun setLoan(mLoan: Loan) {
        _loan.value = mLoan
    }

    var selectedItemPosition = 0
    fun onSelectItem(postion: Int) {
        selectedItemPosition = postion;
    }
    val bankInfo = MutableLiveData<List<Bank>>()
    fun getBankList(): LiveData<List<Bank>> {
        return mBankDAO.getAllBanks()
    }
    private fun sumUserIncrease(id: Long): Long {
        return mTransactionsDAO.sumUserIncrease(id)
    }

    private fun sumUserDecrease(id: Long): Long {
        return mTransactionsDAO.sumUserDecrease(id)
    }

    fun insertMoney(
        amount: String,
        userId: Long,
        increasePage: String?
    ) {
        var bankId: Long = bankInfo.value?.get(selectedItemPosition)?.bankId!!
        uiScope.launch {
            mTransactionsDAO.insert(
                Transactions(
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
}

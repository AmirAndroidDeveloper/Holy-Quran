package com.example.holyquran.ui.loan.getLoan

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.LoanDAO
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.Loan
import com.example.holyquran.data.model.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GetLoanViewModel(
    val mUserInfoDAO: UserDAO,
    val mTransactionsDAO: TransactionsDAO,
    private val mGetLoanDAO: LoanDAO,

    application: Application
) :
    AndroidViewModel(application) {
    var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun insertLoanTimeSpinner(
        amount: String,
        createdDate: String,
        loanSection: String,
        spinnerSectionSpace: String,
        payment: String,
        userId: Long,
    ) {
        uiScope.launch {
            mGetLoanDAO.insert(
                Loan(
                    0L,
                    userId,
                    amount,
                    createdDate,
                    loanSection,
                    null,
                    spinnerSectionSpace,
                    payment
                )
            )
        }
    }

    var selectedItemPosition = 0
    fun onSelectItem(position: Int) {
        selectedItemPosition = position;
        Log.d("position", "onSelectItem: $selectedItemPosition")
    }

    private val _userName = MutableLiveData<UserInfo>()
    val userName: LiveData<UserInfo>
        get() = _userName

    fun setUserName(id: Long): LiveData<UserInfo>? {
        return mUserInfoDAO.get(id)
    }

    fun setUserName(mUser: UserInfo) {
        _userName.value = mUser
    }

    private val _getLoan = MutableLiveData<Boolean>(false)
    val getLoan: LiveData<Boolean>
        get() = _getLoan

    fun getLoan() {
        _getLoan.value = true
    }

    private val _calculateLoan = MutableLiveData<Boolean>(false)
    val calculateLoan: LiveData<Boolean>
        get() = _calculateLoan

    fun calculateLoan() {
        _calculateLoan.value = true
    }

    private val _openCalender = MutableLiveData<Boolean>(false)
    val openCalender: LiveData<Boolean>
        get() = _openCalender

    fun openCalender() {
        _openCalender.value = true
    }
}
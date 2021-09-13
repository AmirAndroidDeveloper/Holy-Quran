package com.example.holyquran

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.holyquran.data.database.LoanDAO
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.ui.addUser.AddUserViewModel
import com.example.holyquran.ui.mainPage.MainFragmentViewModel
import com.example.holyquran.ui.userList.UserListViewModel
import com.example.holyquran.ui.userList.transactions.decreaseMoney.DecreaseViewModel
import com.example.holyquran.ui.userList.transactions.loan.getLoan.GetLoanViewModel
import com.example.holyquran.ui.userList.transactions.increaseMoney.IncreaseMoneyViewModel
import com.example.holyquran.ui.userList.transactions.loan.loanHistory.LoanHistoryViewModel
import com.example.holyquran.ui.userList.transactions.transactionHistory.TransactionHistoryViewModel

class ViewModelProviderFactory (
    private val dataSourceUser: UserDAO,
    private val dataSourceTransactions: TransactionsDAO,
    private val dataSourceLoan: LoanDAO,

    private val application: Application
    ): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserListViewModel::class.java)) {
            return UserListViewModel(dataSourceUser,dataSourceTransactions, dataSourceLoan,application) as T
        } else if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)) {
            return MainFragmentViewModel(dataSourceUser,dataSourceTransactions,dataSourceLoan, application) as T
        } else if (modelClass.isAssignableFrom(AddUserViewModel::class.java)) {
            return AddUserViewModel(dataSourceUser,dataSourceTransactions,dataSourceLoan, application) as T
        } else if (modelClass.isAssignableFrom(IncreaseMoneyViewModel::class.java)) {
            return IncreaseMoneyViewModel(dataSourceUser,dataSourceTransactions,dataSourceLoan,application) as T
        } else if (modelClass.isAssignableFrom(DecreaseViewModel::class.java)) {
            return DecreaseViewModel(dataSourceUser,dataSourceTransactions,dataSourceLoan,application) as T
        } else if (modelClass.isAssignableFrom(TransactionHistoryViewModel::class.java)) {
            return TransactionHistoryViewModel(dataSourceUser,dataSourceTransactions,dataSourceLoan,application) as T
        } else if (modelClass.isAssignableFrom(GetLoanViewModel::class.java)) {
            return GetLoanViewModel(dataSourceUser,dataSourceTransactions,dataSourceLoan,application) as T
        } else if (modelClass.isAssignableFrom(LoanHistoryViewModel::class.java)) {
            return LoanHistoryViewModel(dataSourceUser,dataSourceTransactions,dataSourceLoan,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}
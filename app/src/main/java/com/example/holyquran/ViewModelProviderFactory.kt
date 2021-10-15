package com.example.holyquran

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.holyquran.data.database.BankDAO
import com.example.holyquran.data.database.LoanDAO
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.ui.addUser.AddUserViewModel
import com.example.holyquran.ui.banks.addBank.AddBankViewModel
import com.example.holyquran.ui.banks.bankDetail.BankDetailViewModel
import com.example.holyquran.ui.banks.bankList.BankListViewModel
import com.example.holyquran.ui.mainPage.MainFragmentViewModel
import com.example.holyquran.ui.userList.UserListViewModel
import com.example.holyquran.ui.decreaseMoney.DecreaseViewModel
import com.example.holyquran.ui.editBankInfo.EditBankInfoViewModel
import com.example.holyquran.ui.editUserInfo.EditUserInfoViewModel
import com.example.holyquran.ui.loan.getLoan.GetLoanViewModel
import com.example.holyquran.ui.increaseMoney.IncreaseMoneyViewModel
import com.example.holyquran.ui.loan.loanDetails.LoanDetailViewModel
import com.example.holyquran.ui.loan.loanHistory.LoanHistoryViewModel
import com.example.holyquran.ui.loan.loanList.LoanListViewModel
import com.example.holyquran.ui.loan.payments.PaymentViewModel
import com.example.holyquran.ui.payPayments.PayPaymentsViewModel
import com.example.holyquran.ui.popupWindow.PopupViewModel
import com.example.holyquran.ui.transactionHistory.TransactionHistoryViewModel

class ViewModelProviderFactory(
    private val dataSourceUser: UserDAO,
    private val dataSourceTransactions: TransactionsDAO,
    private val dataSourceLoan: LoanDAO,
    private val dataSourceBank: BankDAO,

    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserListViewModel::class.java)) {
            return UserListViewModel(
                dataSourceUser,
                dataSourceTransactions,
                dataSourceLoan,
                dataSourceBank,
                application
            ) as T
        } else if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)) {
            return MainFragmentViewModel(
                dataSourceUser,
                dataSourceTransactions,
                dataSourceLoan,
                dataSourceBank,
                application
            ) as T
        } else if (modelClass.isAssignableFrom(AddUserViewModel::class.java)) {
            return AddUserViewModel(
                dataSourceUser,
                dataSourceTransactions,
                dataSourceLoan,
                dataSourceBank,
                application
            ) as T
        } else if (modelClass.isAssignableFrom(IncreaseMoneyViewModel::class.java)) {
            return IncreaseMoneyViewModel(
                dataSourceUser,
                dataSourceTransactions,
                dataSourceLoan,
                dataSourceBank,
                application
            ) as T
        } else if (modelClass.isAssignableFrom(DecreaseViewModel::class.java)) {
            return DecreaseViewModel(
                dataSourceUser,
                dataSourceTransactions,
                dataSourceLoan,
                dataSourceBank,
                application
            ) as T
        } else if (modelClass.isAssignableFrom(TransactionHistoryViewModel::class.java)) {
            return TransactionHistoryViewModel(
                dataSourceUser,
                dataSourceTransactions,
                dataSourceLoan,
                dataSourceBank,
                application
            ) as T
        } else if (modelClass.isAssignableFrom(GetLoanViewModel::class.java)) {
            return GetLoanViewModel(
                dataSourceUser,
                dataSourceTransactions,
                dataSourceLoan,
                dataSourceBank,
                application
            ) as T
        } else if (modelClass.isAssignableFrom(LoanHistoryViewModel::class.java)) {
            return LoanHistoryViewModel(
                dataSourceUser,
                dataSourceTransactions,
                dataSourceLoan,
                dataSourceBank,
                application
            ) as T
        } else if (modelClass.isAssignableFrom(LoanDetailViewModel::class.java)) {
            return LoanDetailViewModel(
                dataSourceUser,
                dataSourceTransactions,
                dataSourceLoan,
                dataSourceBank,
                application
            ) as T
        } else if (modelClass.isAssignableFrom(PaymentViewModel::class.java)) {
            return PaymentViewModel(
                dataSourceUser,
                dataSourceTransactions,
                dataSourceLoan,
                dataSourceBank,
                application
            ) as T
        } else if (modelClass.isAssignableFrom(BankListViewModel::class.java)) {
            return BankListViewModel(
                dataSourceUser,
                dataSourceTransactions,
                dataSourceLoan,
                dataSourceBank,
                application
            ) as T
        } else if (modelClass.isAssignableFrom(AddBankViewModel::class.java)) {
            return AddBankViewModel(
                dataSourceUser,
                dataSourceTransactions,
                dataSourceLoan,
                dataSourceBank,
                application
            ) as T
        } else if (modelClass.isAssignableFrom(LoanListViewModel::class.java)) {
            return LoanListViewModel(
                dataSourceUser,
                dataSourceTransactions,
                dataSourceLoan,
                dataSourceBank,
                application
            ) as T
        } else if (modelClass.isAssignableFrom(BankDetailViewModel::class.java)) {
            return BankDetailViewModel(
                dataSourceUser,
                dataSourceTransactions,
                dataSourceLoan,
                dataSourceBank,
                application
            ) as T
        } else if (modelClass.isAssignableFrom(EditUserInfoViewModel::class.java)) {
            return EditUserInfoViewModel(
                dataSourceUser,
                dataSourceTransactions,
                dataSourceLoan,
                dataSourceBank,
                application
            ) as T
        } else if (modelClass.isAssignableFrom(EditBankInfoViewModel::class.java)) {
            return EditBankInfoViewModel(
                dataSourceUser,
                dataSourceTransactions,
                dataSourceLoan,
                dataSourceBank,
                application
            ) as T
        } else if (modelClass.isAssignableFrom(PopupViewModel::class.java)) {
            return PopupViewModel(
                dataSourceUser,
                dataSourceTransactions,
                dataSourceLoan,
                dataSourceBank,
                application
            ) as T
    } else if (modelClass.isAssignableFrom(PayPaymentsViewModel::class.java)) {
            return PayPaymentsViewModel(
                dataSourceUser,
                dataSourceTransactions,
                dataSourceLoan,
                dataSourceBank,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}
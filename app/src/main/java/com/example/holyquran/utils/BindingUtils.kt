package com.example.holyquran.utils

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.holyquran.data.model.Bank
import com.example.holyquran.data.model.Loan
import com.example.holyquran.data.model.Transaction
import com.example.holyquran.data.model.UserInfo
import com.example.holyquran.ui.banks.bankList.BankAdapter
import com.example.holyquran.ui.loan.loanHistory.LoanAdapter
import com.example.holyquran.ui.loan.loanList.LoanListAdapter
import com.example.holyquran.ui.transactionHistory.increaseHistoryAdapter
import com.example.holyquran.ui.userList.UserAdapter

@BindingAdapter("userList")
fun userList(
    recyclerView: RecyclerView,
    list: List<UserInfo>?
) {
    (recyclerView.adapter as UserAdapter?)?.submitList(list)
    (recyclerView.adapter as UserAdapter?)?.notifyDataSetChanged()
}

@BindingAdapter("increaseList")
fun increaseList(
    recyclerView: RecyclerView,
    list: List<Transaction>?
) {
    (recyclerView.adapter as increaseHistoryAdapter?)?.submitList(list)
    (recyclerView.adapter as increaseHistoryAdapter?)?.notifyDataSetChanged()
}

@BindingAdapter("loanList")
fun loanList(
    recyclerView: RecyclerView,
    list: List<Loan>?
) {
    (recyclerView.adapter as LoanListAdapter?)?.submitList(list)
    (recyclerView.adapter as LoanListAdapter?)?.notifyDataSetChanged()
}

@BindingAdapter("bankList")
fun bankList(
    recyclerView: RecyclerView,
    list: List<Bank>?
) {
    (recyclerView.adapter as BankAdapter?)?.submitList(list)
    (recyclerView.adapter as BankAdapter?)?.notifyDataSetChanged()
}

@BindingAdapter("loanHistory")
fun loanHistory(
    recyclerView: RecyclerView,
    list: List<Loan>?
) {
    (recyclerView.adapter as LoanAdapter?)?.submitList(list)
    (recyclerView.adapter as LoanAdapter?)?.notifyDataSetChanged()
}
@BindingAdapter("android:onLongClick")
fun setOnLongClickListener(view: View, block : () -> Unit) {
    view.setOnLongClickListener {
        block()
        return@setOnLongClickListener true
    }
}
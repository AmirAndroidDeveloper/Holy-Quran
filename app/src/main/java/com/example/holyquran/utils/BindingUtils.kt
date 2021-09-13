package com.example.holyquran.utils

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.holyquran.data.model.Loan
import com.example.holyquran.data.model.Transaction
import com.example.holyquran.data.model.UserInfo
import com.example.holyquran.ui.userList.UserAdapter
import com.example.holyquran.ui.userList.transactions.loan.loanHistory.LoanAdapter
import com.example.holyquran.ui.userList.transactions.transactionHistory.increaseHistoryAdapter

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
    Log.d("TAG", "loanList: testTest")
    (recyclerView.adapter as LoanAdapter?)?.submitList(list)
    (recyclerView.adapter as LoanAdapter?)?.notifyDataSetChanged()
}


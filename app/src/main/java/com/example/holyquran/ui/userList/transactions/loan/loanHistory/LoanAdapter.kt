package com.example.holyquran.ui.userList.transactions.loan.loanHistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.holyquran.data.model.Loan
import com.example.holyquran.data.model.Transaction
import com.example.holyquran.databinding.ItemUserLoanListBinding
import com.example.holyquran.databinding.ItemUserLoanListBindingImpl
import com.example.holyquran.databinding.ItemUserTransactionListBinding
import com.example.holyquran.ui.userList.transactions.loan.loanHistory.AdapterListener3
import com.example.holyquran.ui.userList.transactions.loan.loanHistory.BillDiffCallback


class LoanAdapter() : ListAdapter<Loan, RecyclerView.ViewHolder>(BillDiffCallback()) {
    private val ITEM_VIEW_TYPE_EMPTY = 0
    private val ITEM_VIEW_TYPE_ITEM = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            ITEM_VIEW_TYPE_EMPTY -> EmptyViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val item = getItem(position)
                holder.bind(item, clickListener2)
            }
            is EmptyViewHolder -> {
                holder.bind()
            }
        }
    }

    lateinit var clickListener2: AdapterListener3
    fun setOnclickListener(listener: AdapterListener3) {
        clickListener2 = listener
    }


    override fun getItemViewType(position: Int): Int {
        return if (itemCount > 0)
            ITEM_VIEW_TYPE_ITEM
        else
            ITEM_VIEW_TYPE_EMPTY
    }

    class ViewHolder private constructor(val binding: ItemUserLoanListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Loan, adapterListener2: AdapterListener3) {
            binding.loan = item
            binding.clickListener = adapterListener2
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUserLoanListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class EmptyViewHolder private constructor(val binding: ItemUserLoanListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): EmptyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUserLoanListBinding.inflate(layoutInflater, parent, false)
                return EmptyViewHolder(binding)
            }
        }
    }
}

class BillDiffCallback : DiffUtil.ItemCallback<Loan>() {
    override fun areItemsTheSame(oldItem: Loan, newItem: Loan): Boolean {
        return oldItem.loanId == newItem.loanId
    }

    override fun areContentsTheSame(
        oldItem: Loan,
        newItem: Loan
    ): Boolean {
        return oldItem == newItem
    }
}

class AdapterListener3(
    val clickListener2: (id: Long) -> Unit,
    val deleteListener: (category: Loan) -> Unit

) {
    fun onclick(category: Loan) = clickListener2(category.userId)
    fun onDeleteClick(userInfo: Loan) = deleteListener(userInfo)

}

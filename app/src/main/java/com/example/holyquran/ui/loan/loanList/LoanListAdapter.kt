package com.example.holyquran.ui.loan.loanList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.holyquran.data.model.Loan
import com.example.holyquran.databinding.ItemLoanBinding
import java.text.DecimalFormat
import java.text.NumberFormat


class LoanListAdapter() : ListAdapter<Loan, RecyclerView.ViewHolder>(BillDiffCallback()) {
    private val ITEM_VIEW_TYPE_EMPTY = 0
    private val ITEM_VIEW_TYPE_ITEM = 1
    var id = 0L
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
                holder.bind(item, clickListener)
            }
            is EmptyViewHolder -> {
                holder.bind()
            }
        }
    }

    lateinit var clickListener: AdapterListener
    fun setOnclickListener(listener: AdapterListener) {
        clickListener = listener
    }


    override fun getItemViewType(position: Int): Int {
        return if (itemCount > 0)
            ITEM_VIEW_TYPE_ITEM
        else
            ITEM_VIEW_TYPE_EMPTY
    }

    class ViewHolder private constructor(val binding: ItemLoanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Loan, adapterListenerLoan: AdapterListener) {
            binding.loan = item
            binding.clickListenerLoan = adapterListenerLoan
            binding.executePendingBindings()

            if (binding.amount.text.isNotEmpty()) {
                val formatter: NumberFormat = DecimalFormat("#,###,###,###")
                binding.amount.setText("" + formatter.format(item.amount.toLong()))
            }

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemLoanBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class EmptyViewHolder private constructor(val binding: ItemLoanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): EmptyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemLoanBinding.inflate(layoutInflater, parent, false)
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

class AdapterListener(
    val clickListener: (id: Long) -> Unit,
    val deleteListener: (loan: Loan) -> Unit

) {
    fun onclick(loan: Loan) = clickListener(loan.userId)
    fun onDeleteClick(loan: Loan) = deleteListener(loan)

}

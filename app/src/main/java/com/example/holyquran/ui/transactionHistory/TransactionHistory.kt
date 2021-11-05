package com.example.holyquran.ui.transactionHistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.holyquran.data.model.Transactions
import com.example.holyquran.data.model.TransactionAndBank
import com.example.holyquran.databinding.ItemUserTransactionListBinding

class TransactionHistory() :
    ListAdapter<TransactionAndBank, RecyclerView.ViewHolder>(BillDiffCallback()) {
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
                holder.bind(item, clickListener)
            }
            is EmptyViewHolder -> {
                holder.bind()
            }
        }
    }

    lateinit var clickListener: AdapterListener2
    fun setOnclickListener(listener: AdapterListener2) {
        clickListener = listener
    }


    override fun getItemViewType(position: Int): Int {
        return if (itemCount > 0)
            ITEM_VIEW_TYPE_ITEM
        else
            ITEM_VIEW_TYPE_EMPTY
    }

    class ViewHolder
    private constructor(val binding: ItemUserTransactionListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TransactionAndBank, adapterListener2: AdapterListener2) {
            binding.transaction = item
            binding.clickListener = adapterListener2
            binding.executePendingBindings()
            if (item.type == "payPayment") {
                binding.transactionStatus.text = "برداخت قسط"
            } else if (item.type == "decrease") {
                binding.transactionStatus.text = "برداشت"
            } else if (item.type == "increase") {
                binding.transactionStatus.text = "واریز"
            }else if (item.type=="firstMoney"){
                binding.transactionStatus.text="افتتاحیه"
            }

            if (item.decrease == null) {
                binding.amount.text = item.increase
            } else {
                binding.amount.text = item.decrease
            }
            binding.bank.text = item.bankName
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUserTransactionListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class EmptyViewHolder private constructor(val binding: ItemUserTransactionListBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind() {
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): EmptyViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemUserTransactionListBinding.inflate(layoutInflater, parent, false)
            return EmptyViewHolder(binding)
        }
    }
}


class BillDiffCallback : DiffUtil.ItemCallback<TransactionAndBank>() {
    override fun areItemsTheSame(
        oldItem: TransactionAndBank,
        newItem: TransactionAndBank
    ): Boolean {
        return oldItem.bankName == newItem.bankName
    }

    override fun areContentsTheSame(
        oldItem: TransactionAndBank,
        newItem: TransactionAndBank
    ): Boolean {
        return oldItem == newItem
    }
}

class AdapterListener2(
    val clickListener: (id: Long?) -> Unit,
    val deleteListener: (transaction: TransactionAndBank) -> Unit

) {
    fun onclick(transactions: Transactions) = clickListener(transactions.userId)
    fun onDeleteClick(transaction: TransactionAndBank) = deleteListener(transaction)

}

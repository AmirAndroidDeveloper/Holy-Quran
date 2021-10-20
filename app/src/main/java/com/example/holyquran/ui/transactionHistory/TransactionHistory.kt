package com.example.holyquran.ui.transactionHistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.holyquran.data.model.Bank
import com.example.holyquran.data.model.Transaction
import com.example.holyquran.databinding.ItemUserTransactionListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionHistory() :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(BillDiffCallback()) {
    private val ITEM_VIEW_TYPE_EMPTY = 0
    private val ITEM_VIEW_TYPE_ITEM_TRANSACTION = 1
    private val ITEM_VIEW_TYPE_ITEM_BANK = 2

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    /**
     * DO NOT USE .submit(), use the method bellow
     */

    fun addTransactionsAndBanks(transactionList: List<Transaction>?, bankList: List<Bank>?) {
        adapterScope.launch {
            val transactionItems: List<DataItem> = when {
                transactionList == null || transactionList.isEmpty() -> listOf(DataItem.Empty)
                else -> transactionList.map { DataItem.TransactionItem(it) }
            }
            val bankItems: List<DataItem> = when {
                bankList == null || bankList.isEmpty() -> listOf(DataItem.Empty)
                else -> bankList.map { DataItem.BankItem(it) }
            }

            val items = transactionItems + bankItems
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ITEM_TRANSACTION -> ViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM_BANK -> ViewHolder.from(parent)
            ITEM_VIEW_TYPE_EMPTY -> EmptyViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                when (val item = getItem(position)) {
                    is DataItem.TransactionItem -> holder.bind(item.transaction, clickListener)
                    is DataItem.BankItem -> holder.bind2(item.bank, clickListener)
                }
            }
            is EmptyViewHolder -> holder.bind()
        }
    }

    lateinit var clickListener: AdapterListener2
    fun setOnclickListener(listener: AdapterListener2) {
        clickListener = listener
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Empty -> ITEM_VIEW_TYPE_EMPTY
            is DataItem.TransactionItem -> ITEM_VIEW_TYPE_ITEM_TRANSACTION
            is DataItem.BankItem -> ITEM_VIEW_TYPE_ITEM_BANK
        }
    }

    class ViewHolder
    private constructor(val binding: ItemUserTransactionListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Transaction, adapterListener2: AdapterListener2) {
            binding.transaction = item
            binding.clickListener = adapterListener2
            binding.executePendingBindings()
            if (item.type == "payPayment") {
                binding.transactionStatus.text = "برداخت قسط"
            } else if (item.type == "decrease") {
                binding.transactionStatus.text = "برداشت"
            } else if (item.type == "increase") {
                binding.transactionStatus.text = "واریز"
            }

            if (item.decrease == null) {
                binding.amount.text = item.increase
            } else {
                binding.amount.text = item.decrease
            }
        }

        fun bind2(item2: Bank, adapterListener2: AdapterListener2) {
            binding.bankInfo = item2
            binding.clickListener = adapterListener2
            binding.executePendingBindings()
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


class BillDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

class AdapterListener2(
    val clickListener: (id: Long?) -> Unit,
    val deleteListener: (category: Transaction) -> Unit

) {
    fun onclick(transaction: Transaction) = clickListener(transaction.userId)
    fun onDeleteClick(userInfo: Transaction) = deleteListener(userInfo)

}

sealed class DataItem {
    abstract val id: Long

    data class TransactionItem(val transaction: Transaction) : DataItem() {
        override val id = transaction.transId
    }

    data class BankItem(val bank: Bank) : DataItem() {
        override val id = bank.bankId
    }

    object Empty : DataItem() {
        override val id = Long.MIN_VALUE
    }
}

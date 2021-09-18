package com.example.holyquran.ui.loan.loanDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.holyquran.data.model.Loan
import com.example.holyquran.databinding.ItemPaymentBinding


class PaymentAdapter() : ListAdapter<Loan, RecyclerView.ViewHolder>(BillDiffCallback()) {
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

    lateinit var clickListener2: AdapterListener6
    fun setOnclickListener(listener: AdapterListener6) {
        clickListener2 = listener
    }


    override fun getItemViewType(position: Int): Int {
        return if (itemCount > 0)
            ITEM_VIEW_TYPE_ITEM
        else
            ITEM_VIEW_TYPE_EMPTY
    }

    class ViewHolder private constructor(val binding: ItemPaymentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Loan, adapterListener2: AdapterListener6) {
            binding.loan = item
            binding.clickListener2 = adapterListener2
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPaymentBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class EmptyViewHolder private constructor(val binding: ItemPaymentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): EmptyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPaymentBinding.inflate(layoutInflater, parent, false)
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

class AdapterListener6(
    val clickListener2: (id: Long) -> Unit,
    val deleteListener: (category: Loan) -> Unit

) {
    fun onclick(category: Loan) = clickListener2(category.userId)
    fun onDeleteClick(userInfo: Loan) = deleteListener(userInfo)

}

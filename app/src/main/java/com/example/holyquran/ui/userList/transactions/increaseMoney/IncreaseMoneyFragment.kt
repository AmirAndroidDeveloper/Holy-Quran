package com.example.holyquran.ui.userList.transactions.increaseMoney

import android.os.Bundle
import android.util.Log
import net.objecthunter.exp4j.ExpressionBuilder
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import android.view.*
import com.example.holyquran.databinding.FragmentIncreaseMoneyBinding


class IncreaseMoneyFragment : Fragment() {
    var id: Long = 0L
    lateinit var mIncreaseMoneyBinding: FragmentIncreaseMoneyBinding
    lateinit var mIncreaseMoneyViewModel: IncreaseMoneyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mIncreaseMoneyBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_increase_money,
                container,
                false
            )
        val application = requireNotNull(this.activity).application
        val userDAO = UserDatabase.getInstance(application).mUserDAO
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val viewModelFactory = ViewModelProviderFactory(userDAO, transactionDAO, application)

        mIncreaseMoneyViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(IncreaseMoneyViewModel::class.java)

        val arg =
            IncreaseMoneyFragmentArgs.fromBundle(
                requireArguments())
        id = arg.userIdIncrease
        Log.d("TAG", "onCreateView: $id")
        mIncreaseMoneyViewModel.setUserName(id)?.observe(viewLifecycleOwner, {
            mIncreaseMoneyViewModel.setUserName(it)
        })
        mIncreaseMoneyViewModel.userName.observe(viewLifecycleOwner, {
            if (it != null) {
                mIncreaseMoneyBinding.userName = it
            }
        })
        val increase = mIncreaseMoneyViewModel.sumUserIncrease(id).toString()
        val decrease = mIncreaseMoneyViewModel.sumUserDecrease(id).toString()
        val minus = "-"
        try {
            val expression = ExpressionBuilder(increase + minus + decrease).build()
            val result = expression.evaluate()
            val longResult = result.toLong()
            if (result == longResult.toDouble())
                mIncreaseMoneyBinding.totalMoney.text = longResult.toString()
            else
                mIncreaseMoneyBinding.totalMoney.text = result.toString()

        } catch (e: Exception) {
            Log.d("Exception", " message : " + e.message)
        }

        mIncreaseMoneyBinding.finishBtn.setOnClickListener {
            mIncreaseMoneyViewModel.insertMoney(
                mIncreaseMoneyBinding.increaseEDT.text.toString(),
                id
            )
        }
        mIncreaseMoneyBinding.decreaseBtn.setOnClickListener {
            this.findNavController().navigate(
                IncreaseMoneyFragmentDirections.actionIncreaseMoneyFragmentToDecreaseMoneyFragment(
                    id
                )
            )
        }
        mIncreaseMoneyViewModel.setIncrease(id)?.observe(viewLifecycleOwner, {
            if (it != null) {
                mIncreaseMoneyViewModel.setIncrease(it)
            }
        })
        mIncreaseMoneyViewModel.increase.observe(viewLifecycleOwner, {
            if (it != null) {
                if (id == mIncreaseMoneyViewModel.increase.value?.userId) {
                    Toast.makeText(activity, "Match", Toast.LENGTH_SHORT).show()
                    mIncreaseMoneyBinding.userMoney.text = it.increase

                    if (mIncreaseMoneyBinding.totalMoney.text == "0") {
                        mIncreaseMoneyBinding.userMoney.text = it.increase
                    }
                }
                mIncreaseMoneyBinding.userMoney.text =
                    mIncreaseMoneyViewModel.sumUserIncrease(id).toString()
                //                mIncreaseMoneyBinding.userMoney.text = it.increase.toString() + it.increase.toString()
            }
        })
        setHasOptionsMenu(true)
        return mIncreaseMoneyBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.loan_menu, menu);

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.loanMoney -> {
                this.findNavController().navigate(
                    IncreaseMoneyFragmentDirections.actionIncreaseMoneyFragmentToIncreaseHistoryFragment(id)
                )
                Toast.makeText(activity, "تاریخچه تراکنش ها", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
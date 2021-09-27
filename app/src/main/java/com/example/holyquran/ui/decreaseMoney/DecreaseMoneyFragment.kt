package com.example.holyquran.ui.decreaseMoney

import NumberTextWatcherForThousand
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentDecreaseMoneyBinding

class DecreaseMoneyFragment : Fragment() {
    var id: Long = 0L
    lateinit var mDecreaseMoneyBinding: FragmentDecreaseMoneyBinding
    lateinit var mDecreaseMoneyViewModel: DecreaseViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDecreaseMoneyBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_decrease_money,
                container,
                false
            )
        val application = requireNotNull(this.activity).application
        val userDAO = UserDatabase.getInstance(application).mUserDAO
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val loanDAO = UserDatabase.getInstance(application).mLoanDAO
        val bankDAO = UserDatabase.getInstance(application).mBankDAO
        val viewModelFactory =
            ViewModelProviderFactory(userDAO, transactionDAO, loanDAO,bankDAO, application)
        mDecreaseMoneyViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(DecreaseViewModel::class.java)
        mDecreaseMoneyBinding.decreaseMoneyViewModel = mDecreaseMoneyViewModel
        this.also { mDecreaseMoneyBinding.lifecycleOwner = it }

        val arg =
        DecreaseMoneyFragmentArgs.fromBundle(
                requireArguments()
            )
        id = arg.userIdDecrease
        mDecreaseMoneyViewModel.setUserName(id)?.observe(viewLifecycleOwner, {
            mDecreaseMoneyViewModel.setUserName(it)
        })
        mDecreaseMoneyViewModel.userName.observe(viewLifecycleOwner, {
            if (it != null) {
                mDecreaseMoneyBinding.userName = it
            }
        })
        mDecreaseMoneyViewModel.decreaseMoney.observe(viewLifecycleOwner, Observer {
            val removeComma=NumberTextWatcherForThousand.trimCommaOfString(mDecreaseMoneyBinding.decreaseEdt.text.toString()).replace(",", "")
            mDecreaseMoneyBinding.decreaseEdt.addTextChangedListener(NumberTextWatcherForThousand(mDecreaseMoneyBinding.decreaseEdt))
            if (it == true) {
                mDecreaseMoneyViewModel.decreaseMoney(
                    removeComma,
                    false,
                    id
                )
            }
        })
        mDecreaseMoneyViewModel.setDecrease(id)?.observe(viewLifecycleOwner, {
            if (it != null) {
                mDecreaseMoneyViewModel.setDecrease(it)
            }
        mDecreaseMoneyViewModel.decreaseMoneyDone()
        })
        mDecreaseMoneyViewModel.decrease.observe(viewLifecycleOwner, {
            if (it != null) {
                if (id == mDecreaseMoneyViewModel.decrease.value?.userId) {
                    Toast.makeText(activity, "Match", Toast.LENGTH_SHORT).show()
                    mDecreaseMoneyBinding.userMoney.text =it.increase
                }
                mDecreaseMoneyBinding.userMoney.text =
                    mDecreaseMoneyViewModel.sumUserDecrease(id).toString()
                //                mIncreaseMoneyBinding.userMoney.text = it.increase.toString() + it.increase.toString()
            }
        })
        return mDecreaseMoneyBinding.root
    }
}
package com.example.holyquran.ui.userList.transactions.decreaseMoney

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.data.model.UserInfo
import com.example.holyquran.databinding.FragmentDecreaseMoneyBinding
import com.example.holyquran.ui.addUser.AddUserFragmentDirections
import com.example.holyquran.ui.userList.transactions.increaseMoney.IncreaseMoneyFragmentDirections

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
        val viewModelFactory =
            ViewModelProviderFactory(userDAO, transactionDAO, loanDAO, application)

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
        Log.d("TAG", "onCreateView: $id")
        mDecreaseMoneyViewModel.setUserName(id)?.observe(viewLifecycleOwner, {
            mDecreaseMoneyViewModel.setUserName(it)
        })
        mDecreaseMoneyViewModel.userName.observe(viewLifecycleOwner, {
            if (it != null) {
                mDecreaseMoneyBinding.userName = it
            }
        })

        mDecreaseMoneyViewModel.decreaseMoney.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                mDecreaseMoneyViewModel.decreaseMoney(
                    mDecreaseMoneyBinding.decreaseEdt.text.toString(),
                    id
                )
            }
        })
        mDecreaseMoneyViewModel.setDecrease(id)?.observe(viewLifecycleOwner, {
            if (it != null) {
                mDecreaseMoneyViewModel.setDecrease(it)
            }
        })
        mDecreaseMoneyViewModel.decrease.observe(viewLifecycleOwner, {
            if (it != null) {
                if (id == mDecreaseMoneyViewModel.decrease.value?.userId) {
                    Toast.makeText(activity, "Match", Toast.LENGTH_SHORT).show()
                    mDecreaseMoneyBinding.userMoney.text = "-" + it.increase
                }
                mDecreaseMoneyBinding.userMoney.text =
                    mDecreaseMoneyViewModel.sumUserDecrease(id).toString()
                //                mIncreaseMoneyBinding.userMoney.text = it.increase.toString() + it.increase.toString()
            }
        })
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            view?.findNavController()
                ?.navigate(DecreaseMoneyFragmentDirections.actionDecreaseMoneyFragmentToIncreaseMoneyFragment(id)
            )
        }
        return mDecreaseMoneyBinding.root
    }
}
package com.example.holyquran.ui.userList.increaseMoney

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentIncreaseMoneyBinding

class IncreaseMoneyFragment : Fragment() {
    var id: Long = 0L
    val five = 5
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
        val arg = IncreaseMoneyFragmentArgs.fromBundle(requireArguments())
        id = arg.userId
        Log.d("TAG", "onCreateView: $id")
        mIncreaseMoneyViewModel.setUserName(id)?.observe(viewLifecycleOwner, {
            mIncreaseMoneyViewModel.setUserName(it)
        })
        mIncreaseMoneyViewModel.userName.observe(viewLifecycleOwner, {
            if (it != null) {
                mIncreaseMoneyBinding.userName = it
            }
        })

        mIncreaseMoneyBinding.finishBtn.setOnClickListener {
            mIncreaseMoneyViewModel.insertMoney(
                mIncreaseMoneyBinding.increaseEDT.text.toString(),
                id
            )
        }
        mIncreaseMoneyBinding.decreaseBtn.setOnClickListener {
            this.findNavController().navigate(
                IncreaseMoneyFragmentDirections.actionIncreaseMoneyFragmentToDecreaseMoneyFragment(1)
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
                }

                mIncreaseMoneyBinding.userMoney.text =
                    mIncreaseMoneyViewModel.sumUserIncrease(id).toString()
                //                mIncreaseMoneyBinding.userMoney.text = it.increase.toString() + it.increase.toString()
            }
        })
        return mIncreaseMoneyBinding.root
    }
}
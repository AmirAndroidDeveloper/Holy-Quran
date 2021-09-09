package com.example.holyquran.ui.userList.decrease

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentDecreaseMoneyBinding
import com.example.holyquran.ui.userList.increaseMoney.IncreaseMoneyFragmentArgs
import com.example.holyquran.ui.userList.increaseMoney.IncreaseMoneyViewModel

class DecreaseMoneyFragment : Fragment() {
    lateinit var mDecreaseMoneyBinding: FragmentDecreaseMoneyBinding
    lateinit var mDecreaseViewModel: DecreaseViewModel
    var id: Long = 0L
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
        val viewModelFactory = ViewModelProviderFactory(userDAO, transactionDAO, application)

        mDecreaseViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(DecreaseViewModel::class.java)
        val arg = DecreaseMoneyFragmentArgs.fromBundle(requireArguments())
        id = arg.userId
        Log.d("TAG", "onCreateView: $id")
        mDecreaseViewModel.setUserName(id)?.observe(viewLifecycleOwner, {
            mDecreaseViewModel.setUserName(it)
        })
        mDecreaseViewModel.userName.observe(viewLifecycleOwner, {
            if (it != null) {
                mDecreaseMoneyBinding.userName = it
            }
        })

        mDecreaseMoneyBinding.finishBtn.setOnClickListener {
            mDecreaseViewModel.insertMoney(
                mDecreaseMoneyBinding.increaseEDT.text.toString(),
                id
            )
        }
        return mDecreaseMoneyBinding.root

    }
}
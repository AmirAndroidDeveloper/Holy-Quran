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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.data.model.Transaction
import com.example.holyquran.data.model.UserInfo
import com.example.holyquran.databinding.FragmentIncreaseMoneyBinding
import com.example.holyquran.ui.addUser.AddUserFragmentDirections
import com.example.holyquran.ui.userList.UserListFragmentDirections
import com.google.android.material.snackbar.Snackbar

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
              IncreaseMoneyFragmentDirections.actionIncreaseMoneyFragmentToDecreaseMoneyFragment(1))

      }
        return mIncreaseMoneyBinding.root
    }

    private fun deleteDialog(trans: UserInfo) {
        Snackbar.make(mIncreaseMoneyBinding.root, "آیا تمایل به حذف تراکنش دارید؟ ", Snackbar.LENGTH_LONG)
            .setAction("حذف") {
                mIncreaseMoneyViewModel.deleteTrans(trans)
            }
            .setActionTextColor(resources.getColor(android.R.color.holo_red_light))
            .show()
    }
}
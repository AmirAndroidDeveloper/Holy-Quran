package com.example.holyquran.ui.userList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.data.model.UserInfo
import com.example.holyquran.databinding.FragmentUserListBinding
import com.example.holyquran.ui.userList.transactions.UserTotalMoneyAdapter
import com.google.android.material.snackbar.Snackbar


class UserListFragment : Fragment() {
    lateinit var mUserListBinding: FragmentUserListBinding
    lateinit var mUserListViewModel: UserListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mUserListBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_user_list,
                container,
                false
            )
        val application = requireNotNull(this.activity).application
        val personalDAO = UserDatabase.getInstance(application).mUserDAO
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val viewModelFactory = ViewModelProviderFactory(personalDAO, transactionDAO, application)
        mUserListViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(UserListViewModel::class.java)
        mUserListBinding.viewModel = mUserListViewModel
        this.also { mUserListBinding.lifecycleOwner = it }

        mUserListViewModel.popUpWindow.observe(viewLifecycleOwner, Observer {
            if (it == true) {

            }
        })

        mUserListViewModel.goTOAddUser.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    UserListFragmentDirections.actionUserListFragmentToAddUserFragment()
                )
            }
        })
        val mUserAdapter = UserAdapter()
        mUserAdapter.setOnclickListener(AdapterListener({
            if (it != 0L)
                this.findNavController().navigate(
                    UserListFragmentDirections.actionUserListFragmentToIncreaseMoneyFragment(it)
                )
        }, {
            deleteDialog(it)
        }
        ))
        val mLinearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mUserListBinding.rvFeed.adapter = mUserAdapter
        mUserListBinding.rvFeed.layoutManager = mLinearLayoutManager
        userInfo()
        userTotalMoney()


       val mUserTotalMoney= UserTotalMoneyAdapter()



        return mUserListBinding.root
    }

    private fun userInfo() {
        mUserListViewModel.getUserList().observe(viewLifecycleOwner, {
            mUserListViewModel.userInfo.value = it
            Log.d("TAG", "viewHolder: $it")
        })
    }

    fun userTotalMoney() {
        mUserListViewModel.getUserTotalMoney().observe(viewLifecycleOwner, {
            mUserListViewModel.userTotalMoney.value = it
            Log.d("TAG", "viewHolder: $it")
        })
    }

    private fun deleteDialog(userInfo: UserInfo) {
        Snackbar.make(mUserListBinding.root, "آیا تمایل به حذف عضو دارید؟ ", Snackbar.LENGTH_LONG)
            .setAction("حذف") {
                mUserListViewModel.deleteCategory(userInfo)
            }
            .setActionTextColor(resources.getColor(android.R.color.holo_red_light))
            .show()
    }
}

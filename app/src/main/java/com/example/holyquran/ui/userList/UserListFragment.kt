package com.example.holyquran.ui.userList

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
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
        val loanDAO = UserDatabase.getInstance(application).mLoanDAO
        val bankDAO = UserDatabase.getInstance(application).mBankDAO
        val viewModelFactory =
            ViewModelProviderFactory(personalDAO, transactionDAO, loanDAO, bankDAO, application)
        mUserListViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(UserListViewModel::class.java)
        mUserListBinding.viewModel = mUserListViewModel
        this.also { mUserListBinding.lifecycleOwner = it }

        mUserListViewModel.goTOAddUser.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    UserListFragmentDirections.actionUserListFragmentToAddUserFragment()
                )
                mUserListViewModel.goToAddUserDone()
            }
        })
        val mUserAdapter = UserAdapter()
        mUserAdapter.setOnclickListener(AdapterListener({
            if (it != 0L)
                this.findNavController().navigate(
                    UserListFragmentDirections.actionUserListFragmentToIncreaseMoneyFragment(it)
                )
            Log.d("TAG", "navTeat $it ")
        }, {
            deleteDialog(it)
        }
        ) {
            if (it != 0L)
                this.findNavController().navigate(
                    UserListFragmentDirections.actionUserListFragmentToNavigationDialogFragment(it)
                )
            vibratePhone()

        })
        val mLinearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mUserListBinding.rvFeed.adapter = mUserAdapter
        mUserListBinding.rvFeed.layoutManager = mLinearLayoutManager
        userInfo()

        setHasOptionsMenu(true)
        return mUserListBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_all_user_user_list, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.deleteAllUsers -> {
                deleteAllUserDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun userInfo() {
        mUserListViewModel.getUserList().observe(viewLifecycleOwner, {
            mUserListViewModel.userInfo.value = it
            Log.d("TAG", "viewHolder: $it")
        })
    }

    private fun deleteDialog(userInfo: UserInfo) {
        Snackbar.make(
            mUserListBinding.root,
            "آیا تمایل به حذف عضو دارید؟ ",
            Snackbar.LENGTH_LONG
        )
            .setAction("حذف") {
                mUserListViewModel.deleteUser(userInfo)
            }
            .setActionTextColor(resources.getColor(android.R.color.holo_red_light))
            .show()
    }

    private fun deleteAllUserDialog() {
        Snackbar.make(
            mUserListBinding.root,
            "آیا تمایل به حذف تمام اعضا دارید؟ ",
            Snackbar.LENGTH_LONG
        )
            .setAction("حذف همه") {
                mUserListViewModel.deleteAllUsers()
            }
            .setActionTextColor(resources.getColor(android.R.color.holo_red_light))
            .show()
    }

    private fun Fragment.vibratePhone() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    50,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            vibrator.vibrate(50)
        }
    }
}
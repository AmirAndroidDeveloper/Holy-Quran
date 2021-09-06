package com.example.holyquran.ui.userList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.data.model.UserInfo
import com.example.holyquran.databinding.FragmentUserListBinding
import com.example.holyquran.ui.mainPage.MainFragmentViewModel
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
        val viewModelFactory = ViewModelProviderFactory(personalDAO, application)
        mUserListViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(UserListViewModel::class.java)
        mUserListBinding.viewModel = mUserListViewModel
        this.also { mUserListBinding.lifecycleOwner = it }
        mUserListViewModel.popUpWindow.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    UserListFragmentDirections.actionUserListFragmentToPopupWindowFragment()) } })

        mUserListViewModel.goTOAddUser.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    UserListFragmentDirections.actionUserListFragmentToAddUserFragment()
                )
            }
        })

        val mUserAdapter = UserAdapter()
        mUserAdapter.setOnclickListener(AdapterListener {
            deleteDialog(it)
        })
        val mLinearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mUserListBinding.rvFeed.adapter = mUserAdapter
        mUserListBinding.rvFeed.layoutManager = mLinearLayoutManager
        viewHolder()
        return mUserListBinding.root
    }

    private fun viewHolder() {
        mUserListViewModel.getUserList().observe(viewLifecycleOwner, {
            mUserListViewModel.userInfo.value = it
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

package com.example.holyquran.ui.mainPage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentMainPageBinding
import com.example.holyquran.ui.registerUser.RegisterUserViewModel

class MainPageFragment : Fragment() {
    var id: Long = 0L

    lateinit var mMainPageBinding: FragmentMainPageBinding
    lateinit var mMainViewModel: MainFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mMainPageBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main_page, container, false)


        val application = requireNotNull(this.activity).application
        val personalDAO = UserDatabase.getInstance(application).mUserDAO
        val viewModelFactory = ViewModelProviderFactory(personalDAO, application)
        mMainViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(MainFragmentViewModel::class.java)
        mMainPageBinding.viewModel = mMainViewModel
        this.also { mMainPageBinding.lifecycleOwner = it }


        val mCategoryAdapter = UserAdapter()
        mCategoryAdapter.setOnclickListener(AdapterListener ({
            if (it != 0L)
                this.findNavController().navigate(
                    MainPageFragmentDirections.actionMainPageFragmentToRegisterUserFragment(
                    )
                )
        },{
        }
        ))

        val mLinearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mMainPageBinding.rvFeed.adapter = mCategoryAdapter
        mMainPageBinding.rvFeed.layoutManager = mLinearLayoutManager
        viewHolder()
//        subscription6()

        return mMainPageBinding.root

    }

    private fun viewHolder() {
        mMainViewModel.getUserList().observe(viewLifecycleOwner, {
            Log.d("TAG", "rrr: ${it.toString()}")
            mMainViewModel.userInfo.value = it
        })    }

//    private fun subscription6() {
//        mMainViewModel.getUserInfo(id)?.observe(viewLifecycleOwner, {
//            mMainViewModel.setUserInfo(it)
//        })
//        mMainViewModel.userName.observe(viewLifecycleOwner, {
//            if (it != null) {
//                mMainPageBinding.userNameModel = it
//            }
//        })


}
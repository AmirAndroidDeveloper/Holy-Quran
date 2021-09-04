package com.example.holyquran.ui.mainPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentMainPageBinding
import com.example.holyquran.ui.registerUser.RegisterUserViewModel

class MainPageFragment : Fragment() {
    lateinit var mMainPageBinding: FragmentMainPageBinding
    lateinit var mMainViewModel: MainFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mMainPageBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main_page, container, false)



//        mMainPageBinding.viewModel.noNameTillKnow.observe(viewLifecycleOwner, Observer {
//            if (it){
//                findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddProductFragment(id))
//                mMainFragmentViewModel.showAddContactDone()
//            }
//        })
        return mMainPageBinding.root

    }
}
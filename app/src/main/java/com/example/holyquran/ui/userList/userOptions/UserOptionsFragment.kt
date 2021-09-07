package com.example.holyquran.ui.userList.userOptions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.holyquran.R
import com.example.holyquran.databinding.FragmentPopopWindowBinding
import com.example.holyquran.databinding.FragmentUserOptionsBinding
import com.example.holyquran.ui.userList.UserListFragmentDirections

class UserOptionsFragment : Fragment() {
    lateinit var mUserOptionsBinding: FragmentUserOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mUserOptionsBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_user_options,
                container,
                false
            )

        mUserOptionsBinding.increaseMoney.setOnClickListener {



        }

        return mUserOptionsBinding.root
    }

}
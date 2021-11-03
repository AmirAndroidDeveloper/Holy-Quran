package com.example.holyquran.ui.lockScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.holyquran.R
import com.example.holyquran.databinding.FragmentLockScreenBinding

class LockScreenFragment : Fragment() {
lateinit var mLockScreenBinding:FragmentLockScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       mLockScreenBinding=
           DataBindingUtil.inflate(layoutInflater,R.layout.fragment_lock_screen,container,false)

        var getEditTextValue= mLockScreenBinding.password.text.toString()



        return mLockScreenBinding.root
    }

}
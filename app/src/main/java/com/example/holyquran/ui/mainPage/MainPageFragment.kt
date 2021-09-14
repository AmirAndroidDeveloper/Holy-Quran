package com.example.holyquran.ui.mainPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentMainPageBinding
import android.widget.Toast

import com.example.holyquran.ui.MainActivity
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar




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
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val loanDAO = UserDatabase.getInstance(application).mLoanDAO

        val viewModelFactory =
            ViewModelProviderFactory(personalDAO, transactionDAO, loanDAO, application)
        mMainViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(MainFragmentViewModel::class.java)
        mMainPageBinding.viewModel = mMainViewModel
        this.also { mMainPageBinding.lifecycleOwner = it }



//        val now = PersianCalendar()
//        val dpd: DatePickerDialog = DatePickerDialog.newInstance(
//            object : DatePickerDialog.OnDateSetListener {
//                override fun onDateSet(
//                    view: DatePickerDialog?,
//                    year: Int,
//                    monthOfYear: Int,
//                    dayOfMonth: Int
//                ) {
//                    Toast.makeText(
//                        activity,
//                        "$year/$monthOfYear/$dayOfMonth",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            },
//            now.persianYear,
//            now.persianMonth,
//            now.persianDay
//        )
//        dpd.setThemeDark(false)
//        dpd.show(requireActivity().fragmentManager, "FuLLKade")
//

        return mMainPageBinding.root

    }
}
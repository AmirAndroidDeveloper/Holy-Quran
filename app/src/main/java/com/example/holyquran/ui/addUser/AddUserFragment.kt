package com.example.holyquran.ui.addUser

import NumberTextWatcherForThousand
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentAddUserBinding
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import saman.zamani.persiandate.PersianDateFormat
import saman.zamani.persiandate.PersianDate
import android.app.Activity
import android.util.Log
import android.view.WindowManager
import android.widget.ArrayAdapter
import com.example.holyquran.ui.mainPage.MainFragmentViewModel
import java.util.ArrayList


class AddUserFragment : Fragment() {

    private lateinit var mAddUserListBinding: FragmentAddUserBinding
    lateinit var mAddUserViewModel: AddUserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mAddUserListBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_add_user, container, false)
        val application = requireNotNull(this.activity).application
        val personalDAO = UserDatabase.getInstance(application).mUserDAO
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val loanDAO = UserDatabase.getInstance(application).mLoanDAO
        val bankDAO = UserDatabase.getInstance(application).mBankDAO
        val viewModelFactory =
            ViewModelProviderFactory(personalDAO, transactionDAO, loanDAO, bankDAO, application)
        mAddUserViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(AddUserViewModel::class.java)
        mAddUserListBinding.viewModel = mAddUserViewModel
        this.also { mAddUserListBinding.lifecycleOwner = it }



        mAddUserViewModel.addUser.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                 mAddUserViewModel.insertUser(
                    mAddUserListBinding.fullName.text.toString(),
                    mAddUserListBinding.accountId.text.toString(),
                    mAddUserListBinding.mobileNumber.text.toString(),
                    mAddUserListBinding.phoneNumber.text.toString(),
                    mAddUserListBinding.createdDate.text.toString(),
                    mAddUserListBinding.edtAddress.text.toString(),
                )
                findNavController().popBackStack()

            }
        })
        val pdate = PersianDate()
        val pdformater1 = PersianDateFormat("Y/m/d")
        pdformater1.format(pdate) //1396/05/20
        mAddUserListBinding.createdDate.text = pdformater1.format(pdate)

        mAddUserViewModel.openCalender.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                val now = PersianCalendar()
                val dpd: DatePickerDialog = DatePickerDialog.newInstance(
                    { view, year, monthOfYear, dayOfMonth ->
                        val month = monthOfYear + 1
                        Toast.makeText(
                            activity,
                            "$year/${month}/$dayOfMonth",
                            Toast.LENGTH_LONG
                        ).show()
                        mAddUserListBinding.createdDate.text = "$year/$month/$dayOfMonth"
                    },
                    now.persianYear,
                    now.persianMonth,
                    now.persianDay
                )
                dpd.isThemeDark = false
                dpd.show(requireActivity().fragmentManager, "")
            }
        })
        mAddUserViewModel.openCalenderDone()

        mAddUserViewModel.getBankList().observe(viewLifecycleOwner, {
            mAddUserViewModel.bankInfo.value = it
            val bankList: MutableList<String> = ArrayList() //this is list<string>
            it.forEach { item ->
                bankList.add(item.bankName)
            }
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item, bankList
            )
            Log.d("TAG", "toBank: $bankList")
            mAddUserListBinding.bankListSpinner.adapter = adapter
        })

            mAddUserListBinding.bankListSpinner.visibility = View.VISIBLE

        mAddUserListBinding.firstNumber.addTextChangedListener(
            NumberTextWatcherForThousand(
                mAddUserListBinding.firstNumber
            )
        );

        return mAddUserListBinding.root
    }

}




package com.example.holyquran.ui.banks.addBank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentAddBankBinding
import com.example.holyquran.ui.addUser.AddUserViewModel
import com.example.holyquran.ui.userList.UserListFragmentDirections
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

class AddBankFragment : Fragment() {
    lateinit var mAddBankBinding: FragmentAddBankBinding
    lateinit var mAddBankViewModel: AddBankViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mAddBankBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_add_bank, container, false)
        val application = requireNotNull(this.activity).application
        val personalDAO = UserDatabase.getInstance(application).mUserDAO
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val loanDAO = UserDatabase.getInstance(application).mLoanDAO
        val bankDAO = UserDatabase.getInstance(application).mBankDAO
        val viewModelFactory =
            ViewModelProviderFactory(personalDAO, transactionDAO, loanDAO, bankDAO, application)
        mAddBankViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(AddBankViewModel::class.java)
        mAddBankBinding.viewModel = mAddBankViewModel
        this.also { mAddBankBinding.lifecycleOwner = it }


        val pdate = PersianDate()
        val pdformater1 = PersianDateFormat("Y/m/d")
        pdformater1.format(pdate) //1396/05/20
        mAddBankBinding.createdDate.text = pdformater1.format(pdate)

        mAddBankViewModel.openCalender.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                val now = PersianCalendar()
                val dpd: DatePickerDialog = DatePickerDialog.newInstance(
                    { view, year, monthOfYear, dayOfMonth ->
                        val month = monthOfYear + 1
                        mAddBankBinding.createdDate.text = "$year/$month/$dayOfMonth"
                    },
                    now.persianYear,
                    now.persianMonth,
                    now.persianDay
                )
                dpd.setThemeDark(false)
                dpd.show(requireActivity().fragmentManager, "FuLLKade")
            }
        })

        mAddBankViewModel.addCard.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                checkValidation()
                if (mAddBankBinding.bankName.text!!.isNotEmpty().and(
                        mAddBankBinding.cardNumber.text!!.isNotEmpty().and(
                            mAddBankBinding.accountNumber.text!!.isNotEmpty()
                                .and(mAddBankBinding.edtAddress.text!!.isNotEmpty())
                        )
                    )
                ) {
                    valid()
                }
            }
        })



        return mAddBankBinding.root
    }

    private fun checkValidation() {
        if (mAddBankBinding.bankName.text?.isBlank() == true) {
            mAddBankBinding.bankName.error = "BN Empty"
        } else {
            mAddBankBinding.bankName.error = null
        }
        if (mAddBankBinding.cardNumber.text?.isBlank() == true) {
            mAddBankBinding.cardNumber.error = "CN Empty"
        } else {
            mAddBankBinding.cardNumber.error = null
        }
        if (mAddBankBinding.accountNumber.text?.isBlank() == true) {
            mAddBankBinding.accountNumber.error = "AN Empty"
        } else {
            mAddBankBinding.accountNumber.error = null
        }
        if (mAddBankBinding.edtAddress.text?.isBlank() == true) {
            mAddBankBinding.edtAddress.error = "AN Empty"
        } else {
            mAddBankBinding.edtAddress.error = null
        }
    }

    private fun valid() {
        mAddBankViewModel.insertBank(
            mAddBankBinding.bankName.text.toString(),
            mAddBankBinding.accountNumber.text.toString(),
            mAddBankBinding.cardNumber.text.toString(),
            mAddBankBinding.edtAddress.text.toString(),
            mAddBankBinding.createdDate.text.toString(),
        )
        findNavController().popBackStack()
    }
}

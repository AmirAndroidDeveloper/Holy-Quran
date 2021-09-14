package com.example.holyquran.ui.addUser

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
import com.example.holyquran.data.model.UserInfo
import com.example.holyquran.databinding.FragmentAddUserBinding
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import saman.zamani.persiandate.PersianDateFormat
import saman.zamani.persiandate.PersianDate


class AddUserFragment : Fragment() {

    lateinit var mAddUserListBinding: FragmentAddUserBinding
    lateinit var mAddUserViewModel: AddUserViewModel
    val plus = "+"
    val minus = "-"
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
        val viewModelFactory =
            ViewModelProviderFactory(personalDAO, transactionDAO, loanDAO, application)
        mAddUserViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(AddUserViewModel::class.java)
        mAddUserListBinding.viewModel = mAddUserViewModel
        this.also { mAddUserListBinding.lifecycleOwner = it }


        mAddUserViewModel.addUser.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                val userList: MutableList<UserInfo> = mutableListOf()
                mAddUserViewModel.insertUser(
                    mAddUserListBinding.edtFullName.text.toString(),
                    mAddUserListBinding.accountId.text.toString(),
                    mAddUserListBinding.edtMobileNumber.text.toString(),
                    mAddUserListBinding.edtPhoneNumber.text.toString(),
                    mAddUserListBinding.createdDate.text.toString(),
                    mAddUserListBinding.edtAddress.text.toString(),
                    userList
                )
                this.findNavController().navigate(
                    AddUserFragmentDirections.actionAddUserFragmentToUserListFragment()
                )

            }
        })

        val pdate = PersianDate()
        val pdformater1 = PersianDateFormat("Y/m/d")
        pdformater1.format(pdate) //1396/05/20

        mAddUserListBinding.createdDate.text = pdformater1.format(pdate)

        mAddUserListBinding.createdDate.setOnClickListener {
            val now = PersianCalendar()
            val dpd: DatePickerDialog = DatePickerDialog.newInstance(
                object : DatePickerDialog.OnDateSetListener {

                    override fun onDateSet(

                        view: DatePickerDialog?,
                        year: Int,
                        monthOfYear: Int,
                        dayOfMonth: Int

                    ) {
                        val month = monthOfYear + 1
                        Toast.makeText(
                            activity,
                            "$year/${month}/$dayOfMonth",
                            Toast.LENGTH_LONG
                        ).show()

                        mAddUserListBinding.createdDate.text = "$year/$month/$dayOfMonth"
                    }
                },
                now.persianYear,
                now.persianMonth,
                now.persianDay
            )
            dpd.setThemeDark(false)
            dpd.show(requireActivity().fragmentManager, "FuLLKade")
//
        }
        return mAddUserListBinding.root

    }

}




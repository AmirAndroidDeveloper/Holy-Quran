package com.example.holyquran.ui.userList.transactions.loan.getLoan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentGetLoanBinding
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

class GetLoanFragment : Fragment(), AdapterView.OnItemSelectedListener {
    lateinit var mGetLoanBinding: FragmentGetLoanBinding
    lateinit var mGetLoanViewModel: GetLoanViewModel
    var id: Long = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mGetLoanBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_get_loan,
                container,
                false
            )

        val application = requireNotNull(this.activity).application
        val userDAO = UserDatabase.getInstance(application).mUserDAO
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val loanDAO = UserDatabase.getInstance(application).mLoanDAO
        val viewModelFactory =
            ViewModelProviderFactory(userDAO, transactionDAO, loanDAO, application)

        mGetLoanViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(GetLoanViewModel::class.java)
        mGetLoanBinding.viewModel = mGetLoanViewModel
        this.also { mGetLoanBinding.lifecycleOwner = it }


        val arg =
            GetLoanFragmentArgs.fromBundle(
                requireArguments()
            )
        id = arg.userId


        val spinner: Spinner = mGetLoanBinding.spinner
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.planets_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
            spinner.onItemSelectedListener = this
        }

        mGetLoanBinding.finish.setOnClickListener {
            mGetLoanViewModel.insertLoanTimeSpinner(
                mGetLoanBinding.loanAmount.text.toString(),
                mGetLoanBinding.loanDate.text.toString(),
                mGetLoanBinding.loanSections.text.toString(),
                id
            )
            mGetLoanViewModel.userName.observe(viewLifecycleOwner, {
                if (it != null) {

                    mGetLoanBinding.userName = it
                }
                Toast.makeText(
                    activity,
                    "  وام با موفقیت برای کاربر${it.fullName} ثبت شد ",
                    Toast.LENGTH_LONG
                ).show()
                this.findNavController().navigate(
                    GetLoanFragmentDirections.actionGetLoanFragmentToIncreaseMoneyFragment(id)
                )
            })
        }

        val pdate = PersianDate()
        val pdformater1 = PersianDateFormat("Y/m/d")
        pdformater1.format(pdate) //1396/05/20

        mGetLoanBinding.loanDate.text = pdformater1.format(pdate)
        mGetLoanBinding.loanDate.setOnClickListener {
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
                        mGetLoanBinding.loanDate.text = "$year/$month/$dayOfMonth"
                    }
                },
                now.persianYear,
                now.persianMonth,
                now.persianDay
            )
            dpd.setThemeDark(false)
            dpd.show(requireActivity().fragmentManager, "FuLLKade")
        }


        mGetLoanViewModel.setUserName(id)?.observe(viewLifecycleOwner, {
            mGetLoanViewModel.setUserName(it)
        })
        mGetLoanViewModel.userName.observe(viewLifecycleOwner, {
            if (it != null) {

                mGetLoanBinding.userName = it
            }
        })

        mGetLoanBinding.calculate.setOnClickListener {
            if (mGetLoanBinding.loanSections.text.isEmpty()
                    .or(mGetLoanBinding.benefitPrecent.text.isEmpty())
                    .or(mGetLoanBinding.loanAmount.text.isEmpty())
            ) {
            } else {
                CalculateDate()
            }
        }
        return mGetLoanBinding.root
    }
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    private fun CalculateDate() {
        val loanAmount: String = mGetLoanBinding.loanAmount.getText().toString()
        val convertLoanAmount = loanAmount.toLong()

        val benefitPercent: String = mGetLoanBinding.benefitPrecent.getText().toString()
        val convertBenefitPercent = benefitPercent.toLong()

        val sectionTime: String = mGetLoanBinding.loanSections.getText().toString()
        val convertSectionTime = sectionTime
        val division: Long = 2400

        val amount: Long = convertLoanAmount.toLong()
        val benefit: Long = convertBenefitPercent
        val section: Long = convertSectionTime.toLong()
        val division2: Long = division
        val result: Long = section.plus(1) * benefit * amount / division2
        val result2: Long = (result + amount) / section
        val result3: Long = amount + result

        mGetLoanBinding.loanBenefit.text = result.toString()
        mGetLoanBinding.sectionPrice.text = result2.toString()
        mGetLoanBinding.totalLoan.text = result3.toString()
    }

}

//        val benefitPresent: String = mGetLoanBinding.spinner.getSelectedItem().toString()
//            val division = "/"
//            val time = "*"
//            val minus = "-"
//            val plus = "+"
//            val loanAmount = mGetLoanBinding.loanAmount.text.toString()
//            val loanSections = mGetLoanBinding.loanSections.text.toString()
//            val benefitPresent = mGetLoanBinding.benefitPrecent.text.toString()
//
//            val loanBenefit =
//                loanSections + time + benefitPresent + time + loanAmount + division + 2400
//            val test = loanBenefit + plus + loanAmount
//            val lastResult = test + division + 12
//
//            Log.d("TAG", "calculateTest: $lastResult")
//            try {
//                val expression = ExpressionBuilder(lastResult).build()
//                val result = expression.evaluate()
//                val longResult = result.toLong()
//                if (result == longResult.toDouble())
//                    mGetLoanBinding.sectionPrice.text = longResult.toString()
//                else
//                    Toast.makeText(activity, "$result", Toast.LENGTH_LONG).show()
//
//                mGetLoanBinding.sectionPrice.text = result.toString()
//
//            } catch (e: Exception) {
//                Log.d("Exception", " message : " + e.message)
//            }
package com.example.holyquran.ui.loan.getLoan

import NumberTextWatcherForThousand
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentGetLoanBinding
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import java.text.NumberFormat


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
        val bankDAO = UserDatabase.getInstance(application).mBankDAO
        val viewModelFactory =
            ViewModelProviderFactory(userDAO, transactionDAO, loanDAO, bankDAO, application)

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
//        val spinner: Spinner = mGetLoanBinding.spinner
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.payment_time,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            spinner.adapter = adapter
//            spinner.onItemSelectedListener = this
        }

        mGetLoanViewModel.getLoan.observe(viewLifecycleOwner, Observer {
            mGetLoanBinding.loanAmount.addTextChangedListener(
                NumberTextWatcherForThousand(
                    mGetLoanBinding.loanAmount
                )
            )

            if (it == true) {
                if (mGetLoanBinding.loanAmount.text?.let { it1 ->
                        mGetLoanBinding.loanSections.text.isEmpty()
                            .or(mGetLoanBinding.benefitPrecent.text.isEmpty())
                            .or(it1.isEmpty())
                    } == true
                ) {
                } else {
                    saveLoan()
                    requireView().findNavController().popBackStack()
                }
            }
        })

        val pdate = PersianDate()
        val pdformater1 = PersianDateFormat("Y/m/d")
        pdformater1.format(pdate) //1396/05/20
        mGetLoanBinding.loanDate.text = pdformater1.format(pdate)
        mGetLoanViewModel.openCalender.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                val now = PersianCalendar()
                val dpd: DatePickerDialog = DatePickerDialog.newInstance(
                    { view, year, monthOfYear, dayOfMonth ->
                        val month = monthOfYear + 1
                        mGetLoanBinding.loanDate.text = "$year/$month/$dayOfMonth"
                    },
                    now.persianYear,
                    now.persianMonth,
                    now.persianDay
                )
                dpd.setThemeDark(false)
                dpd.show(requireActivity().fragmentManager, "FuLLKade")
            }
        })

        mGetLoanViewModel.setUserName(id)?.observe(viewLifecycleOwner, {
            mGetLoanViewModel.setUserName(it)
        })
        mGetLoanViewModel.userName.observe(viewLifecycleOwner, {
            if (it != null) {
                mGetLoanBinding.userName = it
            }
        })

        mGetLoanViewModel.calculateLoan.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                if (mGetLoanBinding.loanAmount.text?.let { it1 ->
                        mGetLoanBinding.loanSections.text.isEmpty()
                            .or(mGetLoanBinding.benefitPrecent.text.isEmpty())
                            .or(it1.isEmpty())
                    } == true
                ) {
                } else {
                    calculateData()
                }
            }
        })
        mGetLoanBinding.loanSections.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (mGetLoanBinding.loanSections.text.toString() != "0".orEmpty()) {
                    calculateData()
                }
            }
        })
        return mGetLoanBinding.root
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    private fun calculateData() {
        val removeComma = mGetLoanBinding.loanAmount.text.toString().replace(",", "")
        val convertLoanAmount = removeComma.toLong()
        val benefitPercent: String = mGetLoanBinding.benefitPrecent.getText().toString()
        val convertBenefitPercent = benefitPercent.toLong()
        if (mGetLoanBinding.loanSections.text.isNotEmpty()) {
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
            mGetLoanBinding.sectionPayment.text = NumberFormat.getInstance().format(result2)
            mGetLoanBinding.loanBenefit.text = NumberFormat.getInstance().format(result)
            mGetLoanBinding.totalLoan.text = NumberFormat.getInstance().format(result3)
        }
    }

    private fun saveLoan() {
        val text = mGetLoanBinding.sectionPayment.text.toString()
        val convertor = persianToEnglish(text)

        val removeComma =
            NumberTextWatcherForThousand.trimCommaOfString(mGetLoanBinding.loanAmount.text.toString())
                .replace(",", "")

        val removeCommaSection =
            NumberTextWatcherForThousand.trimCommaOfString(convertor)
                .replace(",", "")

        Log.d("TAG", "insertDataEnglishComma: $convertor,$removeComma")
        mGetLoanViewModel.insertLoanTimeSpinner(
            removeComma,
            mGetLoanBinding.loanDate.text.toString(),
            mGetLoanBinding.loanSections.text.toString(),
            removeCommaSection,
            id
        )
    }

    private fun persianToEnglish(persianStr: String): String {
        var result = ""
        var en = '0'
        for (ch in persianStr) {
            en = ch
            when (ch) {
                '۰' -> en = '0'
                '۱' -> en = '1'
                '۲' -> en = '2'
                '۳' -> en = '3'
                '۴' -> en = '4'
                '۵' -> en = '5'
                '۶' -> en = '6'
                '۷' -> en = '7'
                '۸' -> en = '8'
                '۹' -> en = '9'
            }
            result = "${result}$en"
        }
        return result
    }
}

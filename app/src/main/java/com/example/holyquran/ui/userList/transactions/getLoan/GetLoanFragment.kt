package com.example.holyquran.ui.userList.transactions.getLoan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentGetLoanBinding
import com.example.holyquran.ui.userList.transactions.increaseMoney.IncreaseMoneyFragmentArgs
import com.example.holyquran.ui.userList.transactions.increaseMoney.IncreaseMoneyViewModel

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
                mGetLoanBinding.loanSections.text.toString(),
                        id
            )
        }


        return mGetLoanBinding.root
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Log.d("TAG", "onItemSelected: $p3")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Log.d("TAG", "onItemSelected: $p0")
    }
}



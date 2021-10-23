package com.example.holyquran.ui.banks.bankDetail

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentBankDetailBinding
import com.example.holyquran.ui.banks.bankList.BankListFragmentDirections
import com.example.holyquran.ui.increaseMoney.IncreaseMoneyFragmentDirections
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.ArrayList

class BankDetailFragment : Fragment() {
    lateinit var mBankDetailBinding: FragmentBankDetailBinding
    lateinit var mBankDetailViewModel: BankDetailViewModel
    var bankId = 0L
    var type = ""
    var test = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBankDetailBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_bank_detail, container, false)
        val application = requireNotNull(this.activity).application
        val userDAO = UserDatabase.getInstance(application).mUserDAO
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val loanDAO = UserDatabase.getInstance(application).mLoanDAO
        val bankDAO = UserDatabase.getInstance(application).mBankDAO
        val viewModelFactory =
            ViewModelProviderFactory(userDAO, transactionDAO, loanDAO, bankDAO, application)
        mBankDetailViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(BankDetailViewModel::class.java)
        mBankDetailBinding.viewModel = mBankDetailViewModel
        this.also { mBankDetailBinding.lifecycleOwner = it }
        val arg =
            BankDetailFragmentArgs.fromBundle(
                requireArguments()
            )
        bankId = arg.bankId
        mBankDetailViewModel.setBankName(bankId)?.observe(viewLifecycleOwner, {
            test = it.bankId.toString()
            mBankDetailViewModel.setBankName(it)
            val bankList: MutableList<String> = ArrayList() //this is list<string>
            bankList.add(it.bankName)
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item, bankList
            )
            mBankDetailBinding.fromBank.adapter = adapter
        })
        mBankDetailViewModel.bankName.observe(viewLifecycleOwner, {
            if (it != null) {
                mBankDetailBinding.bank = it
            }
        })
        mBankDetailViewModel.getBankList().observe(viewLifecycleOwner, {
            mBankDetailViewModel.bankInfo.value = it
            Log.d("TAG", "viewHolder: $it")
            val bankList: MutableList<String> = ArrayList() //this is list<string>
            it.forEach { item ->
                // here item is item of list category
                bankList.add(item.bankName)
            }
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item, bankList
            )
            Log.d("TAG", "toBank: $bankList")
            mBankDetailBinding.toBank.adapter = adapter
        })


        val increase = mBankDetailViewModel.sumBankMoneyIncrease(bankId).toLong()
        val decrease = mBankDetailViewModel.sumBankMoneyDecrease(bankId).toLong()
        val result = increase - decrease
        mBankDetailBinding.amountLeft.text = result.toString()
        val formatter: NumberFormat = DecimalFormat("#,###,###,###")
        mBankDetailBinding.amountLeft.setText("" + formatter.format(result))


        mBankDetailViewModel.transferMoney.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                val decreasePage = "decrease"
                type = decreasePage
                val amount = mBankDetailBinding.transferAmount.text.toString()
                mBankDetailViewModel.transferMoneyDecrease(
                    amount,
                    bankId,
                    type,
                )
                val increasePage = "increase"
                type = increasePage
                mBankDetailViewModel.transferMoneyIncrease(
                    mBankDetailBinding.transferAmount.text.toString(),
                    null,
                    type,
                )
            }
        })

        setHasOptionsMenu(true)
        return mBankDetailBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.transfer_between_banks, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.transferMoney -> {
                Toast.makeText(
                    activity,
                    "${mBankDetailViewModel.selectedItemPosition}",
                    Toast.LENGTH_SHORT
                ).show()
              mBankDetailBinding.transferMoneyLayout.visibility=View.VISIBLE
                true
            }

            R.id.editBankInfo -> {
                this.findNavController().navigate(
                    BankDetailFragmentDirections.actionBankDetailFragmentToEditBankInfoFragment(bankId)
                )
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
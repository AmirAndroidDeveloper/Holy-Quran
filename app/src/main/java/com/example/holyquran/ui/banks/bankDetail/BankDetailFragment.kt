package com.example.holyquran.ui.banks.bankDetail

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentBankDetailBinding
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.ArrayList

class BankDetailFragment : Fragment() {
    lateinit var mBankDetailBinding: FragmentBankDetailBinding
    lateinit var mBankDetailViewModel: BankDetailViewModel
    var bankId: Long = 0L
    var type = ""
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

        Log.d("TAG", "onCreateView: $bankId")
        mBankDetailViewModel.setBankName(bankId)?.observe(viewLifecycleOwner, {
            mBankDetailViewModel.setBankName(it)

            val bankList: MutableList<String> = ArrayList() //this is list<string>

            bankList.add(it.bankName)


            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item, bankList
            )
            Log.d("TAG", "fromBank: $adapter")

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

        mBankDetailBinding.fromBank?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val decrease = "decrease"
                    type = decrease
                    if (type == "increase") {
                        Toast.makeText(activity, "increase", Toast.LENGTH_SHORT).show()
                    } else if (type == "decrease") {
                        Toast.makeText(activity, "decrease", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        mBankDetailBinding.toBank?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val increase = "increase"
                    type = increase

                    if (type == "increase") {
                        Toast.makeText(activity, "increase", Toast.LENGTH_SHORT).show()
                    } else if (type == "decrease") {
                        Toast.makeText(activity, "decrease", Toast.LENGTH_SHORT).show()
                    }
                    Log.d("TAG", "toBank $type")
                }
            }


        val increase = mBankDetailViewModel.sumBankMoneyIncrease(bankId).toLong()
        val decrease = mBankDetailViewModel.sumBankMoneyDecrease(bankId).toLong()
        val result = increase - decrease
//        Toast.makeText(activity, "$increase|||$decrease|||$result", Toast.LENGTH_SHORT).show()

        mBankDetailBinding.amountLeft.text =result.toString()
        val formatter: NumberFormat = DecimalFormat("#,###,###,###")
        mBankDetailBinding.amountLeft.setText("" + formatter.format(result))


        mBankDetailBinding.finish.setOnClickListener {
            if (type == "increase") {}
            val amount = mBankDetailBinding.transferAmount.text.toString()
            mBankDetailViewModel.transferMoneyDecrease(
                amount,
                bankId,
                type,
            )
        }
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
                mBankDetailBinding.transferMoneyLayout.visibility = View.VISIBLE
                Toast.makeText(activity, "جا به جایی بول", Toast.LENGTH_LONG).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
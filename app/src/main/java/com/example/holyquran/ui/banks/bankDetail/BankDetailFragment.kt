package com.example.holyquran.ui.banks.bankDetail

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentBankDetailBinding
import com.example.holyquran.ui.increaseMoney.IncreaseMoneyFragmentArgs
import com.example.holyquran.ui.increaseMoney.IncreaseMoneyFragmentDirections
import com.example.holyquran.ui.increaseMoney.IncreaseMoneyViewModel

class BankDetailFragment : Fragment() {
    lateinit var mBankDetailBinding: FragmentBankDetailBinding
    lateinit var mBankDetailViewModel: BankDetailViewModel
    var bankId: Long = 0L
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
        })
        mBankDetailViewModel.bankName.observe(viewLifecycleOwner, {
            if (it != null) {
                mBankDetailBinding.bank = it
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
                mBankDetailBinding.transferMoneyLayout.visibility = View.VISIBLE
                Toast.makeText(activity, "جا به جایی بول", Toast.LENGTH_LONG).show()
//                mIncreaseMoneyViewModel.goToIncreaseDone()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
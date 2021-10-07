package com.example.holyquran.ui.editBankInfo

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
import com.example.holyquran.data.model.Bank
import com.example.holyquran.databinding.FragmentEditBankInfoBinding

class EditBankInfoFragment : Fragment() {
    lateinit var mEditBankInfoBinding: FragmentEditBankInfoBinding
    lateinit var mEditBankInfoViewModel: EditBankInfoViewModel
    var id = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mEditBankInfoBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_edit_bank_info,
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

        mEditBankInfoViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(EditBankInfoViewModel::class.java)
        mEditBankInfoBinding.viewModel = mEditBankInfoViewModel
        this.also { mEditBankInfoBinding.lifecycleOwner = it }
        val arg =
            EditBankInfoFragmentArgs.fromBundle(
                requireArguments()
            )
        id = arg.bankId
        setBankInfo()

        return mEditBankInfoBinding.root
    }

    var bankInfo = Bank(0L, "", "", "", "", null);
    private fun setBankInfo() {
        mEditBankInfoViewModel.setBankName(id)?.observe(viewLifecycleOwner, {
            mEditBankInfoViewModel.setBankName(it)
            bankInfo = it
        })
        mEditBankInfoViewModel.bankName.observe(viewLifecycleOwner, {
            if (it != null) {
                mEditBankInfoBinding.bankInfo = it
            }
        })
        editBank()
    }

    private fun editBank() {
        mEditBankInfoViewModel.validInfo.observe(viewLifecycleOwner, Observer {
            if (it) {
                if (validInfo()) {
                    bankInfo.bankName = mEditBankInfoBinding.userName.text.toString()
                    bankInfo.cardNumber = mEditBankInfoBinding.mobileNumber.text.toString()
                    bankInfo.accountNumber = mEditBankInfoBinding.phoneNumber.text.toString()
                    bankInfo.address = mEditBankInfoBinding.address.text.toString()
                    mEditBankInfoViewModel.updateBank(
                        bankInfo
                    )
                    Toast.makeText(activity, "update user", Toast.LENGTH_LONG).show();

                }
                mEditBankInfoViewModel.validDone()
            }
        })
        mEditBankInfoViewModel.updateSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().popBackStack();
                Toast.makeText(activity, "ویرایش با موفقیت دخیره گردید", Toast.LENGTH_LONG).show();
            }
        })
    }
    private fun validInfo(): Boolean {

        return true
    }
}
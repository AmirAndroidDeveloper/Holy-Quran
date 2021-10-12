package com.example.holyquran.ui.popupWindow

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.example.holyquran.R
import com.example.holyquran.databinding.FragmentPopupWindowBinding
import android.view.*
import android.widget.PopupWindow
import android.view.WindowManager
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity

import android.R.layout
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.DialogFragment.STYLE_NO_FRAME
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.data.model.UserInfo
import com.example.holyquran.ui.decreaseMoney.DecreaseMoneyFragmentArgs
import com.example.holyquran.ui.decreaseMoney.DecreaseViewModel
import com.example.holyquran.ui.increaseMoney.IncreaseMoneyFragmentDirections
import com.google.android.material.snackbar.Snackbar


class PopupWindowFragment : DialogFragment() {
    lateinit var mPopupWindowBinding: FragmentPopupWindowBinding
    lateinit var mPopupViewModel: PopupViewModel
    var id: Long = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPopupWindowBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_popup_window,
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
        mPopupViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(PopupViewModel::class.java)
        mPopupWindowBinding.viewModel = mPopupViewModel
        this.also { mPopupWindowBinding.lifecycleOwner = it }
        val arg =
            PopupWindowFragmentArgs.fromBundle(
                requireArguments()
            )
        id = arg.userId

        mPopupViewModel.setUserName(id)?.observe(viewLifecycleOwner, {
            mPopupViewModel.setUserName(it)

        })
        mPopupViewModel.userName.observe(viewLifecycleOwner, {
            if (it != null) {
                mPopupWindowBinding.userName = it
                val hi = it
                mPopupViewModel.deleteUser.observe(viewLifecycleOwner, Observer {
                    if (it == true) {
                        deleteUserDialog(hi)
                    }
                })
            }

        })

        mPopupViewModel.goToIncreaseSubmit.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    PopupWindowFragmentDirections.actionNavigationDialogFragmentToIncreaseMoneyFragment(
                        id
                    )
                )
                mPopupViewModel.goToIncreaseDone()
            }
        })
        mPopupViewModel.goToDecreaseSubmit.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    PopupWindowFragmentDirections.actionNavigationDialogFragmentToDecreaseMoneyFragment(
                        id
                    )
                )
                mPopupViewModel.goToDecreaseDone()
            }
        })
        mPopupViewModel.goToLoanSubmit.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    PopupWindowFragmentDirections.actionNavigationDialogFragmentToGetLoanFragment(
                        id
                    )
                )
                mPopupViewModel.goToLoanDone()
            }
        })

        mPopupViewModel.goToLoanListSubmit.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    PopupWindowFragmentDirections.actionNavigationDialogFragmentToLoanHistoryFragment(
                        id
                    )
                )
                mPopupViewModel.goToLoanDone()
            }
        })

        mPopupViewModel.goToEditUserInfoSubmit.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    PopupWindowFragmentDirections.actionNavigationDialogFragmentToEditFragment(
                        id
                    )
                )
                mPopupViewModel.goToLoanDone()
            }
        })
        return mPopupWindowBinding.root
    }


    private fun deleteUserDialog(userInfo: UserInfo) {
        Snackbar.make(
            mPopupWindowBinding.root,
            "آیا تمایل به حذف عضو دارید؟ ",
            Snackbar.LENGTH_LONG
        )
            .setAction("حذف") {
                mPopupViewModel.deleteUser(userInfo)
                dialog?.dismiss()
            }
            .setActionTextColor(resources.getColor(android.R.color.holo_red_light))
            .show()
    }

}
package com.example.holyquran.ui.popupWindow

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.data.model.UserInfo
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Insets
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.DisplayMetrics
import com.example.holyquran.databinding.FragmentPopupWindowBinding
import com.google.android.material.snackbar.Snackbar


class PopupWindowFragment : DialogFragment() {
    lateinit var mPopupWindowBinding: FragmentPopupWindowBinding
    lateinit var mPopupViewModel: PopupViewModel
    var id: Long = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        mPopupViewModel.goToPayPaymentsSubmit.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    PopupWindowFragmentDirections.actionNavigationDialogFragmentToPayPaymentsFragment(
                        id
                    )
                )
                mPopupViewModel.goToLoanDone()
            }
        })
        mPopupViewModel.goToUserTransactionHistory.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    PopupWindowFragmentDirections.actionNavigationDialogFragmentToTransactionHistoryFragment(
                        id
                    )
                )
                mPopupViewModel.goToLoanDone()
            }
        })

        mPopupViewModel.setLoan(id)?.observe(viewLifecycleOwner, {
            if (it != null) {
                mPopupViewModel.setLoan(it)
            } else {
                notShowLoanInfo()
            }
        })
        mPopupViewModel.loan.observe(viewLifecycleOwner, {
            if (it != null) {
                mPopupWindowBinding.loan = it
            }
        })

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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

    private fun notShowLoanInfo() {
        Toast.makeText(activity, "noLoanHasBEENSAVED", Toast.LENGTH_SHORT).show()
        mPopupWindowBinding.payPayment.setTextColor(resources.getColor(R.color.gray500))
        mPopupWindowBinding.payPaymentImg.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_add_circle))
        mPopupWindowBinding.noLoanForUser.visibility = View.VISIBLE
        mPopupWindowBinding.payPaymentLL.isClickable = false
    }

    private fun resize() {
        val screenWidth: Int = requireActivity().getScreenWidth()
        val screenHeight: Int = requireActivity().getScreenHeight()

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // landscape
            if (dialog != null) dialog!!.window!!.setLayout(
                screenWidth - 300.toPx(requireContext()),
                screenHeight - 100.toPx(requireContext())
            )
        } else {
            // Portrait
            if (dialog != null) dialog!!.window!!.setLayout(
                screenWidth- 50.toPx(requireContext()),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun Int.toPx(context: Context): Int =
        (this * context.resources.displayMetrics.density).toInt()


    private fun Activity.getScreenWidth(): Int {
        return if (Build.VERSION.SDK_INT < 30) {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        } else {
            val metrics = windowManager.currentWindowMetrics
            val insets = metrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            metrics.bounds.width() - insets.left - insets.right
        }
    }

    private fun Activity.getScreenHeight(): Int {
        return if (Build.VERSION.SDK_INT < 30) {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        } else {
            val metrics = windowManager.currentWindowMetrics
            val insets = metrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            metrics.bounds.height() - insets.top - insets.bottom
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resize()
    }
}
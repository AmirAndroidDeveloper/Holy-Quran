package com.example.holyquran.ui.increaseMoney

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.holyquran.databinding.FragmentIncreaseMoneyBinding
import java.text.NumberFormat
import java.text.DecimalFormat


class IncreaseMoneyFragment : Fragment() {
    var id: Long = 0L
    var id2: Boolean = false
    var numbers = 1
    lateinit var mIncreaseMoneyBinding: FragmentIncreaseMoneyBinding
    lateinit var mIncreaseMoneyViewModel: IncreaseMoneyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mIncreaseMoneyBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_increase_money,
                container,
                false
            )
        val application = requireNotNull(this.activity).application
        val userDAO = UserDatabase.getInstance(application).mUserDAO
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val loanDAO = UserDatabase.getInstance(application).mLoanDAO
        val viewModelFactory =
            ViewModelProviderFactory(userDAO, transactionDAO, loanDAO, application)
        mIncreaseMoneyViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(IncreaseMoneyViewModel::class.java)
        mIncreaseMoneyBinding.increaseMoneyViewModel = mIncreaseMoneyViewModel
        this.also { mIncreaseMoneyBinding.lifecycleOwner = it }

        val arg =
            IncreaseMoneyFragmentArgs.fromBundle(
                requireArguments()
            )
        id = arg.userIdIncrease
        Log.d("TAG", "onCreateView: $id")
        mIncreaseMoneyViewModel.setUserName(id)?.observe(viewLifecycleOwner, {
            mIncreaseMoneyViewModel.setUserName(it)
        })


        mIncreaseMoneyViewModel.userName.observe(viewLifecycleOwner, {
            if (it != null) {
                mIncreaseMoneyBinding.userName = it
            }
        })

        val increase = mIncreaseMoneyViewModel.sumUserIncrease(id).toLong()
        val decrease = mIncreaseMoneyViewModel.sumUserDecrease(id).toLong()
        val result = increase - decrease
        mIncreaseMoneyBinding.totalMoney.text = NumberFormat.getInstance().format(result)
        mIncreaseMoneyViewModel.increaseMoney.observe(viewLifecycleOwner, Observer {
            val removeComma = mIncreaseMoneyBinding.increaseEdt.text.toString().replace(",", "")
            if (it == true) {
                if (id2 == true) {
                    mIncreaseMoneyViewModel.insertLoanPayments(
                        removeComma,
                        true,
                        numbers.toString(),
                        id
                    )
                    mIncreaseMoneyViewModel.setLoanDetail(id)?.observe(viewLifecycleOwner, {
                        if (mIncreaseMoneyBinding.increaseEdt.text.toString() != it.payment) {

                            val builder: AlertDialog.Builder =
                                AlertDialog.Builder(requireActivity())
                            builder.setIcon(R.drawable.warning)
                            builder.setTitle("خروج از برنامه ")
                            builder.setMessage("از برنامه خارج میشوید؟")
                                .setCancelable(false)
                                .setPositiveButton("بله",
                                    DialogInterface.OnClickListener { dialog, id -> dialog.dismiss() })
                                .setNegativeButton("خیر",
                                    DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
                            val alert: AlertDialog = builder.create()
                            alert.setCanceledOnTouchOutside(false)
                            alert.show()
                        }
                    })
                } else {
                    mIncreaseMoneyViewModel.insertMoney(
                        removeComma,
                        false,
                        id
                    )
                }
            }
        })
        mIncreaseMoneyViewModel.gotToDecreaseMoney.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    IncreaseMoneyFragmentDirections.actionIncreaseMoneyFragmentToDecreaseMoneyFragment(
                        id
                    )
                )
                mIncreaseMoneyViewModel.goToIncreaseDone()
            }
        })
        mIncreaseMoneyViewModel.setIncrease(id)?.observe(viewLifecycleOwner, {
            if (it != null) {
                mIncreaseMoneyViewModel.setIncrease(it)
            }
        })
        mIncreaseMoneyViewModel.increase.observe(viewLifecycleOwner, {
            if (it != null) {
                val formatter: NumberFormat = DecimalFormat("#,###,###,###")
                mIncreaseMoneyBinding.userMoney.setText("" + formatter.format(increase))
                if (mIncreaseMoneyBinding.totalMoney.text == "0") {
                    mIncreaseMoneyBinding.userMoney.text = it.increase
                }
                mIncreaseMoneyBinding.userMoney.text =
                    mIncreaseMoneyViewModel.sumUserIncrease(id).toString()
                //                mIncreaseMoneyBinding.userMoney.text = it.increase.toString() + it.increase.toString()
            }
        })
        setHasOptionsMenu(true)
        mIncreaseMoneyViewModel.setLoanDetail(id)?.observe(viewLifecycleOwner, {
            if (it?.userId != null) {
                mIncreaseMoneyBinding.checkBox?.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (!isChecked) {
                        Log.d("TAG", "checkBox: test")
                    } else {
                        Log.d("TAG", "checkBox: no")

                    }
                    id2 = isChecked
                    if (id2 == isChecked) {
                        mIncreaseMoneyViewModel.setLoanDetail(id)?.observe(viewLifecycleOwner, {
                            if (mIncreaseMoneyBinding.increaseEdt.text.toString() != it.payment) {
                                mIncreaseMoneyBinding.noLoanForUser.visibility = View.VISIBLE
                                val textLoanPayments = it.payment.toInt()
                                mIncreaseMoneyBinding.textPlus.visibility = View.VISIBLE
                                mIncreaseMoneyBinding.noLoanForUser.text =
                                    NumberFormat.getInstance()
                                        .format(textLoanPayments)
                            }
                        })
                    }
                }
            } else {
                mIncreaseMoneyBinding.checkBox.isEnabled = false
                mIncreaseMoneyBinding.noLoanForUser.visibility = View.VISIBLE

            }
        })

        return mIncreaseMoneyBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.loan_menu, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.transactionHistory -> {
                this.findNavController().navigate(
                    IncreaseMoneyFragmentDirections.actionIncreaseMoneyFragmentToIncreaseHistoryFragment(
                        id
                    )
                )
                Toast.makeText(activity, "تاریخچه تراکنش ها", Toast.LENGTH_LONG).show()
                true
            }
            R.id.getLoan -> {
                this.findNavController().navigate(
                    IncreaseMoneyFragmentDirections.actionIncreaseMoneyFragmentToGetLoanFragment(
                        id
                    )
                )
                true
            }
            R.id.loanHistory -> {
                this.findNavController().navigate(
                    IncreaseMoneyFragmentDirections.actionIncreaseMoneyFragmentToLoanHistoryFragment(
                        id
                    )
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
package com.example.holyquran.ui.increaseMoney

import NumberTextWatcherForThousand
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import android.view.*
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.example.holyquran.databinding.FragmentIncreaseMoneyBinding
import java.text.NumberFormat
import java.text.DecimalFormat
import java.util.ArrayList

class IncreaseMoneyFragment : Fragment() {
    var userId: Long = 0L
    var decide = ""
    private val increasePage = "increase"
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
        val bankDAO = UserDatabase.getInstance(application).mBankDAO
        val viewModelFactory =
            ViewModelProviderFactory(userDAO, transactionDAO, loanDAO, bankDAO, application)
        mIncreaseMoneyViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(IncreaseMoneyViewModel::class.java)
        mIncreaseMoneyBinding.increaseMoneyViewModel = mIncreaseMoneyViewModel
        this.also { mIncreaseMoneyBinding.lifecycleOwner = it }
        mIncreaseMoneyViewModel.increaseMoneyDone()

        val arg =
            IncreaseMoneyFragmentArgs.fromBundle(
                requireArguments()
            )
        userId = arg.userIdIncrease
        Log.d("TAG", "onCreateView: $userId")
        mIncreaseMoneyViewModel.setUserName(userId)?.observe(viewLifecycleOwner, {
            mIncreaseMoneyViewModel.setUserName(it)
        })
        mIncreaseMoneyViewModel.userName.observe(viewLifecycleOwner, {
            if (it != null) {
                mIncreaseMoneyBinding.userName = it
            }
        })
        val increase = mIncreaseMoneyViewModel.sumUserIncrease(userId).toLong()
        val decrease = mIncreaseMoneyViewModel.sumUserDecrease(userId).toLong()
        val result = increase - decrease
        mIncreaseMoneyBinding.totalMoney.text = NumberFormat.getInstance().format(result)
        mIncreaseMoneyViewModel.increaseMoney.observe(viewLifecycleOwner, Observer {
            val removeComma =
                NumberTextWatcherForThousand.trimCommaOfString(mIncreaseMoneyBinding.increaseEdt.text.toString())
                    .replace(",", "")
            mIncreaseMoneyBinding.increaseEdt.addTextChangedListener(
                NumberTextWatcherForThousand(
                    mIncreaseMoneyBinding.increaseEdt
                )
            );
            if (it == true) {
                mIncreaseMoneyViewModel.insertMoney(
                    removeComma,
                    userId,
                    null,

                    )
                mIncreaseMoneyViewModel.goToIncreaseDone()
            }
            mIncreaseMoneyViewModel.goToIncreaseDone()


        })
        mIncreaseMoneyViewModel.increaseMoneyDone()
        mIncreaseMoneyViewModel.goToIncreaseDone()

        mIncreaseMoneyViewModel.gotToDecreaseMoney.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    IncreaseMoneyFragmentDirections.actionIncreaseMoneyFragmentToDecreaseMoneyFragment(
                        userId
                    )
                )
                mIncreaseMoneyViewModel.goToIncreaseDone()
            }
        })
        mIncreaseMoneyViewModel.setIncrease(userId)?.observe(viewLifecycleOwner, {
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
                    mIncreaseMoneyViewModel.sumUserIncrease(userId).toString()
                //                mIncreaseMoneyBinding.userMoney.text = it.increase.toString() + it.increase.toString()
            }
        })
        setHasOptionsMenu(true)
        mIncreaseMoneyViewModel.getBankList().observe(viewLifecycleOwner, {
            mIncreaseMoneyViewModel.bankInfo.value = it
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
            mIncreaseMoneyBinding.chooseBank.adapter = adapter
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
                        userId
                    )
                )
//                Toast.makeText(activity, "تاریخچه تراکنش ها", Toast.LENGTH_LONG).show()
                mIncreaseMoneyViewModel.goToIncreaseDone()

                true
            }
            R.id.getLoan -> {
                this.findNavController().navigate(
                    IncreaseMoneyFragmentDirections.actionIncreaseMoneyFragmentToGetLoanFragment(
                        userId
                    )
                )
                mIncreaseMoneyViewModel.goToIncreaseDone()
                true
            }
            R.id.loanHistory -> {
                this.findNavController().navigate(
                    IncreaseMoneyFragmentDirections.actionIncreaseMoneyFragmentToLoanHistoryFragment(
                        userId
                    )
                )
                mIncreaseMoneyViewModel.goToIncreaseDone()
                true
            }
            R.id.call -> {
                mIncreaseMoneyViewModel.setUserName(userId)?.observe(viewLifecycleOwner, {
                    mIncreaseMoneyViewModel.setUserName(it)
                    it.mobileNumber?.let { it1 -> sendPhoneNumberForCall(it1) }
                })
                true
            }
            R.id.message -> {
                mIncreaseMoneyViewModel.setUserName(userId)?.observe(viewLifecycleOwner, {
                    mIncreaseMoneyViewModel.setUserName(it)
                sendPhoneNumberToSms(it.mobileNumber.toString())
                })
                true
            }

            R.id.editUserInfo -> {
               this.findNavController().navigate(
                   IncreaseMoneyFragmentDirections.actionIncreaseMoneyFragmentToEditFragment(userId)
               )
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun sendPhoneNumberToSms(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phoneNumber, null))
        startActivity(intent)
    }
    private fun sendPhoneNumberForCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null))
        startActivity(intent)
    }}

//val builder: AlertDialog.Builder =
//    AlertDialog.Builder(requireActivity())
//builder.setIcon(R.drawable.warning)
//val increaseEditText = mIncreaseMoneyBinding.increaseEdt.text.toString()
//val currentPayment = it.payment
//if (increaseEditText.toInt() > currentPayment.toInt()) {
//    val more = "بیشتر"
//    decide = more
//} else {
//    val less = "کمتر"
//    decide = less
//}
//builder.setTitle(" مبلغ مورد نظر از مبلغ قسط وام $decide است. ادامه میدهید؟")
//.setCancelable(false)
//.setPositiveButton("اره به هر حال واریز کن",
//DialogInterface.OnClickListener { dialog, id ->
//    mIncreaseMoneyViewModel.insertLoanPayments(
//        removeComma,
//        true,
//        userId,
//        increasePage
//    )
//    Toast.makeText(
//        activity,
//        "قسط با موفقیت برداخت شد.",
//        Toast.LENGTH_SHORT
//    ).show()
//})
//.setNegativeButton("نه,ممنون",
//DialogInterface.OnClickListener { dialog, id -> dialog.dismiss() }
//)
//val alert: AlertDialog = builder.create()
//alert.setCanceledOnTouchOutside(true)
//alert.show()
//mIncreaseMoneyViewModel.goToIncreaseDone()

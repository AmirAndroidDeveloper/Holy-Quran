package com.example.holyquran.ui.mainPage

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentMainPageBinding
import java.text.NumberFormat
import android.os.Environment
import java.lang.Exception
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.provider.MediaStore
import com.example.holyquran.ui.lockScreen.LogInActivity
import ir.androidexception.roomdatabasebackupandrestore.Backup
import ir.androidexception.roomdatabasebackupandrestore.OnWorkFinishListener
import kotlinx.android.synthetic.main.item.*
import java.io.*
import java.nio.channels.FileChannel
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*


class MainPageFragment : Fragment() {
    var id: Long = 0L
    val payPayment: String = "payPayment"
    val firstMoney: String = "firstMoney"
    var type: String = ""
    val number: Long = 1

    lateinit var mMainPageBinding: FragmentMainPageBinding
    lateinit var mMainViewModel: MainFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        loadLocate() // call LoadLocate

        mMainPageBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main_page, container, false)
        val application = requireNotNull(this.activity).application
        val personalDAO = UserDatabase.getInstance(application).mUserDAO
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val loanDAO = UserDatabase.getInstance(application).mLoanDAO
        val bankDAO = UserDatabase.getInstance(application).mBankDAO
        val viewModelFactory =
            ViewModelProviderFactory(personalDAO, transactionDAO, loanDAO, bankDAO, application)
        mMainViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(MainFragmentViewModel::class.java)
        mMainPageBinding.viewModel = mMainViewModel
        this.also { mMainPageBinding.lifecycleOwner = it }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            alertDialog()

        }

        mMainViewModel.getUserList().observe(viewLifecycleOwner, {
            mMainViewModel.userInfo.value = it
            mMainPageBinding.currentUsers.text = it.size.toString()


            val deposits = mMainViewModel.sumUserDeposit(firstMoney)
            type = deposits.toString()
            mMainPageBinding.userDeposits.text = NumberFormat.getInstance().format(deposits)
            mMainPageBinding.increaseDeposits.text = NumberFormat.getInstance().format(deposits)
            mMainPageBinding.userDeposits.append(" ریال")
            mMainPageBinding.increaseDeposits.append(" ریال")

            val resultDeletedUsers = it.size.toString().toLong()
                .minus(mMainPageBinding.deletedUsers.text.toString().toLong())
            mMainPageBinding.resultUsers.text = resultDeletedUsers.toString()
            Log.d("TAG", "onCreateView: $resultDeletedUsers")
        })
        mMainViewModel.getLoanList().observe(viewLifecycleOwner, {
            mMainViewModel.loan.value = it
            mMainPageBinding.loans.text = it.size.toString()
        })

        val sumAll = mMainViewModel.sumAllIncrease() - mMainViewModel.sumAllDecrease()
        mMainPageBinding.wholeMoney.text = NumberFormat.getInstance().format(sumAll)
        mMainPageBinding.wholeMoney.append(" ریال")

        val sumAllLoans = mMainViewModel.sumAllLoansAmount()
        mMainPageBinding.allLoans.text = NumberFormat.getInstance().format(sumAllLoans)
        mMainPageBinding.allLoans.append(" ریال")


        val sumPayments = mMainViewModel.sumUserPayments(payPayment)
        mMainPageBinding.paidMoneyLoan.text = sumPayments.toString()
        type = sumPayments.toString()
        mMainPageBinding.paidMoneyLoan.append(" ریال")

        mMainViewModel.setIncrease()?.observe(viewLifecycleOwner, {
            if (it != null) {
                mMainViewModel.setIncrease(it)
                val amountLeft = mMainViewModel.sumAllLoansAmount().minus(sumPayments)
                mMainPageBinding.amountLoanLeft.text = amountLeft.toString()
                mMainPageBinding.amountLeftLoan.text = amountLeft.toString()
                mMainPageBinding.amountLeftLoan.append(" ریال")
                mMainPageBinding.amountLoanLeft.append(" ریال")


                val text = mMainPageBinding.paidLoans.text.toString().toLong()
                val sumAllLoans = mMainViewModel.sumAllLoansAmount().toString()
                if (type == sumAllLoans) {
                    Toast.makeText(activity, "fix", Toast.LENGTH_SHORT).show()
                    mMainPageBinding.paidLoans.text = (text + number).toString()
                }
            }
        })

        mMainPageBinding.txtTitle.setOnClickListener {
//            Backup.Init()
//                .database(UserDatabase.INSTANCE)
//                .path("path-to-save-backup-file")
//                .fileName("filename.txt")
//                .secretKey("your-secret-key") //optional
//                .onWorkFinishListener { success, message ->
//                    // do anything
//                }
//                .execute()

        }


        setHasOptionsMenu(true)
        return mMainPageBinding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_page_menu, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exportData -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
                val dialogView: View =
                    LayoutInflater.from(context).inflate(R.layout.item, null, false)
                val sms: TextView = dialogView.findViewById(R.id.sendViaSms)
                val txt: TextView = dialogView.findViewById(R.id.sendViaTxt)
                val img: TextView = dialogView.findViewById(R.id.sendViaImg)
                val backUp: TextView = dialogView.findViewById(R.id.backUpData)
                sms.setOnClickListener {
                    sendSMS()
                }

                txt.setOnClickListener {
                    val sumAll = mMainViewModel.sumAllIncrease() - mMainViewModel.sumAllDecrease()
                    val addComma = NumberFormat.getInstance().format(sumAll)
                    val deposits = mMainViewModel.sumUserDeposit(firstMoney)
                    val addCommaDeposits = NumberFormat.getInstance().format(deposits)
                    val sumAllLoans = mMainViewModel.sumAllLoansAmount()
                    val addCommaLoans = NumberFormat.getInstance().format(sumAllLoans)

                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "موجودی  فعلی صندوق: $addComma" + "سبرده: $addCommaDeposits " + "وام های برداختنی: $addCommaLoans "
                        )
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                }

                img.setOnClickListener {
                    sendImg()
                }

                backUp.setOnClickListener {
                }
                builder.setView(dialogView)
                    .create()
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun sendSMS() {
        val sumAll = mMainViewModel.sumAllIncrease() - mMainViewModel.sumAllDecrease()
        val addComma = NumberFormat.getInstance().format(sumAll)
        val deposits = mMainViewModel.sumUserDeposit(firstMoney)
        val addCommaDeposits = NumberFormat.getInstance().format(deposits)
        val sumAllLoans = mMainViewModel.sumAllLoansAmount()
        val addCommaLoans = NumberFormat.getInstance().format(sumAllLoans)

        val sendIntent = Intent(Intent.ACTION_VIEW)
        sendIntent.data = Uri.parse("sms:")
        sendIntent.putExtra(
            "sms_body",
            "موجودی  فعلی صندوق: $addComma" + "سبرده: $addCommaDeposits " + "وام های برداختنی: $addCommaLoans "
        )
        requireActivity().startActivity(sendIntent)
    }

    private fun sendImg() {
        share(screenShot(requireView()));
    }

    private fun screenShot(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun share(bitmap: Bitmap) {
        val pathofBmp = MediaStore.Images.Media.insertImage(
            requireActivity().getContentResolver(),
            bitmap, "title", null
        )
        val uri = Uri.parse(pathofBmp)
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/*"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Star App")
        shareIntent.putExtra(Intent.EXTRA_TEXT, "")
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        requireActivity().startActivity(Intent.createChooser(shareIntent, "hello hello"))
    }

    private fun alertDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        builder.setIcon(R.drawable.warning)
        builder.setTitle("خروج از برنامه ")
        builder.setMessage("از برنامه خارج میشوید؟")
            .setCancelable(false)
            .setPositiveButton("بله",
                DialogInterface.OnClickListener { dialog, id -> activity?.moveTaskToBack(true);
                    getActivity()?.finish(); })
            .setNegativeButton("خیر",
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    private fun setLocate(Lang: String) {

        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        requireActivity().baseContext.resources.updateConfiguration(
            config,
            requireActivity().baseContext.resources.displayMetrics
        )

        val editor = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    private fun loadLocate() {
        val sharedPreferences =
            requireActivity().getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        if (language != null) {
            setLocate(language)
        }
    }
}

package com.example.holyquran.ui.loan.loanHistory

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holyquran.Communicator
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentLoanHistoryBinding
import com.example.holyquran.ui.loan.loanList.LoanListFragment
import kotlinx.android.synthetic.main.fragment_loan_history.*

class LoanHistoryFragment : Fragment() {
    lateinit var mLoanHistoryBinding: FragmentLoanHistoryBinding
    lateinit var mLoanHistoryViewModel: LoanHistoryViewModel
    var id: Long = 0L


    companion object {
        lateinit var mctx: Context
    }
    private var model: Communicator?=null






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mLoanHistoryBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_loan_history,
                container,
                false
            )

        val application = requireNotNull(this.activity).application
        val personalDAO = UserDatabase.getInstance(application).mUserDAO
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val loanDAO = UserDatabase.getInstance(application).mLoanDAO
        val bankDAO = UserDatabase.getInstance(application).mBankDAO
        val viewModelFactory = ViewModelProviderFactory(personalDAO, transactionDAO,loanDAO,bankDAO, application)
        mLoanHistoryViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(LoanHistoryViewModel::class.java)
        mLoanHistoryBinding.increaseHistoryViewModel = mLoanHistoryViewModel
        this.also { mLoanHistoryBinding.lifecycleOwner = it }

        val arg =
           LoanHistoryFragmentArgs.fromBundle(
                requireArguments()
            )
        id = arg.userId
        val mLoanAdapter = LoanAdapter()
        mLoanAdapter.setOnclickListener(AdapterListener3({
            if (it != 0L)
                this.findNavController().navigate(
                LoanHistoryFragmentDirections.actionLoanHistoryFragmentToLoanDetailFragment(it) )
            Log.d("TAG", "navTeat $it ")
        }, {
//            deleteDialog(it)
        }
        ))
        val mLinearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mLoanHistoryBinding.rvFeed.adapter = mLoanAdapter
        mLoanHistoryBinding.rvFeed.layoutManager = mLinearLayoutManager
        userInfo()
        mLoanHistoryViewModel.setUserName(id)?.observe(viewLifecycleOwner, {
            mLoanHistoryViewModel.setUserName(it)
        })
        mLoanHistoryViewModel.userName.observe(viewLifecycleOwner, {
            if (it != null) {
                mLoanHistoryBinding.userName = it
            }
        })
        return mLoanHistoryBinding.root
    }

//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        model= ViewModelProviders.of(requireActivity()).get(Communicator::class.java)
//        //listener onClick
//        mLoanHistoryBinding.test.setOnClickListener {view ->
//            //set the message to share to another fragment
//            model!!.setMsgCommunicator("$id")
//            //Launch the data receiver fragment
//            val myfragment = LoanListFragment()
//            val fragmentTransaction = requireFragmentManager().beginTransaction()
//            fragmentTransaction.replace(R.id.ll, myfragment)
//           ll.visibility=View.INVISIBLE
//            fragmentTransaction.addToBackStack(null)
//            fragmentTransaction.commit()
//        }
//    }


    private fun userInfo() {
        mLoanHistoryViewModel.getLoanList(id).observe(viewLifecycleOwner, {
            Log.d("TAG", "userInfo2: ${it}")
            mLoanHistoryViewModel.loanInfo.value = it
        })
    }
}


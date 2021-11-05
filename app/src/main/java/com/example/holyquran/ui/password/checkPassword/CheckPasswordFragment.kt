package com.example.holyquran.ui.password.checkPassword

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.holyquran.R
import com.example.holyquran.databinding.FragmentCheckPasswordBinding
import com.example.holyquran.databinding.FragmentCreatePasswordBinding

class CheckPasswordFragment : DialogFragment() {
    lateinit var mCheckPasswordBinding: FragmentCheckPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mCheckPasswordBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_check_password,
                container,
                false
            )
        mCheckPasswordBinding.submitPassword.setOnClickListener {
            val passKet = "Password"
            val sharedPreference =
                requireActivity().getSharedPreferences(passKet, Context.MODE_PRIVATE)

             if (mCheckPasswordBinding.currentPassword.text.toString() == sharedPreference.getString("password", "")){
                 this.findNavController().navigate(
                     CheckPasswordFragmentDirections.actionCheckPasswordFragmentToCreatePasswordFragment())
             }

        }
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return mCheckPasswordBinding.root
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
                screenWidth - 20.toPx(requireContext()),
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
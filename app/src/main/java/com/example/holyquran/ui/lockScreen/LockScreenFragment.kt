package com.example.holyquran.ui.lockScreen

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.holyquran.R
import com.example.holyquran.databinding.FragmentLockScreenBinding

class LockScreenFragment : DialogFragment() {
    lateinit var mLockScreenBinding: FragmentLockScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mLockScreenBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_lock_screen, container, false)

        var getEditTextValue = mLockScreenBinding.password.text.toString()
        if (getEditTextValue == "5") {
            Toast.makeText(activity, "work", Toast.LENGTH_SHORT).show()
        }
        mLockScreenBinding.submit.setOnClickListener {
            createPassword()
        }

        val passKet = "Password"
        val sharedPreference =
            requireActivity().getSharedPreferences(passKet, Context.MODE_PRIVATE)
        sharedPreference.getString("password", "defaultName")
        mLockScreenBinding.submit.text = sharedPreference.getString("password", "")

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return mLockScreenBinding.root
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

    private fun createPassword() {
        val passKet = "Password"
        val sharedPreference =
            requireActivity().getSharedPreferences(passKet, Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString("password", mLockScreenBinding.password.text.toString())
        editor.apply()
    }
}
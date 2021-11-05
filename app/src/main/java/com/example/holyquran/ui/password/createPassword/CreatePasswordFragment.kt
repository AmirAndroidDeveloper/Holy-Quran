package com.example.holyquran.ui.password.createPassword

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
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
import com.example.holyquran.databinding.FragmentCreatePasswordBinding

class CreatePasswordFragment : DialogFragment() {
    lateinit var mSetPasswordBinding: FragmentCreatePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mSetPasswordBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_create_password,
                container,
                false
            )
        mSetPasswordBinding.submit.setOnClickListener {
            checkPassword()
        }
        mSetPasswordBinding.clearPassword.setOnClickListener {
            clear()
        }
        val passKet = "Password"
        val sharedPreference =
            requireActivity().getSharedPreferences(passKet, Context.MODE_PRIVATE)
        sharedPreference.getString("password", "defaultName")
        if (sharedPreference.getBoolean("passwordStatus", false)) {
            mSetPasswordBinding.passwordStatus.text = "تغییر رمز"
        }else{
            mSetPasswordBinding.clearPassword.visibility=View.INVISIBLE
        }
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return mSetPasswordBinding.root
    }

    private fun checkPassword(): Boolean {
        val getEditTextValue = mSetPasswordBinding.password.text.toString()
        val getRepeatEditTextValue = mSetPasswordBinding.repeat.text.toString()

        if (getEditTextValue.isNotEmpty()){
            mSetPasswordBinding.passwordL.error = null
        }


        if (getEditTextValue != getRepeatEditTextValue) {
            mSetPasswordBinding.repeatL.error = "رمز با تکرار ان مطابقت ندارد."
        } else if (getEditTextValue.isEmpty()) {
            mSetPasswordBinding.passwordL.error = "رمزی وارد نشده است."
        }else {
            mSetPasswordBinding.repeatL.error = null
            createPassword()
            Toast.makeText(activity, "رمز با موفقیت ثبت شد.", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
            return true
        }

        return true
    }

    private fun createPassword() {
        val passKey = "Password"
        val sharedPreference =
            requireActivity().getSharedPreferences(passKey, Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString("password", mSetPasswordBinding.password.text.toString())
        editor.putBoolean("passwordStatus", true)
        editor.apply()
    }

    private fun clear() {
        val passKey = "Password"
        val passStatus = "passwordStatus"
        val settings = requireContext().getSharedPreferences(passKey, Context.MODE_PRIVATE)
        settings.edit().clear().apply()
        Toast.makeText(activity, "رمز با موفقیت حذف شد.", Toast.LENGTH_SHORT).show()

        findNavController().popBackStack()
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
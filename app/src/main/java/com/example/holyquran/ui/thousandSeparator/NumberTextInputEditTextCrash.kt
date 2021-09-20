package com.example.holyquran.ui.thousandSeparator

import android.content.Context
import android.text.Editable

import android.widget.EditText

import android.text.TextWatcher

import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import java.lang.StringBuilder
import java.math.BigDecimal
import java.text.DecimalFormat


class ThousandNumberEditText(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatEditText(context!!, attrs, defStyleAttr) {
    constructor(context: Context?) : this(context, null) {}
    constructor(context: Context?, attrs: AttributeSet?) : this(
        context,
        attrs,
        androidx.appcompat.R.attr.editTextStyle
    ) {
    }

    private fun init() {
        addTextChangedListener(ThousandNumberTextWatcher(this))
        setInputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
        setFilters(arrayOf<InputFilter>(LengthFilter(MAX_LENGTH)))
    }

    private class ThousandNumberTextWatcher internal constructor(private val mEditText: EditText) :
        TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            val originalString = editable.toString()
            val cleanString = originalString.replace("[,]".toRegex(), "")
            if (cleanString.isEmpty()) {
                return
            }
            val formattedString = getFormatString(cleanString)
            mEditText.removeTextChangedListener(this)
            mEditText.setText(formattedString)
            mEditText.setSelection(mEditText.text.length)
            mEditText.addTextChangedListener(this)
        }

        /**
         * Return the format string
         */
        private fun getFormatString(cleanString: String): String {
            return if (cleanString.contains(".")) {
                formatDecimal(cleanString)
            } else {
                formatInteger(cleanString)
            }
        }

        private fun formatInteger(str: String): String {
            val parsed = BigDecimal(str)
            val formatter: DecimalFormat
            formatter = DecimalFormat("#,###")
            return formatter.format(parsed)
        }

        private fun formatDecimal(str: String): String {
            if (str == ".") {
                return "."
            }
            val parsed = BigDecimal(str)
            val formatter: DecimalFormat
            formatter = DecimalFormat("#,###." + getDecimalPattern(str)) //example patter #,###.00
            return formatter.format(parsed)
        }

        /**
         * It will return suitable pattern for format decimal
         * For example: 10.2 -> return 0 | 10.23 -> return 00 | 10.235 -> return 000
         */
        private fun getDecimalPattern(str: String): String {
            val decimalCount = str.length - 1 - str.indexOf(".")
            val decimalPattern = StringBuilder()
            Log.d("TAG", "getDecimalPattern: $decimalPattern")
            var i = 0
            while (i < decimalCount && i < MAX_DECIMAL) {
                decimalPattern.append("0")
                i++
            }
            return decimalPattern.toString()
        }
    }

    companion object {
        // TODO: 14/09/2017 change it if you want
        private const val MAX_LENGTH = 20
        private const val MAX_DECIMAL = 3
    }

    init {
        init()
    }
}
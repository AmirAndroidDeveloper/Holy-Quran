package com.example.holyquran.ui

import android.content.Context
import android.text.TextUtils
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.google.android.material.textfield.TextInputEditText
import java.lang.NumberFormatException
import java.math.BigDecimal
import java.text.DecimalFormat


class NumberTextInputEditText : TextInputEditText {
    constructor(@NonNull context: Context?) : super(context!!) {}
    constructor(@NonNull context: Context?, @Nullable attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
    }

    constructor(
        @NonNull context: Context?,
        @Nullable attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context!!, attrs, defStyleAttr) {
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addTextChangedListener(textWatcher)
    }

    fun formatNumber(number: Double): String {
        val decimalFormat = DecimalFormat("#,###")
        return decimalFormat.format(number)
    }

    var textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            removeTextChangedListener(this)
            val text = text.toString()
            var format = ""
            if (!TextUtils.isEmpty(text)) {
                format = try {
                    formatNumber(BigDecimal(text.replace(",".toRegex(), "")).toString().toDouble())
                } catch (e: NumberFormatException) {
                    ""
                }
                setText(format)
                setSelection(format.length)
            }
            addTextChangedListener(this)
        }
    }
}
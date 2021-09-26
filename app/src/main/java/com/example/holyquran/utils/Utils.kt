package com.example.holyquran.utils

import java.text.DecimalFormat

fun toCurrencyFormat(money: String): String? {
    return try {
        val num = money.toInt()
        val number: String
        val numFormat = DecimalFormat("#,###,###")
        number = numFormat.format(num.toLong())
        number
    } catch (e: NumberFormatException) {
        money
    }
}
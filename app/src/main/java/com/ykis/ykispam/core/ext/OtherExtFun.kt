package com.ykis.ykispam.core.ext

import java.text.DecimalFormat

fun formatDebt(debt: Float): String {
    return DebtDecimalFormat.format(debt)
}

private val DebtDecimalFormat = DecimalFormat("#,##0.00")

fun Double.formatMoney():String {
   return DebtDecimalFormat.format(this)
}


fun Byte.isTrue(): Boolean = this == 1.toByte()

fun <E> List<E>.extractProportions(selector: (E) -> Float): List<Float> {
    val total = this.sumOf { selector(it).toDouble() }
    return this.map { (selector(it) / total).toFloat() }
}
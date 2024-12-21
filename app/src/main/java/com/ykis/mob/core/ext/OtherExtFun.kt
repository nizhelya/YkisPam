package com.ykis.mob.core.ext

import java.text.DecimalFormat

fun formatDebt(debt: Double): String {
    return DebtDecimalFormat.format(debt)
}

private val DebtDecimalFormat = DecimalFormat("#,##0.00")

fun Double.formatMoneyString():String {
   return DebtDecimalFormat.format(this)
}

fun Double.formatMoneyDouble():String {
    return DebtDecimalFormat.format(this)
}


fun Byte.isTrue(): Boolean = this == 1.toByte()

fun <E> List<E>.extractProportions(selector: (E) ->Double): List<Double> {
    val total = this.sumOf { selector(it) }
    return this.map { (selector(it) / total) }
}
package com.example.myweatherapp

import kotlinx.datetime.*
import kotlinx.datetime.format.*

class Utility(now: Instant = Clock.System.now()) {
    private val today: LocalDate = now.toLocalDateTime(TimeZone.currentSystemDefault()).date

    private val customFormat = LocalDate.Format {
        monthName(MonthNames.ENGLISH_ABBREVIATED); char(' '); dayOfMonth(); chars(", "); year()
    }
    init {
        println("Date is: " + today.format(customFormat))
    }
    fun displayDate() : String {
        return today.format(customFormat)
    }
}
package com.example.cryptrox.util

import java.text.SimpleDateFormat
import java.util.*

object Constants {

    const val BASE_URL = "https://pro-api.coinmarketcap.com"
    const val BASE_URL_IMAGE = "https://s2.coinmarketcap.com/static/img/coins/64x64/"


    fun formatTimestamp(timestamp: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MM-yyyy'T'HH:mm", Locale.getDefault())
        val date = inputFormat.parse(timestamp)
        return outputFormat.format(date!!)
    }

    fun formatTimeStampWithDateOnly(timestamp: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val parsedDate = inputFormat.parse(timestamp)
        return outputFormat.format(parsedDate!!)
    }

}
package ir.ayantech.pishkhansdk.helper.extensions

import ir.ayantech.whygoogle.helper.isNull
import ir.ayantech.whygoogle.helper.trying
import java.text.SimpleDateFormat
import java.util.Locale
import saman.zamani.persiandate.PersianDate

fun String.getFormattedPersianDateTime(): String {
    return if (this.contains("T"))
        this.getDate() + " " + this.getTime()
    else
        "نامشخص"
}

fun String.getDate(): String {
    var date = "-"
    trying {
        if (this.isNotEmpty()) {
            val grogDate = convertUtcToGregorian(this)
            if (grogDate.isNotEmpty()) {
                val persianDate = PersianDate()
                val jalali = persianDate.gregorian_to_jalali(grogDate[0], grogDate[1], grogDate[2])

                // Format month and day with two digits
                val formattedMonth = String.format("%02d", jalali[1])
                val formattedDay = String.format("%02d", jalali[2])

                date = "${jalali[0]}-$formattedMonth-$formattedDay"
            }
        }
    }
    return date
}

fun String.getTime(): String {
    return if (this.isNull()) ""
    else
        this.split("T")[1].replaceRange(5..7, "")
}

fun convertUtcToGregorian(utc: String): List<Int> {
    return if (utc.isNotEmpty()) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        var formattedDate = ""
        val date: MutableList<Int> = ArrayList()
        trying {
            val convertedDate = dateFormat.parse(utc.replace("T", " "))
            formattedDate = SimpleDateFormat("yyyy,MM,dd", Locale.US).format(convertedDate!!)
        }
        val values = formattedDate.split(",")
        for (value in values) {
            date.add(value.toInt())
        }
        date
    } else emptyList()
}
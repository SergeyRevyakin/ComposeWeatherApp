package ru.serg.widgets

import android.text.SpannableString
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import java.text.SimpleDateFormat
import java.util.Locale

object Utils {
    fun get12HoursFormat(bigFont: Int, smallFont: Int): CharSequence {
        val span1 = SpannableString("hh:mm")
        val span3 = SpannableString(" a")
        span1.setSpan(AbsoluteSizeSpan(bigFont, true), 0, span1.length, 0)
        span3.setSpan(AbsoluteSizeSpan(smallFont, true), 0, 2, 0)
        return TextUtils.concat(span1, span3)
    }

    fun get24HoursFormat(bigFont: Int): CharSequence {
        val span1 = SpannableString("HH:mm")
        span1.setSpan(AbsoluteSizeSpan(bigFont, true), 0, span1.length, 0)
        return TextUtils.concat(span1)
    }

    fun getDate(smallFont: Int): CharSequence {
        val span4 = SpannableString("E, dd MMM")
        span4.setSpan(AbsoluteSizeSpan(smallFont, true), 0, span4.length, 0)

        return TextUtils.concat(span4)
    }
}


fun getHour(l: Long?): String =
    SimpleDateFormat("HH:mm", Locale.getDefault()).format((l ?: 0L))

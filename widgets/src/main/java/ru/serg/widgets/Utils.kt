package ru.serg.widgets

import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.AlignmentSpan
import android.text.style.RelativeSizeSpan
import java.text.SimpleDateFormat
import java.util.Locale

object Utils {
    fun get12HoursFormat(): CharSequence {
        val span1 = SpannableString("hh:mm")
        //  val span2 = SpannableString("ss")
        val span3 = SpannableString(" a\n")
        val span4 = SpannableString("E, dd MMM")
        span1.setSpan(AbsoluteSizeSpan(32, true), 0, span1.length, 0)
        //  span2.setSpan(RelativeSizeSpan(0.60f), 0, 2, 0)
        span3.setSpan(AbsoluteSizeSpan(18, true), 0, 2, 0)
        span4.setSpan(AbsoluteSizeSpan(18, true), 0, span4.length, 0)

        return TextUtils.concat(span1, span3, span4)
    }

    fun get24HoursFormat(): CharSequence {
        val span1 = SpannableString("HH:mm\n")
        val span4 = SpannableString("E, dd MMM")
        span1.setSpan(AbsoluteSizeSpan(32, true), 0, span1.length, 0)
        span4.setSpan(AbsoluteSizeSpan(18, true), 0, span4.length, 0)

        return TextUtils.concat(span1, span4)
    }

    fun getDashBoard12HoursFormat(): CharSequence {
        val span1 = SpannableString("hh:mm")
        val span2 = SpannableString(":ss ")
        val span3 = SpannableString("a\n")
        val span4 = SpannableString("E, dd MMMM yyyy")

        span2.setSpan(RelativeSizeSpan(0.50f), 0, span2.length, 0)
        span3.setSpan(RelativeSizeSpan(0.50f), 0, span3.length, 0)
        span4.setSpan(RelativeSizeSpan(0.40f), 0, span4.length, 0)

        return TextUtils.concat(span1, span2, span3, span4)
    }

    fun getItem12HoursFormat(): CharSequence {
        val span1 = SpannableString("hh:mm")
        // val span2 = SpannableString(" : ss ")
        val span3 = SpannableString(" a ")
        val span4 = SpannableString("E, dd MMMM yyyy")

        span3.setSpan(RelativeSizeSpan(0.90f), 0, span3.length, 0)
        span4.setSpan(RelativeSizeSpan(0.90f), 0, span4.length, 0)

        return TextUtils.concat(span1, span3, span4)
    }

    fun getItem24HoursFormat(): CharSequence {
        val span1 = SpannableString("HH:mm")
        val span4 = SpannableString("E, dd MMMM yyyy")
        //  span4.setSpan(RelativeSizeSpan(0.50f), 0, span4.length, 0)
        span4.setSpan(
            AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE),
            0,
            span4.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return TextUtils.concat(span1, span4)
    }

    fun getDashBoard24HoursFormat(): CharSequence {
        val span1 = SpannableString("HH:mm")
        val span2 = SpannableString(":ss\n ")
        val span4 = SpannableString("E, dd MMMM yyyy")
        span2.setSpan(RelativeSizeSpan(0.50f), 0, span2.length, 0)

        span4.setSpan(RelativeSizeSpan(0.35f), 0, span4.length, 0)

        return TextUtils.concat(span1, span2, span4)
    }
}


fun getHour(l: Long?): String =
    SimpleDateFormat("HH:mm", Locale.getDefault()).format((l ?: 0L))

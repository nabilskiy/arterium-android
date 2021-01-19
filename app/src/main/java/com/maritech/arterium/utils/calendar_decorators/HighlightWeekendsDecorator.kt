package com.maritech.arterium.utils.calendar_decorators

import android.content.Context
import android.text.style.ForegroundColorSpan
import android.util.TimeUtils
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.util.*

class HighlightWeekendsDecorator(val color: Int) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        val weekDay = day?.toCalendar()?.get(Calendar.DAY_OF_WEEK) ?: return false
        return weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(ForegroundColorSpan(color))
    }
}

fun CalendarDay.toCalendar() = Calendar.getInstance().apply {
    set(Calendar.YEAR, year)
    set(Calendar.MONTH, month - 1)
    set(Calendar.DAY_OF_MONTH, day)
}

fun Calendar.toCalendarDay(): CalendarDay {
    return CalendarDay.from(
            get(Calendar.YEAR),
            get(Calendar.MONTH) + 1,
            get(Calendar.DAY_OF_MONTH)
    )
}

fun Context.getCompatDrawable(@DrawableRes idDrawable: Int) = ContextCompat.getDrawable(this, idDrawable)
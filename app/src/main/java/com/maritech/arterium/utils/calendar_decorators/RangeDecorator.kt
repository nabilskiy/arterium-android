package com.maritech.arterium.utils.calendar_decorators

import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class RangeDecorator(var firstRangeDay: CalendarDay? = null,
                     var lastRangeDay: CalendarDay? = null,
                     val drawable: Drawable? = null) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        if(firstRangeDay == null || lastRangeDay == null || day == null) return false

        return day.isAfter(firstRangeDay!!) && day.isBefore(lastRangeDay!!)
    }

    override fun decorate(view: DayViewFacade?) {
        if(drawable  == null) return

        view?.setBackgroundDrawable(drawable)
    }

    fun clear() {
        firstRangeDay = null
        lastRangeDay = null
    }
}
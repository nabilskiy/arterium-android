package com.maritech.arterium.utils.calendar_decorators

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class MinDateRange(var minDay: CalendarDay? = null) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.isBefore(minDay ?: return false) ?: false
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setDaysDisabled(true)
    }

    fun clear() {
        minDay = null
    }
}
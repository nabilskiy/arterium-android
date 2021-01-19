package com.maritech.arterium.utils.calendar_decorators

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class MaxDateRange(var maxDay: CalendarDay? = null) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.isAfter(maxDay ?: return false) ?: false
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setDaysDisabled(true)
    }

    fun clear() {
        maxDay = null
    }
}
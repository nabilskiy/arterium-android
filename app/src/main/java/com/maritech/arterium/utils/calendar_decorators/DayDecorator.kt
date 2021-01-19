package com.maritech.arterium.utils.calendar_decorators

import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class DayDecorator(var day: CalendarDay? = null,val drawable: Drawable? = null) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay?) = this.day?.equals(day) == true

    override fun decorate(view: DayViewFacade?) {
        if(drawable == null) return

        view?.setBackgroundDrawable(drawable)
    }

    fun clear() {
        day = null
    }
}
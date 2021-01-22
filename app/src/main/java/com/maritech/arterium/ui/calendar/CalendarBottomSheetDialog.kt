package com.maritech.arterium.ui.calendar

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.maritech.arterium.R
import com.maritech.arterium.databinding.BottomSheetDialogCalendarBinding

import com.maritech.arterium.utils.args.*
import com.maritech.arterium.utils.calendar_decorators.*
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import java.util.*




class CalendarBottomSheetDialog : BottomSheetDialogFragment() {

    private val EXTRA_MIN_DATE = "extra_min_date"
    private val EXTRA_MAX_DATE = "extra_max_date"
    private val EXTRA_SELECT_DATE = "extra_select_date"

    private val EXTRA_SELECT_DATA_FROM = "extra_select_data_from"
    private val EXTRA_SELECT_DATA_TO = "extra_select_data_to"

    private val EXTRA_MAX_SELECTION_RANGE = "extra_max_selection_range"

    private val EXTRA_TITLE = "extra_title"

    companion object {

        fun newInstance(
            dateSelectListener: OnDateSelectedListener,
            selectDateOptions: SelectDateOptions = SelectDateOptions(),
            title: String? = null
        ) = newInstance(
            dateSelectListener,
            selectDateOptions.selectDate,
            selectDateOptions.minDate,
            selectDateOptions.maxDate,
            title
        )

        fun newInstance(
            dateSelectListener: OnRangeSelectedListener,
            title: String? = null
        ): CalendarBottomSheetDialog {
            val selectRangeOptions = SelectRangeOptions()
            return newInstance(
                    dateSelectListener,
                    selectRangeOptions.selectDate,
                    selectRangeOptions.minDate,
                    selectRangeOptions.maxDate,
                    selectRangeOptions.maxSelectionRange,
                    title
            )
        }

        fun newInstance(
            rangeSelectListener: OnRangeSelectedListener,
            selectRangeOptions: SelectRangeOptions,
            title: String? = null
        ) = newInstance(
            rangeSelectListener,
            selectRangeOptions.selectDate,
            selectRangeOptions.minDate,
            selectRangeOptions.maxDate,
            selectRangeOptions.maxSelectionRange,
            title)

        fun newInstance(
            dateSelectListener: OnDateSelectedListener,
            selectDate: Long? = null,
            minDate: Long? = null,
            maxDate: Long? = null,
            title: String? = null
        ) = CalendarBottomSheetDialog().apply {

            onDateSelectListener = dateSelectListener
            calendarType = CalendarType.SINGLE

            withArgs {
                putLongOrNull(EXTRA_SELECT_DATE, selectDate)
                putLongOrNull(EXTRA_MIN_DATE, minDate)
                putLongOrNull(EXTRA_MAX_DATE, maxDate)
                putString(EXTRA_TITLE, title)
            }
        }

        fun newInstance(
            rangeSelectListener: OnRangeSelectedListener,
            selectRange: Pair<Long?, Long?>? = null,
            minDate: Long? = null,
            maxDate: Long? = null,
            maxSelectionRange: Int? = null,
            title: String? = null
        ) = CalendarBottomSheetDialog().apply {

            onRangeSelectListener = rangeSelectListener
            calendarType = CalendarType.RANGE

            withArgs {
                putLongOrNull(EXTRA_MIN_DATE, minDate)
                putLongOrNull(EXTRA_MAX_DATE, maxDate)

                putLongOrNull(EXTRA_SELECT_DATA_FROM, selectRange?.first)
                putLongOrNull(EXTRA_SELECT_DATA_TO, selectRange?.second)

                putIntOrNull(EXTRA_MAX_SELECTION_RANGE, maxSelectionRange)

                putString(EXTRA_TITLE, title)
            }
        }

        val TAG: String = CalendarBottomSheetDialog::javaClass.name
    }

    private var onDateSelectListener: OnDateSelectedListener? = null
    private var onRangeSelectListener: OnRangeSelectedListener? = null

    private lateinit var binding: BottomSheetDialogCalendarBinding
    private lateinit var calendarType: CalendarType

    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?) = BottomSheetDialog(requireContext(), theme)

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.bottom_sheet_dialog_calendar,
            null,
            false
        )

        dialog.setContentView(binding.root)


        val bottomSheetBehavior = BottomSheetBehavior.from(binding.root.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.skipCollapsed = true

        (binding.root.parent as ViewGroup).clipToPadding = false

        initCalendar()
    }

    private fun initCalendar() {
        val minDate = getCalendarDayByTimeStamp(arguments?.getLongOrNull(EXTRA_MIN_DATE))
        val maxDate = getCalendarDayByTimeStamp(arguments?.getLongOrNull(EXTRA_MAX_DATE))

        binding.calendarView.state().edit()
            .setMinimumDate(minDate)
            .setMaximumDate(maxDate)
            .commit()

        binding.calendarView.showOtherDates = MaterialCalendarView.SHOW_OUT_OF_RANGE

        when (calendarType) {
            CalendarType.SINGLE -> initSingleCalendar()
            CalendarType.RANGE -> initRangeCalendar()
        }
    }

    private fun initSingleCalendar() = with(binding) {
        binding.tvTitle.text = arguments?.getString(EXTRA_TITLE)?.takeIf { it.isEmpty().not() } ?: ""

        val selectedDate: CalendarDay? = getCalendarDayByTimeStamp(arguments?.getLongOrNull(EXTRA_SELECT_DATE))
        val dayDecorator = DayDecorator(selectedDate)

        calendarView.selectionMode = MaterialCalendarView.SELECTION_MODE_SINGLE

        calendarView.setCurrentDate(selectedDate, true)
        calendarView.selectedDate = selectedDate

        calendarView.addDecorator(dayDecorator)

        calendarView.setTitleFormatter(TitleFormatter.DEFAULT)

        calendarView.setOnDateChangedListener { widget, date, isSelected ->
            dayDecorator.day = date.takeIf { isSelected }

            widget.invalidateDecorators()

            btnApply.isEnabled = isSelected
        }
        calendarView.invalidateDecorators()

        btnApply.setOnClickListener {
            onDateSelectListener?.onDateSelected(dayDecorator.day!!.toCalendar().timeInMillis)
            dismiss()
        }

        btnApply.isEnabled = selectedDate != null
    }

    private fun initRangeCalendar() = with(binding) {
        binding.tvTitle.text = arguments?.getString(EXTRA_TITLE)?.takeIf { it.isEmpty().not() } ?: ""

        val selectRange = Pair(
            getCalendarDayByTimeStamp(arguments?.getLongOrNull(EXTRA_SELECT_DATA_FROM)),
            getCalendarDayByTimeStamp(arguments?.getLongOrNull(EXTRA_SELECT_DATA_TO))
        )

        val maxSelectionRange = arguments?.getIntOrNull(EXTRA_MAX_SELECTION_RANGE)?.minus(1)

        val dayDecorator = DayDecorator(drawable = requireContext().getCompatDrawable(R.drawable.ic_choose_day)).apply {
            day = selectRange.first?.takeIf { selectRange.first == selectRange.second }
        }

        val decoratorRange = RangeDecorator(drawable = requireContext().getCompatDrawable(R.drawable.calendar_decorator_range)).apply {
            firstRangeDay = selectRange.first?.takeIf { it != selectRange.second }
            lastRangeDay = selectRange.second?.takeIf { it != selectRange.first }
        }

        val decoratorFirst = DayDecorator(drawable = requireContext().getCompatDrawable(R.drawable.calendar_decorator_first)).apply {
            day = selectRange.first?.takeIf { it != selectRange.second }
        }

        val decoratorEnd = DayDecorator(drawable = requireContext().getCompatDrawable(R.drawable.calendar_decorator_end)).apply {
            day = selectRange.second?.takeIf { it != selectRange.first }
        }

        val minDateRange = MinDateRange()
        val maxDateRange = MaxDateRange()

        calendarView.addDecorator(minDateRange)
        calendarView.addDecorator(maxDateRange)
        calendarView.addDecorator(dayDecorator)
        calendarView.addDecorator(decoratorFirst)
        calendarView.addDecorator(decoratorEnd)
        calendarView.addDecorator(decoratorRange)

        calendarView.selectionMode = MaterialCalendarView.SELECTION_MODE_RANGE

        calendarView.selectRange(selectRange.first, selectRange.second)
        calendarView.setCurrentDate(selectRange.first, true)

        calendarView.setOnDateChangedListener { widget, date, isSelected ->
            dayDecorator.clear()
            decoratorRange.clear()
            decoratorFirst.clear()
            decoratorEnd.clear()
            minDateRange.clear()
            maxDateRange.clear()

            if (maxSelectionRange != null && isSelected) {

                minDateRange.minDay = date.toCalendar().apply {
                    add(Calendar.DAY_OF_MONTH, maxSelectionRange.unaryMinus())
                }.toCalendarDay()

                maxDateRange.maxDay = date.toCalendar().apply {
                    add(Calendar.DAY_OF_MONTH, maxSelectionRange)
                }.toCalendarDay()
            }

            dayDecorator.day = date.takeIf { isSelected }

            widget.invalidateDecorators()

            btnApply.isEnabled = isSelected
        }

        calendarView.setOnRangeSelectedListener { widget, dates ->
            decoratorFirst.clear()
            decoratorEnd.clear()
            decoratorRange.clear()
            dayDecorator.clear()

            val dateFrom = dates.firstOrNull()
            val dateTo = dates.lastOrNull()

            decoratorRange.firstRangeDay = dateFrom
            decoratorRange.lastRangeDay = dateTo

            decoratorFirst.day = dateFrom
            decoratorEnd.day = dateTo

            widget.invalidateDecorators()
        }

        calendarView.invalidateDecorators()

        btnApply.setOnClickListener {
            if (decoratorRange.firstRangeDay != null && decoratorRange.lastRangeDay != null) {
                onRangeSelectListener?.onRangeSelected(
                    decoratorRange.firstRangeDay!!.toCalendar().timeInMillis,
                    decoratorRange.lastRangeDay!!.toCalendar().timeInMillis
                )
                dismiss()
                return@setOnClickListener
            }

            onRangeSelectListener?.onRangeSelected(
                dayDecorator.day!!.toCalendar().timeInMillis,
                dayDecorator.day!!.toCalendar().timeInMillis
            )
            dismiss()
        }

        btnApply.isEnabled = selectRange.first != null == true && selectRange.second != null == true
    }

    interface OnRangeSelectedListener {
        fun onRangeSelected(dateFrom: Long, dateTo: Long)
    }

    interface OnDateSelectedListener {
        fun onDateSelected(date: Long)
    }

    private enum class CalendarType {
        SINGLE, RANGE
    }

    private fun getCalendarDayByTimeStamp(timeInMilliseconds: Long?): CalendarDay? {
        return timeInMilliseconds?.let { Calendar.getInstance().apply { timeInMillis = it }.toCalendarDay() }
    }

    data class SelectDateOptions(
        var selectDate: Long? = null,
        var minDate: Long? = null,
        var maxDate: Long? = null
    )

    data class SelectRangeOptions(
        var selectDate: Pair<Long, Long>? = null,
        var minDate: Long? = null,
        var maxDate: Long? = null,
        var maxSelectionRange: Int? = null
    )
}
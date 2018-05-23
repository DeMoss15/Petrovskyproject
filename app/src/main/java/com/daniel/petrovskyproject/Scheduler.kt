package com.daniel.petrovskyproject

import java.util.*
import kotlin.collections.ArrayList

class Scheduler {

    private val startingPoint = GregorianCalendar()
    private val startingPointShift = 5
    private val scheduleCycle = arrayListOf("0",
            "0", "1", "1", "1", "1",
            "0", "2", "2", "2", "2",
            "0", "3", "3", "3", "3")

    init {
        startingPoint.set(2017, 0, 1)
    }

    fun getToday(): DaySchedule {
        val date = GregorianCalendar()
        var shift = startingPointShift
        shift += daysBetween(startingPoint, date) % scheduleCycle.size

        return DaySchedule(
                date.clone() as Calendar,
                scheduleCycle[shift % scheduleCycle.size],
                scheduleCycle[(shift + 4) % scheduleCycle.size],
                scheduleCycle[(shift + 12) % scheduleCycle.size],
                scheduleCycle[(shift + 8) % scheduleCycle.size]
        )
    }

    fun setDate(date: Calendar): ArrayList<DaySchedule> {
        date.add(Calendar.DAY_OF_MONTH, -8)
        val list = ArrayList<DaySchedule>()
        var shift = startingPointShift
        shift += daysBetween(startingPoint, date) % scheduleCycle.size

        for (i in 0..16){
            list.add(DaySchedule(
                    date.clone() as Calendar,
                    scheduleCycle[shift % scheduleCycle.size],
                    scheduleCycle[(shift + 4) % scheduleCycle.size],
                    scheduleCycle[(shift + 12) % scheduleCycle.size],
                    scheduleCycle[(shift + 8) % scheduleCycle.size]
                    ))

            date.add(Calendar.DAY_OF_MONTH, 1)
            shift = (shift + 1) % scheduleCycle.size
        }

        return list
    }

    fun getForward(date: Calendar): ArrayList<DaySchedule> {
        val list = ArrayList<DaySchedule>()
        var shift = startingPointShift
        shift += daysBetween(startingPoint, date) % scheduleCycle.size

        for (i in 0..10){
            list.add(DaySchedule(
                    date.clone() as Calendar,
                    scheduleCycle[shift % scheduleCycle.size],
                    scheduleCycle[(shift + 4) % scheduleCycle.size],
                    scheduleCycle[(shift + 12) % scheduleCycle.size],
                    scheduleCycle[(shift + 8) % scheduleCycle.size]
            ))

            date.add(Calendar.DAY_OF_MONTH, 1)
            shift = (shift + 1) % scheduleCycle.size
        }

        return list
    }

    fun getBackward(date: Calendar): ArrayList<DaySchedule> {
        date.add(Calendar.DAY_OF_MONTH, -11)
        val list = ArrayList<DaySchedule>()
        var shift = startingPointShift
        shift += daysBetween(startingPoint, date) % scheduleCycle.size

        for (i in 0..10){
            list.add(DaySchedule(
                    date.clone() as Calendar,
                    scheduleCycle[shift % scheduleCycle.size],
                    scheduleCycle[(shift + 4) % scheduleCycle.size],
                    scheduleCycle[(shift + 12) % scheduleCycle.size],
                    scheduleCycle[(shift + 8) % scheduleCycle.size]
            ))

            date.add(Calendar.DAY_OF_MONTH, 1)
            shift = (shift + 1) % scheduleCycle.size
        }

        return list
    }

    private fun daysBetween(day1: Calendar, day2: Calendar): Int {
        var dayOne = day1.clone() as Calendar
        var dayTwo = day2.clone() as Calendar

        if (dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR)) {
            return Math.abs(dayOne.get(Calendar.DAY_OF_YEAR) - dayTwo.get(Calendar.DAY_OF_YEAR))
        } else {
            if (dayTwo.get(Calendar.YEAR) > dayOne.get(Calendar.YEAR)) {
                //swap them
                val temp = dayOne
                dayOne = dayTwo
                dayTwo = temp
            }
            var extraDays = 0

            val dayOneOriginalYearDays = dayOne.get(Calendar.DAY_OF_YEAR)

            while (dayOne.get(Calendar.YEAR) > dayTwo.get(Calendar.YEAR)) {
                dayOne.add(Calendar.YEAR, -1)
                // getActualMaximum() important for leap years
                extraDays += dayOne.getActualMaximum(Calendar.DAY_OF_YEAR)
            }

            return extraDays - dayTwo.get(Calendar.DAY_OF_YEAR) + dayOneOriginalYearDays
        }
    }
}
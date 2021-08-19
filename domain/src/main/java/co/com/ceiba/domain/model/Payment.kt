package co.com.ceiba.domain.model

import co.com.ceiba.domain.exception.ParkingValueNotValidException
import java.util.*

class Payment(
    private val dateEntry: Date,
    private val parkingHourValue: Int,
    private val parkingDayValue: Int
) {
    private val initRangeHoursDay = 9
    private val endRangeHoursDay = 24

    init {
        validParkingValues()
    }

    private fun validParkingValues() {
        if (dateEntry == null || parkingHourValue == null || parkingDayValue == null)
            throw ParkingValueNotValidException()

        if (parkingDayValue <= 0 || parkingHourValue <= 0)
            throw ParkingValueNotValidException()
    }

    fun calculatePayment(): Long {
        var parkingHours = getParkingHours()
        var hoursToPay : Long = 0
        var daysToPay : Long = 0

        if (parkingHours / endRangeHoursDay > 0){
            daysToPay = parkingHours / endRangeHoursDay
            parkingHours %= endRangeHoursDay
        }

        if (parkingHours >= initRangeHoursDay){
            daysToPay++
        }else{
            hoursToPay = parkingHours
        }

        return ( (daysToPay * parkingDayValue) + (hoursToPay * parkingHourValue) )
    }

    private fun getParkingHours(): Long {
        var diff = Date().time - dateEntry.time
        return diff / ( 60 * 60 * 1000)
    }

}
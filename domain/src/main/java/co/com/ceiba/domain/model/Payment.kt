package co.com.ceiba.domain.model

import co.com.ceiba.domain.exception.ParkingValueNotValidException
import java.util.*

class Payment(
    val vehicle: Vehicle
) {
    private val initRangeHoursDay = 9
    private val endRangeHoursDay = 24
    private val notDaysInParking = 0
    private val millisecondsInOneSecond = 1000
    private val secondsInOneMinute = 60
    private val minutesInOneHour = 60

    init {
        validParkingValues()
    }

    private fun validParkingValues() {
        if (vehicle == null){
            throw ParkingValueNotValidException()
        }

        if (vehicle.entryDate == null || vehicle.parkingHourValue == null || vehicle.parkingDayValue == null)
            throw ParkingValueNotValidException()

        if (vehicle.parkingDayValue <= 0 || vehicle.parkingHourValue <= 0)
            throw ParkingValueNotValidException()
    }

    @JvmName("getVehiclePayment")
    fun getVehicle(): Vehicle{
        return vehicle
    }

    fun calculatePayment(): Long {
        var parkingHours = getParkingHours()
        var hoursToPay : Long = 0
        var daysToPay : Long = 0
        var totalValue : Long = 0

        if (parkingHours / endRangeHoursDay > notDaysInParking){
            daysToPay = parkingHours / endRangeHoursDay
            parkingHours %= endRangeHoursDay
        }

        if (parkingHours >= initRangeHoursDay){
            daysToPay++
        }else{
            hoursToPay = parkingHours
        }

        totalValue =  (daysToPay * vehicle.parkingDayValue!!) + (hoursToPay * vehicle.parkingHourValue!!)

        if (vehicle.hasToValidCylinderCapacityInPayment() &&
            vehicle.cylinderCapacity > vehicle.maximumCylinderCapacityToNotPayExtraValue){
            totalValue += vehicle.payExtraValue.toLong()
        }

        return totalValue
    }

    private fun getParkingHours(): Long {
        var diff = Date().time - vehicle.entryDate!!.time
        var minutesDiff = (diff / (secondsInOneMinute * millisecondsInOneSecond))
        var parkingHours = if(minutesDiff <= minutesInOneHour){ 1 } else{
            (diff / ( minutesInOneHour * secondsInOneMinute * millisecondsInOneSecond))
        }
        return parkingHours
    }

}
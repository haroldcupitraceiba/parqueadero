package co.com.ceiba.domain.model

import co.com.ceiba.domain.exception.ParkingValueNotValidException
import java.util.*

class Payment(
    val vehicle: Vehicle
) {
    private val initRangeHoursDay = 9
    private val endRangeHoursDay = 24

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

        if (parkingHours / endRangeHoursDay > 0){
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
        var minutesDiff = (diff / (60 * 1000))
        var parkingHours = if(minutesDiff <= 60){ 1 } else{
            (diff / ( 60 * 60 * 1000))
        }
        return parkingHours
    }

}
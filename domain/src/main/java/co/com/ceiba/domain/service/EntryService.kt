package co.com.ceiba.domain.service

import android.util.Log
import co.com.ceiba.domain.model.Vehicle
import co.com.ceiba.domain.exception.InvalidVehicleToAddParkingLotException
import co.com.ceiba.domain.exception.LicensePlateEntryDeniedException
import co.com.ceiba.domain.exception.VehicleLimitException
import co.com.ceiba.domain.repository.VehicleRepository
import java.text.SimpleDateFormat
import java.util.*

class EntryService(
    private val vehicle: Vehicle,
    private var vehicleRepository: VehicleRepository,
    private val parkingAmount: ParkingAmount
) {
    fun saveVehicle(){
        if (!parkingAmount.hasReachedVehicleLimit()){
            throw VehicleLimitException()
        }

        if(isLicensePlateInvalidToday()){
            throw LicensePlateEntryDeniedException()
        }

        if (vehicleRepository.vehicleExists(vehicle.licensePlate) != null){
            throw InvalidVehicleToAddParkingLotException()
        }

        vehicleRepository.saveVehicle(vehicle)

    }

    fun searchVehicle(): Vehicle?{
        return vehicleRepository.vehicleExists(vehicle.licensePlate)
    }

    fun deleteVehicle(){
        vehicleRepository.deleteVehicle(vehicle.licensePlate)
    }

    private fun isLicensePlateInvalidToday(): Boolean {
        val calendar = Calendar.getInstance()
        calendar.time = vehicle.entryDate
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return vehicle.licensePlate.substring(0,1).equals("A") &&
                (dayOfWeek != 1 && dayOfWeek != 2)

    }
}
package co.com.ceiba.domain.repository

import co.com.ceiba.domain.model.Vehicle

interface VehicleRepository {
    fun vehicleExists(licensePlate:String) : Vehicle?
    fun saveVehicle(vehicle: Vehicle)
    fun deleteVehicle(licensePlate: String)
}
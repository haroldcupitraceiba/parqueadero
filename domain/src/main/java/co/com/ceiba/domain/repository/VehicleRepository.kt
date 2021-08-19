package co.com.ceiba.domain.repository

import co.com.ceiba.domain.model.Vehicle

interface VehicleRepository {
    //fun fullAmount(vehicleType: String)
    suspend fun vehicleExists(licensePlate:String) : Vehicle?
    suspend fun saveVehicle(vehicle: Vehicle)
    suspend fun deleteVehicle(licensePlate: String)
}
package co.com.ceiba.domain.service

import co.com.ceiba.domain.model.Vehicle
import co.com.ceiba.domain.exception.InvalidLicensePlateToDeleteVehicleParkingLotException
import co.com.ceiba.domain.exception.InvalidVehicleToAddParkingLotException
import co.com.ceiba.domain.repository.VehicleRepository
import javax.inject.Inject

class VehicleService constructor(var vehicleRepository: VehicleRepository) {

    suspend fun saveVehicle(vehicle: Vehicle){
        if (vehicleRepository.vehicleExists(vehicle.licensePlate) == null){
            vehicleRepository.saveVehicle(vehicle)
        }else{
            throw InvalidVehicleToAddParkingLotException()
        }
    }

    suspend fun deleteVehicle(licensePlate: String){
        if (!licensePlate.isNullOrEmpty()){
            vehicleRepository.deleteVehicle(licensePlate)
        }else{
            throw InvalidLicensePlateToDeleteVehicleParkingLotException()
        }

    }
}
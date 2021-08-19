package co.com.ceiba.application

import co.com.ceiba.domain.model.Vehicle
import co.com.ceiba.domain.service.VehicleService
import javax.inject.Inject

class VehicleApplicationService @Inject constructor(@Inject var vehicleService: VehicleService){

    fun saveVehicle(vehicle: Vehicle) {
        vehicleService.saveVehicle(vehicle)
    }

    fun deleteVehicle(licensePlate: String){
        vehicleService.deleteVehicle(licensePlate)
    }
}
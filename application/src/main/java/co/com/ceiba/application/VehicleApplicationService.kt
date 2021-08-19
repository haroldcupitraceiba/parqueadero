package co.com.ceiba.application

import co.com.ceiba.domain.model.Vehicle
import co.com.ceiba.domain.service.EntryService
import co.com.ceiba.domain.service.VehicleService
import javax.inject.Inject

class VehicleApplicationService (var entryService: EntryService){

    suspend fun saveVehicle() {
        entryService.saveVehicle()
    }

    suspend fun deleteVehicle(){
        entryService.deleteVehicle()
    }
}
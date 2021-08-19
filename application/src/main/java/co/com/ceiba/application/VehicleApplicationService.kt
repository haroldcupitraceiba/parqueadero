package co.com.ceiba.application

import co.com.ceiba.domain.model.Vehicle
import co.com.ceiba.domain.service.EntryService
import co.com.ceiba.domain.service.VehicleService
import javax.inject.Inject

class VehicleApplicationService(private var entryService: EntryService){

    fun saveVehicle() {
        entryService.saveVehicle()
    }

    fun searchVehicle(): Vehicle? {
        return entryService.searchVehicle()
    }

    fun deleteVehicle(){
        entryService.deleteVehicle()
    }
}
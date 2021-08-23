package co.com.ceiba.application

import co.com.ceiba.domain.model.Payment
import co.com.ceiba.domain.model.Vehicle
import co.com.ceiba.domain.service.EntryService

class VehicleApplicationService(private var entryService: EntryService){

    fun saveVehicle() {
        entryService.saveVehicle()
    }

    fun getPaymentVehicle(): Payment? {
        return entryService.getPayment()
    }

    fun deleteVehicle(){
        entryService.deleteVehicle()
    }
}
package co.com.ceiba.parqueadero.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.com.ceiba.application.VehicleApplicationService
import co.com.ceiba.domain.service.EntryService

class VehicleViewModelFactory(
    private val vehicleApplicationService: VehicleApplicationService
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VehicleViewModel() as T
    }

}
package co.com.ceiba.parqueadero.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import co.com.ceiba.application.VehicleApplicationService
import co.com.ceiba.domain.model.Vehicle
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class VehicleViewModel @Inject constructor(
    private val vehicleService: VehicleApplicationService
)  : ViewModel() {


    private lateinit var vehicleSaved:MutableLiveData<Boolean>
    private lateinit var vehicleDeleted:MutableLiveData<Boolean>

    fun executeSaveVehicle(vehicle: Vehicle) : LiveData<Boolean> {
        vehicleSaved.let {
            vehicleSaved = MutableLiveData()
            saveVehicle(vehicle)
        }
        return vehicleSaved
    }

    fun executeDeleteVehicle(licensePlate: String) : LiveData<Boolean> {
        vehicleDeleted.let {
            vehicleDeleted = MutableLiveData()
            deleteVehicle(licensePlate)
        }
        return vehicleDeleted
    }

    private fun deleteVehicle(licensePlate: String) {
        try {
            vehicleService.deleteVehicle(licensePlate)
            vehicleDeleted.value = true
        }catch (ex: Exception){
            ex.printStackTrace()
            vehicleDeleted.value = false
        }
    }

    private fun saveVehicle(vehicle: Vehicle) {
        try {
            vehicleService.saveVehicle(vehicle)
            vehicleSaved.value = true
        }catch (ex: Exception){
            ex.printStackTrace()
            vehicleSaved.value = false
        }
    }
}
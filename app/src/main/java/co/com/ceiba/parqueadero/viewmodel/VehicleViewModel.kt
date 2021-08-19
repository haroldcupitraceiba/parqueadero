package co.com.ceiba.parqueadero.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.com.ceiba.application.VehicleApplicationService
import co.com.ceiba.domain.model.Vehicle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class VehicleViewModel: ViewModel() {

    private var vehicleSaved:MutableLiveData<Boolean>? = null
    private var vehicleDeleted:MutableLiveData<Boolean>? = null
    private var message:MutableLiveData<String>? = null
    private lateinit var vehicleService: VehicleApplicationService

    fun setVehicleService(vehicleService: VehicleApplicationService){
        this.vehicleService = vehicleService
    }

    fun observeSaveVehicle() : LiveData<Boolean> {
        if (vehicleSaved == null){
            vehicleSaved = MutableLiveData()
        }
        return vehicleSaved!!
    }

    fun observeDeleteVehicle() : LiveData<Boolean> {
        if(vehicleDeleted == null) {
            vehicleDeleted = MutableLiveData()
        }
        return vehicleDeleted!!
    }

    fun observeInfoMessage() : LiveData<String> {
        if (message == null){
            message = MutableLiveData()
        }
        return message!!
    }

    fun executeDeleteVehicle() {
        viewModelScope.launch {
            try {
                vehicleService.deleteVehicle()
                vehicleDeleted?.value = true
            }catch (ex: Exception){
                message?.value = ex.message
                vehicleDeleted?.value = false
            }
        }
    }

    fun executeSaveVehicle() {
        viewModelScope.launch {
            try {
                vehicleService.saveVehicle()
                vehicleSaved?.value = true
            }catch (ex: Exception){
                message?.value = ex.message
                vehicleSaved?.value = false
            }
        }
    }
}
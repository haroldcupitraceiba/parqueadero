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
class VehicleViewModel(
    private val vehicleService: VehicleApplicationService
)  : ViewModel() {


    private var vehicleSaved:MutableLiveData<Boolean>? = null
    private var vehicleDeleted:MutableLiveData<Boolean>? = null
    private var message:MutableLiveData<String>? = null

    fun executeSaveVehicle() : LiveData<Boolean> {
        if (vehicleSaved == null){
            vehicleSaved = MutableLiveData()
            saveVehicle()
        }
        return vehicleSaved!!
    }

    fun executeDeleteVehicle() : LiveData<Boolean> {
        if(vehicleDeleted == null) {
            vehicleDeleted = MutableLiveData()
            deleteVehicle()
        }
        return vehicleDeleted!!
    }

    fun executeInfoMessage() : LiveData<String> {
        if (message == null){
            message = MutableLiveData()
        }
        return message!!
    }

    private  fun deleteVehicle() {
        viewModelScope.launch {
            try {
                vehicleService.deleteVehicle()
                vehicleDeleted?.value = true
            }catch (ex: Exception){
                ex.printStackTrace()
                vehicleDeleted?.value = false
            }
        }
    }

    private fun saveVehicle() {
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
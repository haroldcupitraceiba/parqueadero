package co.com.ceiba.parqueadero.viewmodel

import androidx.lifecycle.*
import co.com.ceiba.application.VehicleApplicationService
import co.com.ceiba.domain.model.Payment
import co.com.ceiba.domain.model.Vehicle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class VehicleViewModel: ViewModel() {

    private var vehicleSaved:LiveData<String>? = null
    private var vehicleDeleted:LiveData<String>? = null
    private var vehicleSearched:LiveData<Vehicle?>? = null
    private var message:MutableLiveData<String>? = null
    private lateinit var vehicleService: VehicleApplicationService

    fun setVehicleService(vehicleService: VehicleApplicationService){
        this.vehicleService = vehicleService
    }

    fun observeSaveVehicle() : LiveData<String> {
        if (vehicleSaved == null){
            vehicleSaved = MutableLiveData()
        }
        return vehicleSaved!!
    }

    fun observeDeleteVehicle() : LiveData<String> {
        if(vehicleDeleted == null) {
            vehicleDeleted = MutableLiveData()
        }
        return vehicleDeleted!!
    }

    fun observeSearchVehicle() : LiveData<Vehicle?>{
        if (vehicleSearched == null){
            vehicleSearched = MutableLiveData()
        }
        return vehicleSearched!!
    }

    fun observeInfoMessage() : LiveData<String> {
        if (message == null){
            message = MutableLiveData()
        }
        return message!!
    }

    fun executeDeleteVehicle() :LiveData<String> {
        return liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            try {
                vehicleService.deleteVehicle()
                emit("Vehículo eliminado.")
            }catch (ex: Exception){
                ex.printStackTrace()
                emit(ex.message!!)
            }
        }
    }

    fun executeSaveVehicle() :LiveData<String> {

        return liveData(Dispatchers.IO) {
            try {
                vehicleService.saveVehicle()
                emit("Vehículo registrado.")
            }catch (ex: Exception){
                ex.printStackTrace()
                emit(ex.message!!)
            }
        }
    }

    fun executeSearchVehicle() : LiveData<Payment?>{
        return liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            var payment: Payment?
            try {
                payment = vehicleService.getPaymentVehicle()
                emit(payment)

            }catch (ex: Exception){
                ex.printStackTrace()
                emit(null)
            }
        }
    }
}
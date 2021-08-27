package co.com.ceiba.parqueadero.viewmodel

import android.content.Context
import androidx.lifecycle.*
import co.com.ceiba.application.VehicleApplicationService
import co.com.ceiba.application.dto.VehiclePayment

import kotlinx.coroutines.*
import java.lang.Exception


class VehicleViewModel: ViewModel() {

    private var message:MutableLiveData<String>? = null
    private val vehicleService = VehicleApplicationService()

    fun setDataToCreateService(licensePlate: String, cylinderCapacity: Int, context: Context, typeVehicle: String): String?{
        return vehicleService.createService(licensePlate,cylinderCapacity, context, typeVehicle)
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

    fun executeSaveVehicle() : LiveData<String> {

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

    fun executeSearchVehicle() : LiveData<VehiclePayment?>{
        return liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            try {
                val vehiclePayment = vehicleService.getPaymentVehicle()

                emit(vehiclePayment)
            }catch (ex: Exception){
                ex.printStackTrace()
                emit(null)
            }
        }
    }
}
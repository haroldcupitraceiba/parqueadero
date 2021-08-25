package co.com.ceiba.parqueadero.viewmodel

import android.content.Context
import androidx.lifecycle.*
import co.com.ceiba.application.VehicleApplicationService
import co.com.ceiba.domain.model.Car
import co.com.ceiba.domain.model.Motorcycle
import co.com.ceiba.domain.model.Payment
import co.com.ceiba.parqueadero.MainActivity
import co.com.ceiba.parqueadero.factory.VehicleApplicationFactory

import kotlinx.coroutines.*
import java.lang.Exception
import java.util.*


class VehicleViewModel: ViewModel() {

    private var message:MutableLiveData<String>? = null
    private lateinit var vehicleService: VehicleApplicationService

    fun setVehicleService(vehicleService: VehicleApplicationService){
        this.vehicleService = vehicleService
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
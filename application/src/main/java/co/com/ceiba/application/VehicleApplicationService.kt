package co.com.ceiba.application

import android.content.Context
import co.com.ceiba.application.anticorruption.ApplicationTranslator
import co.com.ceiba.application.dto.VehiclePayment
import co.com.ceiba.dataaccess.repository.CarRepositoryImpl
import co.com.ceiba.dataaccess.repository.MotorcycleRepositoryImpl
import co.com.ceiba.dataaccess.repository.VehicleRepositoryImpl
import co.com.ceiba.domain.model.Car
import co.com.ceiba.domain.model.Motorcycle
import co.com.ceiba.domain.model.Payment
import co.com.ceiba.domain.model.Vehicle
import co.com.ceiba.domain.service.EntryCarService
import co.com.ceiba.domain.service.EntryMotorcycleService
import co.com.ceiba.domain.service.EntryService
import java.lang.Exception
import java.util.*

class VehicleApplicationService(){

    private lateinit var entryService: EntryService

    fun createService(
        licensePlate:String,
        cylinderCapacity: Int,
        context: Context,
        typeVehicle: String
    ): String?{

        var exceptionMessage: String? = null
        try{
            var vehicle:Vehicle
            val vehicleRepository = VehicleRepositoryImpl(context)
            when(typeVehicle){
                "CAR" -> {
                    vehicle = Car(licensePlate, Date())
                    val carRepositoryImpl = CarRepositoryImpl(context)
                    val carService = EntryCarService(carRepositoryImpl)
                    this.entryService = EntryService(vehicle,vehicleRepository, carService)
                }
                "MOTORCYCLE" -> {
                    vehicle = Motorcycle(licensePlate, Date(),cylinderCapacity)
                    val motorcycleRepositoryImpl = MotorcycleRepositoryImpl(context)
                    val motorcycleService = EntryMotorcycleService(motorcycleRepositoryImpl)
                    this.entryService = EntryService(vehicle, vehicleRepository, motorcycleService)
                }
            }
        }catch (exception: Exception){
            exception.printStackTrace()
            exceptionMessage = exception.message
        }
        return exceptionMessage
    }

    fun saveVehicle() {
        entryService.saveVehicle()
    }

    fun getPaymentVehicle(): VehiclePayment? {

        var vehiclePayment : VehiclePayment? = null
        val payment = entryService.getPayment()

        payment?.let {
            vehiclePayment = ApplicationTranslator().fromPaymentToVehiclePayment(it)
        }

        return vehiclePayment
    }

    fun deleteVehicle(){
        entryService.deleteVehicle()
    }
}
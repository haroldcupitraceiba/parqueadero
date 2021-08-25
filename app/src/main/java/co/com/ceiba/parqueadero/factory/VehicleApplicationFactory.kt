package co.com.ceiba.parqueadero.factory

import android.content.Context
import co.com.ceiba.application.VehicleApplicationService
import co.com.ceiba.dataaccess.repository.CarRepositoryImpl
import co.com.ceiba.dataaccess.repository.MotorcycleRepositoryImpl
import co.com.ceiba.dataaccess.repository.VehicleRepositoryImpl
import co.com.ceiba.domain.model.Car
import co.com.ceiba.domain.model.Motorcycle
import co.com.ceiba.domain.model.Vehicle
import co.com.ceiba.domain.service.EntryCarService
import co.com.ceiba.domain.service.EntryMotorcycleService
import co.com.ceiba.domain.service.EntryService
import co.com.ceiba.parqueadero.dto.VehicleApplication
import java.lang.Exception
import java.util.*

class VehicleApplicationFactory(
    private val licensePlate: String,
    private val cylinderCapacity: Int,
    private val context: Context,
    private val typeVehicle: TypeVehicle
) {

    companion object{
        enum class TypeVehicle { CAR, MOTORCYCLE}
    }

    fun getVehicle(): VehicleApplication? {
        var vehicle:Vehicle
        val vehicleRepositoryImpl = VehicleRepositoryImpl(context)
        var vehicleApplication: VehicleApplication
        try {
            when(typeVehicle){
                TypeVehicle.CAR -> {
                    vehicle = Car(licensePlate, Date())
                    val carRepository = CarRepositoryImpl(context)
                    val carService = EntryCarService(carRepository)
                    val entryService = EntryService(vehicle, vehicleRepositoryImpl, carService)
                    val vehicleApplicationService = VehicleApplicationService(entryService)
                    vehicleApplication =  VehicleApplication(vehicleApplicationService)
                }

                TypeVehicle.MOTORCYCLE -> {
                    vehicle = Motorcycle(licensePlate, Date(), cylinderCapacity)
                    val motorcycleRepository = MotorcycleRepositoryImpl(context)
                    val motorcycleService = EntryMotorcycleService(motorcycleRepository)
                    val entryService = EntryService(vehicle, vehicleRepositoryImpl, motorcycleService)
                    val vehicleApplicationService = VehicleApplicationService(entryService)
                    vehicleApplication = VehicleApplication(vehicleApplicationService)
                }
            }
        }catch (exception: Exception){
            exception.printStackTrace()
            vehicleApplication = VehicleApplication(exceptionMessage = exception.message)
        }

        return vehicleApplication
    }



}
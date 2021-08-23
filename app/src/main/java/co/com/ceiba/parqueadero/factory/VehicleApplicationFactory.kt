package co.com.ceiba.parqueadero.factory

import android.content.Context
import co.com.ceiba.application.VehicleApplicationService
import co.com.ceiba.dataaccess.repository.CarRepositoryImpl
import co.com.ceiba.dataaccess.repository.MotorcycleRepositoryImpl
import co.com.ceiba.dataaccess.repository.VehicleRepositoryImpl
import co.com.ceiba.domain.model.Motorcycle
import co.com.ceiba.domain.model.Vehicle
import co.com.ceiba.domain.service.EntryCarService
import co.com.ceiba.domain.service.EntryMotorcycleService
import co.com.ceiba.domain.service.EntryService

class VehicleApplicationFactory(
    private val vehicle: Vehicle,
    private val context: Context,
    private val typeVehicle: String
) {

    companion object{
        const val CAR_TYPE = "Car"
        const val MOTORCYCLE_TYPE = "Motorcycle"
    }

    fun getVehicle(): VehicleApplicationService? {
        val vehicleRepositoryImpl = VehicleRepositoryImpl(context)
        return when{
            isACar(typeVehicle) -> {
                val carRepository = CarRepositoryImpl(context)
                val carService = EntryCarService(carRepository)
                val entryService = EntryService(vehicle, vehicleRepositoryImpl, carService)
                return VehicleApplicationService(entryService)
            }

            isAMotorcycle(typeVehicle) -> {
                val motorcycleRepository = MotorcycleRepositoryImpl(context)
                val motorcycleService = EntryMotorcycleService(motorcycleRepository)
                val entryService = EntryService(vehicle, vehicleRepositoryImpl, motorcycleService)
                return VehicleApplicationService(entryService)
            }

            else -> null
        }
    }

    fun isACar(typeVehicle: String): Boolean{
        return typeVehicle.equals(CAR_TYPE)
    }

    fun isAMotorcycle(typeVehicle: String): Boolean{
        return typeVehicle.equals(MOTORCYCLE_TYPE)
    }


}
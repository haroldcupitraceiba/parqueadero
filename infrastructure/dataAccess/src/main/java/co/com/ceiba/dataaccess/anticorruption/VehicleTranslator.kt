package co.com.ceiba.dataaccess.anticorruption

import co.com.ceiba.dataaccess.database.entity.VehicleEntity
import co.com.ceiba.domain.model.Car
import co.com.ceiba.domain.model.Motorcycle
import co.com.ceiba.domain.model.Vehicle
import java.util.*

class VehicleTranslator {

    fun fromDomainToEntity(vehicle: Vehicle): VehicleEntity{
        return VehicleEntity(
            licencePlate = vehicle.licensePlate,
            type = vehicle.type,
            entryDate = vehicle.entryDate.time,
            cylinderCapacity = vehicle.cylinderCapacity
        )
    }

    fun fromEntityToDomain(vehicleEntity: VehicleEntity): Vehicle?{
        return when(vehicleEntity.type){
            Car::class.java.name -> {
                Car(
                    vehicleEntity.licencePlate,
                    Date(vehicleEntity.entryDate),
                )
            }
            Motorcycle::class.java.name -> {
                Motorcycle(
                    vehicleEntity.licencePlate,
                    Date(vehicleEntity.entryDate),
                    vehicleEntity.cylinderCapacity!!
                )
            }
            else -> null
        }
    }

}
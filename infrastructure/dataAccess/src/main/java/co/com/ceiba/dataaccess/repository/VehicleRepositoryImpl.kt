package co.com.ceiba.dataaccess.repository

import android.content.Context
import co.com.ceiba.dataaccess.anticorruption.VehicleTranslator
import co.com.ceiba.dataaccess.database.VehicleDatabase
import co.com.ceiba.domain.model.Vehicle
import co.com.ceiba.domain.repository.VehicleRepository


class VehicleRepositoryImpl(context: Context): VehicleRepository {

    private var vehicleDatabase:VehicleDatabase = VehicleDatabase.getDatabase(context)

    override fun vehicleExists(licensePlate: String): Vehicle? {
        val vehicleEntity = vehicleDatabase.vehicleDao().vehicleExists(licensePlate)
        var vehicle: Vehicle? = null

        vehicleEntity?.let {
            vehicle = VehicleTranslator().fromEntityToDomain(vehicleEntity)
        }

        return vehicle
    }

    override fun saveVehicle(vehicle: Vehicle) {
        vehicleDatabase.vehicleDao().saveVehicle(
            VehicleTranslator().fromDomainToEntity(vehicle)
        )
    }

    override fun deleteVehicle(licensePlate: String) {
        vehicleDatabase.vehicleDao().deleteVehicle(licensePlate)
    }

}
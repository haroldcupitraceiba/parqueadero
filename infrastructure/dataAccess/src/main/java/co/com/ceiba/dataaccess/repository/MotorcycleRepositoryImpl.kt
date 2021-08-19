package co.com.ceiba.dataaccess.repository

import android.content.Context
import co.com.ceiba.dataaccess.database.VehicleDatabase
import co.com.ceiba.domain.model.Motorcycle
import co.com.ceiba.domain.repository.MotorcycleRepository

class MotorcycleRepositoryImpl(context: Context): MotorcycleRepository {

    private var vehicleDatabase: VehicleDatabase = VehicleDatabase.getDatabase(context)

    override suspend fun getAmount(): Int {
        return vehicleDatabase.vehicleDao().getVehicleAmountByType(Motorcycle::class.java.name)
    }

}
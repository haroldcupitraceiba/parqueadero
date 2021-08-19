package co.com.ceiba.dataaccess.repository

import android.content.Context
import co.com.ceiba.dataaccess.database.VehicleDatabase
import co.com.ceiba.domain.model.Car
import co.com.ceiba.domain.repository.CarRepository

class CarRepositoryImpl(context: Context) :CarRepository {

    private val vehicleDatabase = VehicleDatabase.getDatabase(context)

    override suspend fun getAmount(): Int {
        return vehicleDatabase.vehicleDao().getVehicleAmountByType(Car::class.java.name)
    }
}
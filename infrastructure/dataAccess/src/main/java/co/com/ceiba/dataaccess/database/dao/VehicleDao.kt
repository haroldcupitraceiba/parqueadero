package co.com.ceiba.dataaccess.database.dao

import androidx.room.Insert
import androidx.room.Query
import co.com.ceiba.dataaccess.database.entity.VehicleEntity

interface VehicleDao {
    @Query("SELECT * FROM vehicles WHERE licencePlate LIKE :licensePlate LIMIT 1")
    fun vehicleExists(licensePlate:String) : VehicleEntity

    @Insert
    fun saveVehicle(vehicle: VehicleEntity)

    @Query("DELETE FROM vehicles WHERE licencePlate LIKE :licensePlate")
    fun deleteVehicle(licensePlate: String)
}
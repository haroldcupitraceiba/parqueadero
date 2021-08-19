package co.com.ceiba.dataaccess.database.dao

import androidx.annotation.NonNull
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import co.com.ceiba.dataaccess.database.entity.VehicleEntity

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicles WHERE licencePlate LIKE :licensePlate LIMIT 1")
    fun vehicleExists(licensePlate:String) : VehicleEntity

    @Insert
    fun saveVehicle(@NonNull vehicle: VehicleEntity)

    @Query("DELETE FROM vehicles WHERE licencePlate LIKE :licensePlate")
    fun deleteVehicle(@NonNull licensePlate: String)

    @Query("SELECT COUNT(*) FROM vehicles WHERE type LIKE :type")
    fun getVehicleAmountByType(@NonNull type: String) : Int
}
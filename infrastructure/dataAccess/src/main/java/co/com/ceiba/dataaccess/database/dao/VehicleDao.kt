package co.com.ceiba.dataaccess.database.dao

import androidx.annotation.NonNull
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import co.com.ceiba.dataaccess.database.entity.VehicleEntity

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicles WHERE licencePlate LIKE :licensePlate LIMIT 1")
    suspend fun vehicleExists(licensePlate:String) : VehicleEntity

    @Insert
    suspend fun saveVehicle(@NonNull vehicle: VehicleEntity)

    @Query("DELETE FROM vehicles WHERE licencePlate LIKE :licensePlate")
    suspend fun deleteVehicle(@NonNull licensePlate: String)

    @Query("SELECT COUNT(*) FROM vehicles WHERE type LIKE :type")
    suspend fun getVehicleAmountByType(@NonNull type: String) : Int
}
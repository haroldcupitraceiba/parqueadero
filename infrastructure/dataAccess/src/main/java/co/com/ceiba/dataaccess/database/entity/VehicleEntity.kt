package co.com.ceiba.dataaccess.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = VehicleEntity.TABLE_NAME)
data class VehicleEntity(
    @PrimaryKey val licencePlate:String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "entry_date") val entryDate: Long,
    @ColumnInfo(name = "cylinder_capacity") var cylinderCapacity: Int?

){
    companion object{
        const val TABLE_NAME = "vehicles"
    }
}
package co.com.ceiba.dataaccess.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.com.ceiba.dataaccess.database.dao.VehicleDao
import co.com.ceiba.dataaccess.database.entity.VehicleEntity

@Database(entities = [VehicleEntity::class], version = 1)
abstract class  VehicleDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao

    companion object{
        private var INSTANCE: VehicleDatabase? = null

        fun getDatabase(context: Context): VehicleDatabase{
            if (INSTANCE == null) {
                //Let's build our RoomDatabase using builder pattern
                INSTANCE = Room.databaseBuilder(
                    context, VehicleDatabase::class.java,
                    "VehiclesDatabase"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as VehicleDatabase
        }
    }
}
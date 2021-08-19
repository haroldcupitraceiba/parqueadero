package co.com.ceiba.dataaccess.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.com.ceiba.dataaccess.database.dao.VehicleDao
import co.com.ceiba.dataaccess.database.entity.VehicleEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@Database(entities = [VehicleEntity::class], version = 1)
abstract class  VehicleDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao

    companion object{
        @Volatile
        private var INSTANCE: VehicleDatabase? = null

        @Inject
        fun getDatabase(@ApplicationContext context: Context): VehicleDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VehicleDatabase::class.java,
                    "vehicle_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}
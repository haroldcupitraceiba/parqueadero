package co.com.ceiba.parqueadero.dimodule

import co.com.ceiba.dataaccess.repository.VehicleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import co.com.ceiba.domain.repository.VehicleRepository

@Module
@InstallIn(ActivityComponent::class)
abstract class VehicleModule {
    @Binds
    abstract fun bindVehicleRepository( vehicleRepository: VehicleRepositoryImpl): VehicleRepository
}
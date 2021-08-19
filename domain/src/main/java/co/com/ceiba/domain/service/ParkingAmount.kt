package co.com.ceiba.domain.service

interface ParkingAmount {
    suspend fun hasReachedVehicleLimit(): Boolean
}

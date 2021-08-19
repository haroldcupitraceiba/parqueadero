package co.com.ceiba.domain.service

import co.com.ceiba.domain.model.Motorcycle
import co.com.ceiba.domain.repository.MotorcycleRepository

class EntryMotorcycleService(
    private var motorcycleRepository: MotorcycleRepository
) : ParkingAmount {
    private val maximumQuantityAvailable = 10

    override suspend fun hasReachedVehicleLimit(): Boolean {
        return (motorcycleRepository.getAmount() < maximumQuantityAvailable)
    }
}
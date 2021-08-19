package co.com.ceiba.domain.service

import co.com.ceiba.domain.model.Car
import co.com.ceiba.domain.repository.CarRepository


class EntryCarService (
    private var carRepository: CarRepository
) : ParkingAmount {
    private val maximumQuantityAvailable = 20

    override suspend fun hasReachedVehicleLimit(): Boolean {
        return (carRepository.getAmount() < maximumQuantityAvailable)
    }
}
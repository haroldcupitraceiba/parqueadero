package co.com.ceiba.domain.repository

interface MotorcycleRepository{
    suspend fun getAmount(): Int
}
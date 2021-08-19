package co.com.ceiba.domain.repository

interface CarRepository {
    suspend fun getAmount(): Int
}
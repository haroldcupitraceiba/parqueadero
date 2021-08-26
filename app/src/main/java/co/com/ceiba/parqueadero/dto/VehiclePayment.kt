package co.com.ceiba.parqueadero.dto

import co.com.ceiba.parqueadero.factory.VehicleApplicationFactory

class VehiclePayment(
    val licensePlate: String,
    val cylinderCapacity: Int,
    val paymentValue: Long
)
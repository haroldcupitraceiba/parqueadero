package co.com.ceiba.parqueadero.anticorruption

import co.com.ceiba.domain.model.Payment
import co.com.ceiba.parqueadero.dto.VehiclePayment

class VehicleTranslator {

    fun fromPaymentToVehiclePayment(payment: Payment): VehiclePayment{
        return VehiclePayment(
            licensePlate = payment.vehicle.licensePlate,
            cylinderCapacity = payment.vehicle.cylinderCapacity,
            paymentValue = payment.calculatePayment()
        )
    }

}
package co.com.ceiba.application.anticorruption

import co.com.ceiba.application.dto.VehiclePayment
import co.com.ceiba.domain.model.Payment

class ApplicationTranslator {

    fun fromPaymentToVehiclePayment(payment: Payment?): VehiclePayment?{
        var vehiclePayment: VehiclePayment? = null

        payment?.let {
            vehiclePayment = VehiclePayment(
                licensePlate = payment.getVehicle().licensePlate,
                cylinderCapacity = payment.getVehicle().cylinderCapacity,
                paymentValue = payment.calculatePayment()
            )
        }

        return vehiclePayment
    }

}
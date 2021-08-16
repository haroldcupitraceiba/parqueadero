package co.com.ceiba.domain.entity

import co.com.ceiba.domain.exception.InvalidCylinderCapacityValueException
import java.util.*

class Motorcycle(
    override val licensePlate: String,
    override val entryDate: Date,
    override var exitDate: Date?,
    val cylinderCapacity: Int
) : Vehicle(licensePlate,entryDate,exitDate) {

    private val REGULAR_EXPRESSION_TO_VALID_LICENSE_PLATE_FORMAT = "[A-Z]{3}[0-9]{2}[A-Z]{1}"

    companion object{
        const val LICENSE_PLATE_LENGTH = 6
    }

    init {
        validateFormatLicensePlate(LICENSE_PLATE_LENGTH,REGULAR_EXPRESSION_TO_VALID_LICENSE_PLATE_FORMAT)
        validateCylinderCapacity()
    }

    private fun validateCylinderCapacity() {
        if (cylinderCapacity <= 0)
            throw InvalidCylinderCapacityValueException()
    }

    override fun calculatePayment(): Int {
        return 0
    }
}

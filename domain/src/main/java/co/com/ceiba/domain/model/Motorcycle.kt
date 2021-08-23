package co.com.ceiba.domain.model

import co.com.ceiba.domain.exception.InvalidCylinderCapacityValueException
import java.util.*

class Motorcycle(
    override val licensePlate: String,
    override val entryDate: Date,
    override val cylinderCapacity: Int
) : Vehicle(licensePlate, Motorcycle::class.java.name,entryDate,cylinderCapacity) {

    private val REGULAR_EXPRESSION_TO_VALID_LICENSE_PLATE_FORMAT = "[A-Z]{3}[0-9]{2}[A-Z]{1}"


    companion object{
        const val LICENSE_PLATE_LENGTH = 6
    }

    init {
        validateNotNullValues()
        validateFormatLicensePlate(LICENSE_PLATE_LENGTH,REGULAR_EXPRESSION_TO_VALID_LICENSE_PLATE_FORMAT)
        validateCylinderCapacity()
    }

    private fun validateCylinderCapacity() {
        if (cylinderCapacity <= 0)
            throw InvalidCylinderCapacityValueException()
    }

    override val parkingDayValue: Int
        get() = 4000
    override val parkingHourValue: Int
        get() = 500
    override val maximumCylinderCapacityToNotPayExtraValue: Int
        get() = 500
    override val payExtraValue: Int
        get() = 2000

    override fun hasToValidCylinderCapacityInPayment(): Boolean {
        return true
    }
}

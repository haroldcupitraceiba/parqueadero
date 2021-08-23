package co.com.ceiba.domain.model

import java.util.*

class Car(
    override val licensePlate: String,
    override val entryDate: Date,
) : Vehicle(licensePlate, Car::class.java.name,entryDate) {

    private val REGULAR_EXPRESSION_TO_VALID_LICENSE_PLATE_FORMAT = "[A-Z]{3}[0-9]{3}"

    companion object{
        const val LICENSE_PLATE_LENGTH = 6
    }

    init {
        validateNotNullValues()
        validateFormatLicensePlate(LICENSE_PLATE_LENGTH,REGULAR_EXPRESSION_TO_VALID_LICENSE_PLATE_FORMAT)
    }

    override val parkingDayValue: Int
        get() = 8000
    override val parkingHourValue: Int
        get() = 1000
    override val maximumCylinderCapacityToNotPayExtraValue: Int
        get() = 0
    override val payExtraValue: Int
        get() = 0

    override fun hasToValidCylinderCapacityInPayment(): Boolean {
        return false
    }
}
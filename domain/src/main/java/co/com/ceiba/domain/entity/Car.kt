package co.com.ceiba.domain.entity

import java.util.*

class Car(
    override val licensePlate: String,
    override val entryDate: Date,
    override var exitDate: Date?
) : Vehicle(licensePlate,entryDate,exitDate) {

    private val REGULAR_EXPRESSION_TO_VALID_LICENSE_PLATE_FORMAT = "[A-Z]{3}[0-9]{3}"

    companion object{
        const val LICENSE_PLATE_LENGTH = 6
    }

    init {
        validateFormatLicensePlate(LICENSE_PLATE_LENGTH,REGULAR_EXPRESSION_TO_VALID_LICENSE_PLATE_FORMAT)
    }

    override fun calculatePayment(): Int {
        return 0
    }
}
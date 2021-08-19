package co.com.ceiba.domain.exception

class InvalidEntryDateException : RuntimeException(INVALID_ENTRY_DATE_MESSAGE) {
    companion object{
        const val INVALID_ENTRY_DATE_MESSAGE = "Fecha de ingreso del vehículo no permitida."
    }
}
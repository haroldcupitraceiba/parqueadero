package co.com.ceiba.domain.exception

class ParkingValueNotValidException : RuntimeException(PARKING_VALUE_NOT_VALID_MESSAGE) {
    companion object{
        const val PARKING_VALUE_NOT_VALID_MESSAGE = "Valor no valido para realizar pago."
    }
}
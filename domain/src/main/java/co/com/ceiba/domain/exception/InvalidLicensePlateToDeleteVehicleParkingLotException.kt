package co.com.ceiba.domain.exception

class InvalidLicensePlateToDeleteVehicleParkingLotException : RuntimeException(INVALID_LICENSE_PLATE_MESSAGE) {
    companion object{
        const val INVALID_LICENSE_PLATE_MESSAGE = "El vehículo a agregar es nulo."
    }
}
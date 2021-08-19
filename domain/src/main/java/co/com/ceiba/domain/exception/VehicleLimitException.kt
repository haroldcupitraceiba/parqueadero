package co.com.ceiba.domain.exception

class VehicleLimitException : RuntimeException(VEHICLE_LIMIT_MESSAGE) {
    companion object{
        const val VEHICLE_LIMIT_MESSAGE = "Cantidad maxima permitida de vehículos."
    }
}
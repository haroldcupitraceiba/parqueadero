package co.com.ceiba.domain.exception

class InvalidVehicleToAddParkingLotException : RuntimeException(INVALID_VEHICLE_TO_ADD_PARKING_LOT_MESSAGE) {
    companion object{
        const val INVALID_VEHICLE_TO_ADD_PARKING_LOT_MESSAGE = "El vehículo ya se encuentra registrado."
    }
}

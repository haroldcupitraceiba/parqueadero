package co.com.ceiba.domain.exception

import java.lang.RuntimeException

class InvalidCylinderCapacityValueException : RuntimeException(INVALID_CYLINDER_CAPACITY_MESSAGE) {
    companion object{
        const val INVALID_CYLINDER_CAPACITY_MESSAGE = "El cilindraje debe ser mayor a 0."
    }
}
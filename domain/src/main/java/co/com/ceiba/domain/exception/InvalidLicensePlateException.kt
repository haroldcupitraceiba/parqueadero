package co.com.ceiba.domain.exception

import java.lang.RuntimeException

class InvalidLicensePlateException : RuntimeException(INVALID_LICENSE_PLATE_MESSAGE) {
    companion object{
        const val INVALID_LICENSE_PLATE_MESSAGE = "Placa del vehículo no valida."
    }


}
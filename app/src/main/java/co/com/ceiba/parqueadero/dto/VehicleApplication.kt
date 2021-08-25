package co.com.ceiba.parqueadero.dto

import co.com.ceiba.application.VehicleApplicationService
import java.lang.Exception

class VehicleApplication(
    val service : VehicleApplicationService? = null,
    val exceptionMessage : String? = null
)
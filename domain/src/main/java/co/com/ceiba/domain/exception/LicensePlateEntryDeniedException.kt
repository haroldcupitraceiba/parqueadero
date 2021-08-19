package co.com.ceiba.domain.exception

class LicensePlateEntryDeniedException : RuntimeException(LICENSE_PLATE_ENTRY_DENIED_MESSAGE) {
    companion object{
        const val LICENSE_PLATE_ENTRY_DENIED_MESSAGE = "Ingreso no permitido al vehículo."
    }
}
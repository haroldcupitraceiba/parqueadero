package co.com.ceiba.domain.model

import co.com.ceiba.domain.exception.InvalidLicensePlateException
import java.lang.NullPointerException
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

open abstract class Vehicle(
    open val licensePlate: String,
    open val type: String,
    open val entryDate: Date,
    open val cylinderCapacity: Int? = null
){
    abstract val parkingDayValue: Int
    abstract val parkingHourValue: Int

    protected fun validateNotNullValues(){
        if (licensePlate.isNullOrEmpty()){
            throw InvalidLicensePlateException()
        }

        if (entryDate == null){
            throw NullPointerException()
        }
    }

    protected fun validateFormatLicensePlate(licensePlateLengthToValid: Int, regularExpressionToValid: String){
        if (licensePlate.length != licensePlateLengthToValid || !isLicensePlateFormatValid(regularExpressionToValid))
            throw InvalidLicensePlateException()
    }

    private fun isLicensePlateFormatValid(regularExpressionToValid: String):Boolean{
        val pattern: Pattern = Pattern.compile(regularExpressionToValid)
        val matcher: Matcher = pattern.matcher(licensePlate)
        return matcher.matches()
    }

    abstract fun calculatePayment(): Long
}
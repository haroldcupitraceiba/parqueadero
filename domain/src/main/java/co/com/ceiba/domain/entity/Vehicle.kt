package co.com.ceiba.domain.entity

import co.com.ceiba.domain.exception.InvalidLicensePlateException
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

open abstract class Vehicle(
    open val licensePlate: String,
    open val entryDate: Date,
    open var exitDate: Date?
){
    protected fun validateFormatLicensePlate(licensePlateLengthToValid: Int, regularExpressionToValid: String){
        if (licensePlate.length != licensePlateLengthToValid || !isLicensePlateFormatValid(regularExpressionToValid))
            throw InvalidLicensePlateException()
    }

    private fun isLicensePlateFormatValid(regularExpressionToValid: String):Boolean{
        val pattern: Pattern = Pattern.compile(regularExpressionToValid)
        val matcher: Matcher = pattern.matcher(licensePlate)
        return matcher.matches()
    }

    abstract fun calculatePayment(): Int
}
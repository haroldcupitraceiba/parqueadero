package co.com.ceiba.domain.model

import co.com.ceiba.domain.exception.InvalidCylinderCapacityValueException
import co.com.ceiba.domain.exception.InvalidLicensePlateException
import org.junit.Assert
import org.junit.Test
import java.util.*

class MotorcycleTest {

    @Test
    fun motorcycle_createMotorcycleWithCorrectParams_successful(){
        //Arrange
        val entryDate = Date()
        val licensePlate = "IWB24E"
        val cylinderCapacity = 150
        //Act
        val motorcycle = Motorcycle(licensePlate,entryDate,cylinderCapacity)
        //Assert
        Assert.assertNotNull(motorcycle)
    }

    @Test
    fun motorcycle_createMotorcycleWithZeroCylinderCapacity_exception(){
        //Arrange
        val entryDate = Date()
        val licensePlate = "IWB24E"
        val cylinderCapacity = 0
        val expectedMessage = "El cilindraje debe ser mayor a 0."
        //Act
        try {
            val motorcycle = Motorcycle(licensePlate,entryDate,cylinderCapacity)
            Assert.fail()
        }catch (ex: InvalidCylinderCapacityValueException){
            //Assert
            Assert.assertEquals(expectedMessage,ex.message)
        }
    }


    @Test
    fun motorcycle_createMotorcycleWithIncorrectLicensePlateFormat_exception(){
        //Arrange
        val entryDate = Date()
        val licensePlate = "AZZ39"
        val expectedMessage = "Placa del vehículo no valida."
        val cylinderCapacity = 150
        //Act
        try {
            val motorcycle = Motorcycle(licensePlate,entryDate, cylinderCapacity)
            Assert.fail()
        }catch (ex: InvalidLicensePlateException){
            //Assert
            Assert.assertEquals(expectedMessage,ex.message)
        }
    }

    @Test
    fun motorcycle_createMotorcycleWithIncorrectLengthPlateFormat_exception(){
        //Arrange
        val entryDate = Date()
        val licensePlate = "AZZ"
        val cylinderCapacity = 150
        val expectedMessage = "Placa del vehículo no valida."
        //Act
        try {
            val motorcycle = Motorcycle(licensePlate,entryDate, cylinderCapacity)
            Assert.fail()
        }catch (ex: InvalidLicensePlateException){
            //Assert
            Assert.assertEquals(expectedMessage,ex.message)
        }
    }

    @Test
    fun motorcycle_createMotorcycleWithEmptyLicensePlateValue_exception(){
        //Arrange
        val entryDate = Date()
        val licensePlate = ""
        val cylinderCapacity = 150
        val expectedMessage = "Placa del vehículo no valida."
        //Act
        try {
            val motorcycle = Motorcycle(licensePlate,entryDate, cylinderCapacity)
            Assert.fail()
        }catch (ex: InvalidLicensePlateException){
            //Assert
            Assert.assertEquals(expectedMessage,ex.message)
        }
    }

    @Test
    fun motorcycle_createMotorcycleAndCalculatePaymentAfter9Hours_successful(){
        //Arrange
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.HOUR,-9)

        val entryDate = calendar.time
        val licensePlate = "AZZ03B"
        val cylinderCapacity = 150
        val motorcycle = Motorcycle(licensePlate, entryDate, cylinderCapacity)

        val paymentValue = motorcycle.calculatePayment()

        Assert.assertEquals(4000L,paymentValue)
    }

    @Test
    fun motorcycle_createMotorcycleAndCalculatePaymentAfter57Hours_successful(){
        //Arrange
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.HOUR,-57)

        val entryDate = calendar.time
        val licensePlate = "AZZ03B"
        val cylinderCapacity = 150
        val motorcycle = Motorcycle(licensePlate, entryDate, cylinderCapacity)

        val paymentValue = motorcycle.calculatePayment()

        Assert.assertEquals(12000L,paymentValue)
    }

    @Test
    fun motorcycle_createMotorcycleAndCalculatePaymentAfter7Hours_successful(){
        //Arrange
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.HOUR,-7)

        val entryDate = calendar.time
        val licensePlate = "AZZ03B"
        val cylinderCapacity = 150
        val motorcycle = Motorcycle(licensePlate, entryDate, cylinderCapacity)

        val paymentValue = motorcycle.calculatePayment()

        Assert.assertEquals(3500L,paymentValue)
    }

    @Test
    fun motorcycle_createMotorcycleAndCalculatePaymentWithExtraValueAfter9Hours_successful(){
        //Arrange
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.HOUR,-9)

        val entryDate = calendar.time
        val licensePlate = "AZZ03B"
        val cylinderCapacity = 600
        val motorcycle = Motorcycle(licensePlate, entryDate, cylinderCapacity)

        val paymentValue = motorcycle.calculatePayment()

        Assert.assertEquals(6000L,paymentValue)
    }

    @Test
    fun motorcycle_createMotorcycleAndCalculatePaymentWithExtraValueAfter57Hours_successful(){
        //Arrange
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.HOUR,-57)

        val entryDate = calendar.time
        val licensePlate = "AZZ03B"
        val cylinderCapacity = 600
        val motorcycle = Motorcycle(licensePlate, entryDate, cylinderCapacity)

        val paymentValue = motorcycle.calculatePayment()

        Assert.assertEquals(14000L,paymentValue)
    }

    @Test
    fun motorcycle_createMotorcycleAndCalculatePaymentWithExtraValueAfter7Hours_successful(){
        //Arrange
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.HOUR,-7)

        val entryDate = calendar.time
        val licensePlate = "AZZ03B"
        val cylinderCapacity = 600
        val motorcycle = Motorcycle(licensePlate, entryDate, cylinderCapacity)

        val paymentValue = motorcycle.calculatePayment()

        Assert.assertEquals(5500L,paymentValue)
    }
}
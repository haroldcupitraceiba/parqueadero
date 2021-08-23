package co.com.ceiba.domain.model

import co.com.ceiba.domain.exception.InvalidLicensePlateException
import org.junit.Assert
import org.junit.Test
import java.util.*

class CarTest {

    @Test
    fun car_createCarWithCorrectParams_successful(){
        //Arrange
        val entryDate = Date()
        val licensePlate = "AZZ039"
        //Act
        val car = Car(licensePlate,entryDate)
        //Assert
        Assert.assertNotNull(car)
    }


    @Test
    fun car_createCarWithIncorrectLicensePlateFormat_exception(){
        //Arrange
        val entryDate = Date()
        val licensePlate = "AZz039"
        val expectedMessage = "Placa del vehículo no valida."
        //Act
        try {
            val car = Car(licensePlate,entryDate)
            Assert.fail()
        }catch (ex: InvalidLicensePlateException){
            //Assert
            Assert.assertEquals(expectedMessage,ex.message)
        }
    }

    @Test
    fun car_createCarWithIncorrectLengthPlateFormat_exception(){
        //Arrange
        val entryDate = Date()
        val licensePlate = "AZZ"
        val expectedMessage = "Placa del vehículo no valida."
        //Act
        try {
            val car = Car(licensePlate,entryDate)
            Assert.fail()
        }catch (ex: InvalidLicensePlateException){
            //Assert
            Assert.assertEquals(expectedMessage,ex.message)
        }
    }

    @Test
    fun car_createCarWithEmptyLicensePlateValue_exception(){
        //Arrange
        val entryDate = Date()
        val licensePlate = ""
        val expectedMessage = "Placa del vehículo no valida."
        //Act
        try {
            val car = Car(licensePlate!!,entryDate)
            Assert.fail()
        }catch (ex: InvalidLicensePlateException){
            //Assert
            Assert.assertEquals(expectedMessage,ex.message)
        }
    }

    @Test
    fun car_createCarAndCalculatePaymentAfter9Hours_successful(){
        //Arrange
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.HOUR,-9)

        val entryDate = calendar.time
        val licensePlate = "AZZ039"
        val car = Car(licensePlate, entryDate)

        val paymentValue = Payment(car).calculatePayment()

        Assert.assertEquals(8000L,paymentValue)
    }

    @Test
    fun car_createCarAndCalculatePaymentAfter57Hours_successful(){
        //Arrange
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.HOUR,-57)

        val entryDate = calendar.time
        val licensePlate = "AZZ039"
        val car = Car(licensePlate, entryDate)

        val paymentValue = Payment(car).calculatePayment()

        Assert.assertEquals(24000L,paymentValue)
    }

    @Test
    fun car_createCarAndCalculatePaymentAfter7Hours_successful(){
        //Arrange
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.HOUR,-7)

        val entryDate = calendar.time
        val licensePlate = "AZZ039"
        val car = Car(licensePlate, entryDate)

        val paymentValue = Payment(car).calculatePayment()

        Assert.assertEquals(7000L,paymentValue)
    }
}
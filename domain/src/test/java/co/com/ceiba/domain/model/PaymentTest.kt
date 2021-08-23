package co.com.ceiba.domain.model

import co.com.ceiba.domain.exception.ParkingValueNotValidException
import org.junit.Assert
import org.junit.Test
import java.util.*

class PaymentTest {
    @Test
    fun payment_createPaymentWithCorrectParams_successful(){
        //Arrange
        val entryDate = Date()
        val licensePlate = "AZZ039"
        val car = Car(licensePlate, entryDate)
        //Act
        val payment = Payment(car)
        //Assert
        Assert.assertNotNull(payment)
    }


    @Test
    fun payment_calculatePaymentAfter7Hours_successful(){
        //Arrange
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.HOUR,-7)

        val entryDate = calendar.time
        val licensePlate = "AZZ039"
        val car = Car(licensePlate, entryDate)
        val payment = Payment(car)
        //Act

        val valueToPay = payment.calculatePayment()
        //Assert
        Assert.assertEquals(7000L,valueToPay)
    }

    @Test
    fun payment_calculatePaymentAfter9Hours_successful(){
        //Arrange
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.HOUR,-9)

        val entryDate = calendar.time
        val licensePlate = "AZZ039"
        val car = Car(licensePlate, entryDate)
        val payment = Payment(car)
        //Act
        val valueToPay = payment.calculatePayment()
        //Assert
        Assert.assertEquals(8000L,valueToPay)
    }

    @Test
    fun payment_calculatePaymentAfter57Hours_successful(){
        //Arrange
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.HOUR,-57)

        val entryDate = calendar.time
        val licensePlate = "AZZ039"
        val car = Car(licensePlate, entryDate)
        val payment = Payment(car)
        //Act
        val valueToPay = payment.calculatePayment()
        //Assert
        Assert.assertEquals(24000L,valueToPay)
    }
}
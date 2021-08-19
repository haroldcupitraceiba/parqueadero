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
        //Act
        val payment = Payment(entryDate, 1000,8000)
        //Assert
        Assert.assertNotNull(payment)
    }

    @Test
    fun payment_createPaymentWithZeroParkingValues_exception(){
        //Arrange
        val entryDate = Date()
        val expectedMessage = "Valor no valido para realizar pago."
        try {
            //Act
            val payment = Payment(entryDate, 1000,0)
            Assert.fail()
        }catch (ex: ParkingValueNotValidException){
            //Assert
            Assert.assertEquals(expectedMessage, ex.message)
        }
    }

    @Test
    fun payment_calculatePaymentAfter7Hours_successful(){
        //Arrange
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.HOUR,-7)

        val entryDate = calendar.time
        //Act
        val payment = Payment(entryDate, 1000,8000)
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
        //Act
        val payment = Payment(entryDate, 1000,8000)
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
        //Act
        val payment = Payment(entryDate, 1000,8000)
        val valueToPay = payment.calculatePayment()
        //Assert
        Assert.assertEquals(24000L,valueToPay)
    }
}
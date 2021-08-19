package co.com.ceiba.domain.service

import co.com.ceiba.domain.exception.InvalidVehicleToAddParkingLotException
import co.com.ceiba.domain.exception.LicensePlateEntryDeniedException
import co.com.ceiba.domain.exception.VehicleLimitException
import co.com.ceiba.domain.model.Car
import co.com.ceiba.domain.repository.VehicleRepository
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class EntryServiceTest {
    @Mock
    lateinit var vehicleRepository: VehicleRepository
    @Mock
    lateinit var parkingAmount: ParkingAmount

    @Test
    fun entryService_createEntryServiceWithCorrectParameters_successful(){
        //Arrange
        val entryDate = Date()
        val licensePlate = "AZZ039"
        val car = Car(licensePlate,entryDate)
        //Act
        val  entryService = EntryService(car,vehicleRepository,parkingAmount)
        //Assert
        Assert.assertNotNull(entryService)
    }

    @Test
    fun entryService_saveVehicleWithCorrectParameters_successful(){
        //Arrange
        val entryDate = Date()
        val licensePlate = "BZZ039"
        val car = Car(licensePlate,entryDate)
        val  entryService = EntryService(car,vehicleRepository,parkingAmount)

        Mockito.`when`(parkingAmount.hasReachedVehicleLimit()).thenReturn(true)
        Mockito.`when`(vehicleRepository.vehicleExists(car.licensePlate)).thenReturn(null)
        //Act
        entryService.saveVehicle()
    }

    @Test
    fun entryService_saveVehicleWithReachedVehicleLimit_exception(){
        //Arrange
        val entryDate = Date()
        val licensePlate = "AZZ039"
        val car = Car(licensePlate,entryDate)
        val entryService = EntryService(car,vehicleRepository,parkingAmount)
        val expectedMessage = "Cantidad maxima permitida de vehículos."

        Mockito.`when`(parkingAmount.hasReachedVehicleLimit()).thenReturn(false)
        //Act
        try{
            entryService.saveVehicle()
            Assert.fail()
        }catch (ex: VehicleLimitException){
            //Assert
            Assert.assertEquals(expectedMessage, ex.message)
        }
    }

    @Test
     fun entryService_saveVehicleExists_exception(){
        //Arrange
        val entryDate = Date()
        val licensePlate = "BZZ039"
        val car = Car(licensePlate,entryDate)
        val entryService = EntryService(car,vehicleRepository,parkingAmount)
        val expectedMessage = "El vehículo ya se encuentra registrado."

        Mockito.`when`(parkingAmount.hasReachedVehicleLimit()).thenReturn(true)
        Mockito.`when`(vehicleRepository.vehicleExists(car.licensePlate)).thenReturn(car)
        //Act
        try{
            entryService.saveVehicle()
            Assert.fail()
        }catch (ex: InvalidVehicleToAddParkingLotException){
            //Assert
            Assert.assertEquals(expectedMessage, ex.message)
        }
    }

    @Test
     fun entryService_saveVehicleOnInvalidDate_exception(){
        //Arrange
        val entryDate = Date(1629326865000) //Wednesday
        val licensePlate = "AZZ039"
        val car = Car(licensePlate,entryDate)
        val entryService = EntryService(car,vehicleRepository,parkingAmount)
        val expectedMessage = "Ingreso no permitido al vehículo."

        Mockito.`when`(parkingAmount.hasReachedVehicleLimit()).thenReturn(true)
        //Act
        try{
            entryService.saveVehicle()
            Assert.fail()
        }catch (ex: LicensePlateEntryDeniedException){
            Assert.assertEquals(expectedMessage, ex.message)
        }
    }

    @Test
     fun entryService_saveVehicleOnSundayDate_successful(){
        //Arrange
        val entryDate = Date(1629067665000) //Sunday
        val licensePlate = "AZZ039"
        val car = Car(licensePlate,entryDate)
        val entryService = EntryService(car,vehicleRepository,parkingAmount)

        Mockito.`when`(parkingAmount.hasReachedVehicleLimit()).thenReturn(true)
        Mockito.`when`(vehicleRepository.vehicleExists(car.licensePlate)).thenReturn(null)

        //Act
        entryService.saveVehicle()
    }

    @Test
     fun entryService_saveVehicleOnMondayDate_successful(){
        //Arrange
        val entryDate = Date(1629154065000) //Monday
        val licensePlate = "AZZ039"
        val car = Car(licensePlate,entryDate)
        val entryService = EntryService(car,vehicleRepository,parkingAmount)

        Mockito.`when`(parkingAmount.hasReachedVehicleLimit()).thenReturn(true)
        Mockito.`when`(vehicleRepository.vehicleExists(car.licensePlate)).thenReturn(null)

        //Act
        entryService.saveVehicle()
    }

    @Test
     fun entryService_deleteVehicleWithCorrectParameters_successful(){
        //Arrange
        val entryDate = Date()
        val licensePlate = "BZZ039"
        val car = Car(licensePlate,entryDate)
        val  entryService = EntryService(car,vehicleRepository,parkingAmount)

        //Act
        entryService.deleteVehicle()
    }
}
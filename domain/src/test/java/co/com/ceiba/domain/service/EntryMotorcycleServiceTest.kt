package co.com.ceiba.domain.service

import co.com.ceiba.domain.repository.MotorcycleRepository
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EntryMotorcycleServiceTest {
    @Mock
    lateinit var motorcycleRepository: MotorcycleRepository

    @Test
    fun entryMotorcycleService_createInstanceWithCorrectParameters(){
        //Arrange

        //Act
        val entryMotorcycleService = EntryMotorcycleService(motorcycleRepository)
        //Assert
        Assert.assertNotNull(entryMotorcycleService)
    }

    @Test
    fun entryMotorcycleService_validReachedVehicleLimitWhenAmountIsGreatThanMaximumValue_successful(){
        //Arrange
        val entryMotorcycleService = EntryMotorcycleService(motorcycleRepository)
        Mockito.`when`(motorcycleRepository.getAmount()).thenReturn(10)
        //Act
        val hasReached = entryMotorcycleService.hasReachedVehicleLimit()
        //Assert
        Assert.assertEquals(false,hasReached)
    }

    @Test
    fun entryMotorcycleService_validReachedVehicleLimitWhenAmountIsLessThanMaximumValue_successful(){
        //Arrange
        val entryMotorcycleService = EntryMotorcycleService(motorcycleRepository)
        Mockito.`when`(motorcycleRepository.getAmount()).thenReturn(9)
        //Act
        val hasReached = entryMotorcycleService.hasReachedVehicleLimit()
        //Assert
        Assert.assertEquals(true,hasReached)
    }
}
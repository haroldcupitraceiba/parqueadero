package co.com.ceiba.domain.service

import co.com.ceiba.domain.repository.CarRepository
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EntryCarServiceTest {
    @Mock
    lateinit var carRepository: CarRepository

    @Test
    fun entryCarService_createInstaceWithCorrectParameters(){
        //Arrange

        //Act
        val entryCarService = EntryCarService(carRepository)
        //Assert
        Assert.assertNotNull(entryCarService)
    }

    @Test
    fun entryCarService_validReachedVehicleLimitWhenAmountIsGreatThanMaximumValue_successful(){
        //Arrange
        val entryCarService = EntryCarService(carRepository)
        Mockito.`when`(carRepository.getAmount()).thenReturn(20)
        //Act
        val hasReached = entryCarService.hasReachedVehicleLimit()
        //Assert
        Assert.assertEquals(false,hasReached)
    }

    @Test
    fun entryCarService_validReachedVehicleLimitWhenAmountIsLessThanMaximumValue_successful(){
        //Arrange
        val entryCarService = EntryCarService(carRepository)
        Mockito.`when`(carRepository.getAmount()).thenReturn(19)
        //Act
        val hasReached = entryCarService.hasReachedVehicleLimit()
        //Assert
        Assert.assertEquals(true,hasReached)
    }
}
package co.com.ceiba.dataaccess.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.com.ceiba.dataaccess.repository.CarRepositoryImpl
import kotlinx.coroutines.launch

class CarViewModel(private val carRepositoryImpl: CarRepositoryImpl) : ViewModel() {
    fun getAmount(){
        viewModelScope.launch {
            carRepositoryImpl.getAmount()
        }
    }
}
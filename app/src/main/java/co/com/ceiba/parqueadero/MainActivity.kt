package co.com.ceiba.parqueadero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import co.com.ceiba.application.VehicleApplicationService
import co.com.ceiba.dataaccess.repository.MotorcycleRepositoryImpl
import co.com.ceiba.dataaccess.repository.VehicleRepositoryImpl
import co.com.ceiba.domain.model.Car
import co.com.ceiba.domain.model.Motorcycle
import co.com.ceiba.domain.service.EntryMotorcycleService
import co.com.ceiba.domain.service.EntryService
import co.com.ceiba.parqueadero.viewmodel.VehicleViewModel
import co.com.ceiba.parqueadero.viewmodel.VehicleViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var vehicleViewModel: VehicleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val motorcycle = Motorcycle("IWB24B",Date(), 300)
        val motorcycleRepository = MotorcycleRepositoryImpl(this)
        val motorcycleService = EntryMotorcycleService(motorcycleRepository)
        val vehicleRepositoryImpl = VehicleRepositoryImpl(this)
        val entryService = EntryService(motorcycle, vehicleRepositoryImpl, motorcycleService)
        val vehicleApplicationService = VehicleApplicationService(entryService)

        vehicleViewModel = ViewModelProvider(this, VehicleViewModelFactory(vehicleApplicationService) ).get(VehicleViewModel::class.java)
        initObservers()
    }

    private fun initObservers() {
        vehicleViewModel.executeSaveVehicle().observe(this, {
            if (it){
                Toast.makeText(this,"Vehiculo guardado",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"No se logro guardar el vehículo",Toast.LENGTH_SHORT).show()
            }
        })

        vehicleViewModel.executeDeleteVehicle().observe(this,{
            if (it){
                Toast.makeText(this,"Vehiculo eliminado",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"No se logro eliminar el vehículo",Toast.LENGTH_SHORT).show()
            }
        })

        vehicleViewModel.executeInfoMessage().observe(this,{
            it?.let {
                Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            }
        })
    }
}
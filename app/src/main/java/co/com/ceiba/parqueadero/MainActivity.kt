package co.com.ceiba.parqueadero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import co.com.ceiba.domain.model.Car
import co.com.ceiba.parqueadero.viewmodel.VehicleViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val vehicleViewModel: VehicleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //vehicleViewModel = ViewModelProvider(this).get(VehicleViewModel::class.java)

        val car = Car("AZZ039", Date())
        vehicleViewModel.executeSaveVehicle(car).observe(this, androidx.lifecycle.Observer {
            if (it){
                Toast.makeText(this,"Vehiculo guardado",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"No se logro guardar el vehiculo",Toast.LENGTH_SHORT).show()
            }
        })

    }
}
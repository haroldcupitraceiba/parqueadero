package co.com.ceiba.parqueadero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import co.com.ceiba.application.VehicleApplicationService
import co.com.ceiba.domain.model.Car
import co.com.ceiba.domain.model.Motorcycle
import co.com.ceiba.domain.model.Vehicle
import co.com.ceiba.parqueadero.databinding.ActivityMainBinding
import co.com.ceiba.parqueadero.factory.VehicleApplicationFactory
import co.com.ceiba.parqueadero.viewmodel.VehicleViewModel
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {


    private lateinit var binding : ActivityMainBinding
    private lateinit var vehicleViewModel: VehicleViewModel
    private var vehicleSearched: Vehicle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        vehicleViewModel = ViewModelProvider(this).get(VehicleViewModel::class.java)
        initObservers()
    }

    private fun initViews() {
        initMenuOptionsLayout()
        initEntryLayout()
        initExitLayout()
    }

    private fun initExitLayout(){
        binding.exitVehicle.searchButton.setOnClickListener {
            try {
                var vehicle:Vehicle
                var vehicleApplicationService: VehicleApplicationService?
                val licensePlate = binding.exitVehicle.exitLicensePlate.text.toString()

                if(binding.exitVehicle.radioCarExit.isChecked){
                    vehicle = Car(licensePlate, Date())
                    vehicleApplicationService =
                        VehicleApplicationFactory(vehicle, this, VehicleApplicationFactory.CAR_TYPE).getVehicle()
                }else{
                    val cylinderCapacity = 100
                    vehicle = Motorcycle(licensePlate,Date(),cylinderCapacity)
                    vehicleApplicationService =
                        VehicleApplicationFactory(vehicle, this, VehicleApplicationFactory.MOTORCYCLE_TYPE).getVehicle()
                }

                if (vehicleApplicationService != null){
                    vehicleViewModel.setVehicleService(vehicleApplicationService)
                    vehicleViewModel.executeSearchVehicle().observe(this,{
                        if (it != null){
                            vehicleSearched = it.vehicle
                            binding.exitVehicle.paymentValue.setText("$"+it.calculatePayment().toString())
                        }else{
                            showMessage(getString(R.string.vehicle_not_found))
                            vehicleSearched = null
                        }
                    })
                }
            }catch (ex: Exception){
                showMessage(ex.message.toString())
                binding.exitVehicle.exitLicensePlate.setText("")
                binding.exitVehicle.paymentValue.setText("$0")
                vehicleSearched = null
            }
        }

        binding.exitVehicle.exitButton.setOnClickListener {

            if (vehicleSearched == null)
                return@setOnClickListener

            var vehicleApplicationService = if(binding.exitVehicle.radioCarExit.isChecked){
                VehicleApplicationFactory(vehicleSearched!!, this, VehicleApplicationFactory.CAR_TYPE).getVehicle()
            }else{
                VehicleApplicationFactory(vehicleSearched!!, this, VehicleApplicationFactory.MOTORCYCLE_TYPE).getVehicle()
            }

            vehicleApplicationService?.let {vehicleApplicationService ->
                vehicleViewModel.setVehicleService(vehicleApplicationService)

                vehicleViewModel.executeDeleteVehicle().observe(this,{ message ->
                    it?.let {
                        showMessage(message)
                    }
                })

                showMenuOptions()
            }
        }

        binding.exitVehicle.cancelButton.setOnClickListener {
            showMenuOptions()
        }
    }

    private fun initEntryLayout(){
        hideCylinderCapacity()

        binding.entryVehicle.entryButton.setOnClickListener {

            var vehicle:Vehicle
            var vehicleApplicationService: VehicleApplicationService?
            val licensePlate = binding.entryVehicle.entryLicensePlate.text.toString()

            if(binding.entryVehicle.radioCarEntry.isChecked){
                vehicle = Car(licensePlate, Date())
                vehicleApplicationService =
                    VehicleApplicationFactory(vehicle, this, VehicleApplicationFactory.CAR_TYPE).getVehicle()
            }else{
                val cylinderCapacity = if(!binding.entryVehicle.cylinderCapacityEntry.text.toString().isNullOrEmpty())
                        binding.entryVehicle.cylinderCapacityEntry.text.toString().toInt()
                    else 0

                vehicle = Motorcycle(licensePlate,Date(),cylinderCapacity)
                vehicleApplicationService =
                    VehicleApplicationFactory(vehicle, this, VehicleApplicationFactory.MOTORCYCLE_TYPE).getVehicle()
            }

            vehicleApplicationService?.let {
                vehicleViewModel.setVehicleService(vehicleApplicationService)

                vehicleViewModel.executeSaveVehicle().observe(this,{
                    showMessage(it)
                })

                showMenuOptions()
            }
        }

        binding.entryVehicle.cancelButton.setOnClickListener {
            showMenuOptions()
        }

        binding.entryVehicle.radioCarEntry.setOnClickListener {
            binding.entryVehicle.cylinderCapacityEntry.setText("")
            if (binding.entryVehicle.radioCarEntry.isChecked){
                hideCylinderCapacity()
            }else{
                showCylinderCapacity()
            }
        }

        binding.entryVehicle.radioMotorcycleEntry.setOnClickListener {
            binding.entryVehicle.cylinderCapacityEntry.setText("")
            if (binding.entryVehicle.radioMotorcycleEntry.isChecked){
                showCylinderCapacity()
            }else{
                hideCylinderCapacity()
            }
        }
    }

    private fun hideCylinderCapacity(){
        binding.entryVehicle.tilCylinderCapacity.visibility = View.GONE
    }

    private fun showCylinderCapacity(){
        binding.entryVehicle.tilCylinderCapacity.visibility = View.VISIBLE
    }

    private fun resetEntryValues(){
        binding.entryVehicle.entryLicensePlate.setText("")
        binding.entryVehicle.radioCarEntry.isChecked = true
        hideCylinderCapacity()
    }

    private fun initMenuOptionsLayout() {
        showMenuOptions()
        binding.menuOptions.entryOptionButton.setOnClickListener {
            showEntryVehicleLayout()
        }

        binding.menuOptions.exitOptionButton.setOnClickListener {
            showExitVehicleLayout()
        }
    }

    private fun showMenuOptions() {
        binding.menuOptions.menuOptions.visibility = View.VISIBLE
        hideEntryVehicleLayout()
        hideExitVehicleLayout()
    }

    private fun hideMenuOptions() {
        binding.menuOptions.menuOptions.visibility = View.GONE
    }

    private fun showExitVehicleLayout(){
        binding.exitVehicle.exitLicensePlate.setText("")
        binding.exitVehicle.paymentValue.setText("$0")
        vehicleSearched = null
        binding.exitVehicle.exitVehicle.visibility = View.VISIBLE
        hideMenuOptions()
        hideEntryVehicleLayout()
    }

    private fun hideExitVehicleLayout(){
        binding.exitVehicle.exitVehicle.visibility = View.GONE
    }

    private fun showEntryVehicleLayout(){
        binding.entryVehicle.entryVehicle.visibility = View.VISIBLE
        resetEntryValues()
        hideMenuOptions()
        hideExitVehicleLayout()
    }

    private fun hideEntryVehicleLayout(){
        binding.entryVehicle.entryVehicle.visibility = View.GONE
    }

    private fun initObservers() {

        vehicleViewModel.observeInfoMessage().observe(this,{
            it?.let {
                showMessage(it)
            }
        })
    }

    private fun showMessage(message:String){
        binding.infoMessage.setText(message)
    }
}
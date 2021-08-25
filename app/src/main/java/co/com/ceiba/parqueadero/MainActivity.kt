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
    private var typeVehicleChecked = VehicleApplicationFactory.Companion.TypeVehicle.CAR

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
        binding.exitVehicle.radioCarExit.isChecked = true
        typeVehicleChecked = VehicleApplicationFactory.Companion.TypeVehicle.CAR

        binding.exitVehicle.searchButton.setOnClickListener {
            val licensePlate = binding.exitVehicle.exitLicensePlate.text.toString()
            val defaultCylinderCapacity = 100
            val vehicleApplication = VehicleApplicationFactory(
                licensePlate = licensePlate,
                cylinderCapacity = defaultCylinderCapacity,
                context = this,
                typeVehicle = typeVehicleChecked
            ).getVehicle()

            vehicleApplication?.let { vehicleApplication ->
                vehicleApplication.service?.let {
                    vehicleViewModel.setVehicleService(vehicleApplication.service)
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
                vehicleApplication.exceptionMessage?.let {
                    showMessage(it)
                    binding.exitVehicle.exitLicensePlate.setText("")
                    binding.exitVehicle.paymentValue.setText("$0")
                    vehicleSearched = null
                }
            }
        }

        binding.exitVehicle.exitButton.setOnClickListener {

            if (vehicleSearched == null)
                return@setOnClickListener

            val vehicleApplication = VehicleApplicationFactory(
                licensePlate = vehicleSearched!!.licensePlate,
                cylinderCapacity = vehicleSearched!!.cylinderCapacity,
                context = this,
                typeVehicle = typeVehicleChecked
            ).getVehicle()

            vehicleApplication?.let {vehicleApplication ->
                vehicleApplication.service?.let {
                    vehicleViewModel.setVehicleService(vehicleApplication.service)
                    vehicleViewModel.executeDeleteVehicle().observe(this,{ message ->
                        it?.let {
                            showMessage(message)
                        }
                    })
                    showMenuOptions()
                }

                vehicleApplication.exceptionMessage?.let { showMessage(it) }
            }
        }

        binding.exitVehicle.cancelButton.setOnClickListener {
            showMenuOptions()
        }

        binding.exitVehicle.radioCarExit.setOnClickListener {
            if (binding.exitVehicle.radioCarExit.isChecked){
                typeVehicleChecked = VehicleApplicationFactory.Companion.TypeVehicle.CAR
            }
        }

        binding.exitVehicle.radioMotorcycleExit.setOnClickListener {
            if (binding.exitVehicle.radioMotorcycleExit.isChecked){
                typeVehicleChecked = VehicleApplicationFactory.Companion.TypeVehicle.MOTORCYCLE
            }
        }
    }

    private fun initEntryLayout(){
        binding.entryVehicle.radioCarEntry.isChecked = true
        typeVehicleChecked = VehicleApplicationFactory.Companion.TypeVehicle.CAR
        hideCylinderCapacity()

        binding.entryVehicle.entryButton.setOnClickListener {

            val licensePlate = binding.entryVehicle.entryLicensePlate.text.toString()
            val cylinderCapacity = if(!binding.entryVehicle.cylinderCapacityEntry.text.toString().isNullOrEmpty())
                    binding.entryVehicle.cylinderCapacityEntry.text.toString().toInt()
                else 0

            val vehicleApplication = VehicleApplicationFactory(licensePlate, cylinderCapacity, this, typeVehicleChecked).getVehicle()

            vehicleApplication?.let { vehicleApplication ->
                vehicleApplication.service?.let {
                    vehicleViewModel.setVehicleService(vehicleApplication.service)
                    vehicleViewModel.executeSaveVehicle().observe(this,{
                        showMessage(it)
                    })
                    showMenuOptions()
                }
                vehicleApplication.exceptionMessage?.let { showMessage(it) }
            }
        }

        binding.entryVehicle.cancelButton.setOnClickListener {
            showMenuOptions()
        }

        binding.entryVehicle.radioCarEntry.setOnClickListener {
            binding.entryVehicle.cylinderCapacityEntry.setText("")
            if (binding.entryVehicle.radioCarEntry.isChecked){
                typeVehicleChecked = VehicleApplicationFactory.Companion.TypeVehicle.CAR
                hideCylinderCapacity()
            }else{
                showCylinderCapacity()
            }
        }

        binding.entryVehicle.radioMotorcycleEntry.setOnClickListener {
            binding.entryVehicle.cylinderCapacityEntry.setText("")
            if (binding.entryVehicle.radioMotorcycleEntry.isChecked){
                typeVehicleChecked = VehicleApplicationFactory.Companion.TypeVehicle.MOTORCYCLE
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
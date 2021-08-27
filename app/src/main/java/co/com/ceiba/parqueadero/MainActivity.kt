package co.com.ceiba.parqueadero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import co.com.ceiba.application.dto.VehiclePayment
import co.com.ceiba.parqueadero.databinding.ActivityMainBinding
import co.com.ceiba.parqueadero.viewmodel.VehicleViewModel
import java.util.*

class MainActivity : AppCompatActivity() {


    private lateinit var binding : ActivityMainBinding
    private lateinit var vehicleViewModel: VehicleViewModel
    private var vehicleSearched: VehiclePayment? = null
    private val vehicleTypeCar = "CAR"
    private val vehicleTypeMotorcycle = "MOTORCYCLE"
    private var typeVehicleChecked = vehicleTypeCar

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
        typeVehicleChecked = vehicleTypeCar

        binding.exitVehicle.searchButton.setOnClickListener {
            val licensePlate = binding.exitVehicle.exitLicensePlate.text.toString()
            val defaultCylinderCapacity = 100
            val exceptionMessage:String? = vehicleViewModel.setDataToCreateService(licensePlate,defaultCylinderCapacity,this,typeVehicleChecked)

            if (exceptionMessage == null){
                vehicleViewModel.executeSearchVehicle().observe(this,{
                    showPaymentValue(it)
                })
            }else{
                initVehicleSearched(exceptionMessage)
            }
        }

        binding.exitVehicle.exitButton.setOnClickListener {

            if (vehicleSearched == null)
                return@setOnClickListener

            val exceptionMessage = vehicleViewModel.setDataToCreateService(
                vehicleSearched!!.licensePlate,
                vehicleSearched!!.cylinderCapacity,
                this,
                typeVehicleChecked
            )

            if (exceptionMessage == null){
                vehicleViewModel.executeDeleteVehicle().observe(this,{ message ->
                    showMessage(message)
                })
                showMenuOptions()
            }else{
                showMessage(exceptionMessage!!)
            }
        }

        binding.exitVehicle.cancelButton.setOnClickListener {
            showMenuOptions()
        }

        binding.exitVehicle.radioCarExit.setOnClickListener {
            if (binding.exitVehicle.radioCarExit.isChecked){
                typeVehicleChecked = vehicleTypeCar
            }
        }

        binding.exitVehicle.radioMotorcycleExit.setOnClickListener {
            if (binding.exitVehicle.radioMotorcycleExit.isChecked){
                typeVehicleChecked = vehicleTypeMotorcycle
            }
        }
    }

    private fun initVehicleSearched(exceptionMessage: String) {
        showMessage(exceptionMessage!!)
        binding.exitVehicle.exitLicensePlate.setText("")
        binding.exitVehicle.paymentValue.setText("$0")
        vehicleSearched = null
    }

    private fun showPaymentValue(vehiclePayment: VehiclePayment?) {
        if (vehiclePayment != null){
            vehicleSearched = vehiclePayment
            binding.exitVehicle.paymentValue.setText("$"+vehicleSearched!!.paymentValue.toString())
        }else{
            showMessage(getString(R.string.vehicle_not_found))
            vehicleSearched = null
        }
    }

    private fun initEntryLayout(){
        binding.entryVehicle.radioCarEntry.isChecked = true
        typeVehicleChecked = vehicleTypeCar
        hideCylinderCapacity()

        binding.entryVehicle.entryButton.setOnClickListener {

            val licensePlate = binding.entryVehicle.entryLicensePlate.text.toString()
            val cylinderCapacity = if(!binding.entryVehicle.cylinderCapacityEntry.text.toString().isNullOrEmpty()){
                    binding.entryVehicle.cylinderCapacityEntry.text.toString().toInt()
                }
                else 0

            val exceptionMessage = vehicleViewModel.setDataToCreateService(licensePlate, cylinderCapacity, this, typeVehicleChecked)

            if (exceptionMessage == null){
                vehicleViewModel.executeSaveVehicle().observe(this,{
                    showMessage(it)
                })
                showMenuOptions()
            }else{
                showMessage(exceptionMessage!!)
            }
        }

        binding.entryVehicle.cancelButton.setOnClickListener {
            showMenuOptions()
        }

        binding.entryVehicle.radioCarEntry.setOnClickListener {
            binding.entryVehicle.cylinderCapacityEntry.setText("")
            if (binding.entryVehicle.radioCarEntry.isChecked){
                typeVehicleChecked = vehicleTypeCar
                hideCylinderCapacity()
            }else{
                showCylinderCapacity()
            }
        }

        binding.entryVehicle.radioMotorcycleEntry.setOnClickListener {
            binding.entryVehicle.cylinderCapacityEntry.setText("")
            if (binding.entryVehicle.radioMotorcycleEntry.isChecked){
                typeVehicleChecked = vehicleTypeMotorcycle
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
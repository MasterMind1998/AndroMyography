package com.example.andromyography.Patient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.andromyography.BluetoothConfigurationAndEMGTest.SelectDeviceActivity
import com.example.andromyography.DataBaseModel.ObjectUser
import com.example.andromyography.DataBaseModel.UserDAO
import com.example.andromyography.ManualAndAbout.AboutUs
import com.example.andromyography.ManualAndAbout.UserManual
import com.example.andromyography.R
import kotlinx.android.synthetic.main.add_new_patient_layout.*


class AddNewPatient : AppCompatActivity() {
    
    private val realmDB = UserDAO()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_patient_layout)
        
        title = "Add New Patient"
        
        submit_and_start_test.setOnClickListener {
            
            val objectUser = ObjectUser()
            
            val pID = add_id_field.text.toString()
            val pName = add_name_field.text.toString()
            val pFamily = add_family_field.text.toString()
            val pAge = add_age_field.text.toString()
            val pPhone = add_phone_field.text.toString()
            val pGender = add_gender_field.selectedItem.toString()
            val pDamaged = add_damaged_muscle_field.text.toString()
            
            when{
                pID.isEmpty() ->{
                    Toast.makeText(this , "Please Enter Patient ID" , Toast.LENGTH_SHORT).show()
                }
                
                pName.isEmpty() ->{
                    Toast.makeText(this , "Please Enter Patient Name" , Toast.LENGTH_SHORT).show()
                }
                
                pFamily.isEmpty() ->{
                    Toast.makeText(this , "Please Enter Patient Family" , Toast.LENGTH_SHORT).show()
                }
                
                pAge.isEmpty() ->{
                    Toast.makeText(this , "Please Enter Patient Age" , Toast.LENGTH_SHORT).show()
                }
                
                pPhone.isEmpty() ->{
                    Toast.makeText(this , "Please Enter Patient Phone Number" , Toast.LENGTH_SHORT).show()
                }
                
                pGender == "Choose the Gender..." ->{
                    Toast.makeText(this , "Please Enter Patient Gender" , Toast.LENGTH_SHORT).show()
                }
                
                pDamaged.isEmpty() ->{
                    Toast.makeText(this , "Please Enter Patient Damaged Muscles" , Toast.LENGTH_SHORT).show()
                }
                
                else ->{
                    objectUser.patientID = pID.toLong()
                    objectUser.patientName = pName
                    objectUser.patientFamily = pFamily
                    objectUser.patientAge = pAge.toInt()
                    objectUser.patientPhone = pPhone.toLong()
                    objectUser.patientGender = pGender
                    objectUser.patientDamagedMuscle = pDamaged
                    
                    realmDB.insertDB(objectUser)
                    Toast.makeText(this , "Patient Information was Successfully Saved" , Toast.LENGTH_SHORT).show()
                    val intent = Intent(this , SelectDeviceActivity::class.java)
                    startActivity(intent)
                    
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.menu_add -> {
                Toast.makeText(this , "Please Enter Patient Information " , Toast.LENGTH_SHORT).show()
            }
            R.id.menu_search -> startActivity(Intent(this, SearchPatient::class.java))
            R.id.menu_edit -> startActivity(Intent(this, EditPatientProfile::class.java))
            R.id.menu_howToUse -> startActivity(Intent(this, UserManual::class.java))
            R.id.menu_aboutUs -> startActivity(Intent(this, AboutUs::class.java))
        }
        return true
    }

    override fun onDestroy() {
        realmDB.closeDB()
        super.onDestroy()
    }
}
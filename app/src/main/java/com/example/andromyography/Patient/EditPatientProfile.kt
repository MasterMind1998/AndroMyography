package com.example.andromyography.Patient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.andromyography.DataBaseModel.UserDAO
import com.example.andromyography.ManualAndAbout.AboutUs
import com.example.andromyography.ManualAndAbout.UserManual
import com.example.andromyography.R
import kotlinx.android.synthetic.main.edit_patient_profile_layout.*

class EditPatientProfile : AppCompatActivity() {

    private val realmDB = UserDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_patient_profile_layout)

        title = "Edit Patient Information by ID"

        submit_id_for_edit.setOnClickListener {


            val pID = id_for_edit.text.toString()

            when {

                pID.isEmpty() -> {
                    Toast.makeText(this, "Please Enter Patient ID", Toast.LENGTH_SHORT).show()
                }else -> {
                try {



                    val intent = Intent(this , EditPatientProfileForm::class.java)
                    val resultForEdit = realmDB.readByID(pID.toLong())

                    intent.putExtra("PatID", resultForEdit?.patientID.toString())
                    intent.putExtra("PatName", resultForEdit?.patientName.toString())
                    intent.putExtra("PatFamily", resultForEdit?.patientFamily.toString())
                    intent.putExtra("PatAge", resultForEdit?.patientAge.toString())
                    intent.putExtra("PatPhone", resultForEdit?.patientPhone.toString())
                    intent.putExtra("PatDamaged", resultForEdit?.patientDamagedMuscle.toString())
                    startActivity(intent)


                }catch (ex : Exception){
                    Toast.makeText(this , "${ex.printStackTrace()}" , Toast.LENGTH_SHORT).show()
                }

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

            R.id.menu_add -> startActivity(Intent(this, AddNewPatient::class.java))
            R.id.menu_search -> startActivity(Intent(this, SearchPatient::class.java))
            R.id.menu_edit -> {
                Toast.makeText(this , "Please Enter Patient ID" , Toast.LENGTH_SHORT).show()
            }
            R.id.menu_howToUse -> startActivity(Intent(this, UserManual::class.java))
            R.id.menu_aboutUs -> startActivity(Intent(this, AboutUs::class.java))
        }
        return true
    }
}
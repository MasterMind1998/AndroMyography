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
import kotlinx.android.synthetic.main.search_patient_layout.*

class SearchPatient : AppCompatActivity() {

    private val realmDB = UserDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_patient_layout)

        title = "Search Patient by ID"

        submit_id_for_search.setOnClickListener {

            val pID = id_for_search.text.toString()
            when{
                pID.isEmpty() ->{
                    Toast.makeText(this , "Please Enter Patient ID" , Toast.LENGTH_SHORT).show()

                }
                else ->{
                    try {
                        val intent = Intent(this , PatientSearchResult::class.java)
                        val resultByID = realmDB.readByID(pID.toLong())

                        intent.putExtra("PatientID", resultByID?.patientID.toString())
                        intent.putExtra("PatientName", resultByID?.patientName.toString())
                        intent.putExtra("PatientFamily", resultByID?.patientFamily.toString())
                        intent.putExtra("PatientAge", resultByID?.patientAge.toString())
                        intent.putExtra("PatientPhone", resultByID?.patientPhone.toString())
                        intent.putExtra("PatientGender", resultByID?.patientGender.toString())
                        intent.putExtra("PatientDamagedMuscle", resultByID?.patientDamagedMuscle.toString())
                        startActivity(intent)

                    }
                    catch (ex:Exception){
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
            R.id.menu_search -> {
                Toast.makeText(this , "Please Enter Patient ID" , Toast.LENGTH_SHORT).show()
            }
            R.id.menu_edit -> startActivity(Intent(this, EditPatientProfile::class.java))
            R.id.menu_howToUse -> startActivity(Intent(this, UserManual::class.java))
            R.id.menu_aboutUs -> startActivity(Intent(this, AboutUs::class.java))
        }
        return true
    }
}
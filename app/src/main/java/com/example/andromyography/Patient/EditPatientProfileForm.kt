package com.example.andromyography.Patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.andromyography.DataBaseModel.ObjectUser
import com.example.andromyography.DataBaseModel.UserDAO
import com.example.andromyography.R
import kotlinx.android.synthetic.main.edit_patient_profile_form_layout.*

class EditPatientProfileForm : AppCompatActivity() {

    private val realmDB = UserDAO()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_patient_profile_form_layout)

        title = "Edit Patient Information"



        edit_id_field.setText(intent.getStringExtra("PatID"))
        edit_name_field.setText(intent.getStringExtra("PatName"))
        edit_family_field.setText(intent.getStringExtra("PatFamily"))
        edit_age_field.setText(intent.getStringExtra("PatAge"))
        edit_phone_field.setText(intent.getStringExtra("PatPhone"))
        edit_damaged_muscle_field.setText(intent.getStringExtra("PatDamaged"))

        apply_changes.setOnClickListener {


            val updateId = intent.getStringExtra("PatID")
            val updateName = edit_name_field.text.toString()
            val updateFamily = edit_family_field.text.toString()
            val updateAge = edit_age_field.text.toString()
            val updatePhone = edit_phone_field.text.toString()
            val updateGender = edit_gender_field.selectedItem.toString()
            val updateDamaged = edit_damaged_muscle_field.text.toString()

            when (updateGender) {
                "Choose the Gender..." -> {
                    Toast.makeText(this , "Please Choose Patient Gender" , Toast.LENGTH_SHORT).show()
                }
                else -> {

                    val objectUser = ObjectUser()

                    objectUser.patientID = updateId?.toLong()
                    objectUser.patientName = updateName
                    objectUser.patientFamily = updateFamily
                    objectUser.patientAge = updateAge.toInt()
                    objectUser.patientPhone = updatePhone.toLong()
                    objectUser.patientGender = updateGender
                    objectUser.patientDamagedMuscle = updateDamaged

                    realmDB.editDB(objectUser)
                    Toast.makeText(this , "Profile Update Completed Successfully" , Toast.LENGTH_SHORT).show()

                }

            }
        }


    }
}
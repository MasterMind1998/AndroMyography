package com.example.andromyography.Patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.andromyography.R
import kotlinx.android.synthetic.main.patient_search_result_layout.*

class PatientSearchResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patient_search_result_layout)

        title = "Patient Search Result"

        id_result.text = intent.getStringExtra("PatientID")
        name_result.text = intent.getStringExtra("PatientName")
        family_result.text = intent.getStringExtra("PatientFamily")
        age_result.text = intent.getStringExtra("PatientAge")
        phone_result.text = intent.getStringExtra("PatientPhone")
        gender_result.text = intent.getStringExtra("PatientGender")
        damaged_muscle_result.text = intent.getStringExtra("PatientDamagedMuscle")


        back_to_main.setOnClickListener {
            super.onBackPressed()
        }
    }


}
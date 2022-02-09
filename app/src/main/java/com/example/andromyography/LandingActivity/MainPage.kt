package com.example.andromyography.LandingActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.andromyography.ManualAndAbout.AboutUs
import com.example.andromyography.ManualAndAbout.UserManual
import com.example.andromyography.Patient.AddNewPatient
import com.example.andromyography.Patient.EditPatientProfile
import com.example.andromyography.Patient.SearchPatient
import com.example.andromyography.R
import kotlinx.android.synthetic.main.main_page_layout.*

class MainPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page_layout)

        btn_addNewPatient.setOnClickListener {
            val intent = Intent(this, AddNewPatient::class.java)
            startActivity(intent)
        }

        btn_searchPatient.setOnClickListener {
            val intent = Intent(this, SearchPatient::class.java)
            startActivity(intent)
        }

        btn_editPatient.setOnClickListener {
            val intent = Intent(this, EditPatientProfile::class.java)
            startActivity(intent)
        }

        btn_howToUse.setOnClickListener {
            val intent = Intent(this, UserManual::class.java)
            startActivity(intent)
        }

        btn_aboutUs.setOnClickListener {
            val intent = Intent(this, AboutUs::class.java)
            startActivity(intent)
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
            R.id.menu_edit -> startActivity(Intent(this, EditPatientProfile::class.java))
            R.id.menu_howToUse -> startActivity(Intent(this, UserManual::class.java))
            R.id.menu_aboutUs -> startActivity(Intent(this, AboutUs::class.java))
        }
        return true
    }
}
package com.example.andromyography.ManualAndAbout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.andromyography.Patient.AddNewPatient
import com.example.andromyography.Patient.EditPatientProfile
import com.example.andromyography.Patient.SearchPatient
import com.example.andromyography.R

class UserManual : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_manual_layout)
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
            R.id.menu_aboutUs -> startActivity(Intent(this, AboutUs::class.java))
        }
        return true
    }
}
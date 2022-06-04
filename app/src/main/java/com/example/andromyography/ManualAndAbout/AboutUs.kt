package com.example.andromyography.ManualAndAbout

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ScrollView
import android.widget.TextView
import com.example.andromyography.Patient.AddNewPatient
import com.example.andromyography.Patient.EditPatientProfile
import com.example.andromyography.Patient.SearchPatient
import com.example.andromyography.R
import kotlinx.android.synthetic.main.about_us_layout.*

class AboutUs : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_us_layout)


        val text = findViewById<TextView>(R.id.textview_about)
        text.text = "My name is Abbas Mahdiyeh\n" +
                "I'm junior Android Developer\n" +
                "and I have +1 year experience with kotlin.\n" +
                "I am studying for a bachelor's degree in computer software in Islamic Azad University.\n" +
                "and This is my final bachelor project."

        github_account.setOnClickListener {
            val url = "https://github.com/MasterMind1998"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        twitter_account.setOnClickListener {
            val url = "https://twitter.com/AbbasMahdiyeh"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }


        linkedin_account.setOnClickListener {
            val url = "https://linkedin.com/in/abbas-mahdiyeh-800959235"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        stackOverFlow_account.setOnClickListener {
            val url = "https://stackoverflow.com/users/15493045/abbas-mahdiyeh"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
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
        }
        return true
    }
}
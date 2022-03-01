package com.example.andromyography.ManualAndAbout

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ScrollView
import com.example.andromyography.R
import kotlinx.android.synthetic.main.about_us_layout.*

class AboutUs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_us_layout)

        github_account.setOnClickListener {
            val url = "https://github.com/MasterMind1998"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        twitter_account.setOnClickListener {
            val url = "https://github.com/MasterMind1998"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        gmail_account.setOnClickListener {
            val url = "https://github.com/MasterMind1998"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        instagram_account.setOnClickListener {
            val url = "https://github.com/MasterMind1998"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        telegram_account.setOnClickListener {
            val url = "https://github.com/MasterMind1998"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }


    }
}
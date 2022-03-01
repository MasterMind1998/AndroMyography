package com.example.andromyography.LandingActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.example.andromyography.R
import kotlinx.android.synthetic.main.spalsh_screen_layout.*

class SpalshScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.spalsh_screen_layout)

        val animZoom = AnimationUtils.loadAnimation(this , R.anim.zoom_in)
        val animLeft = AnimationUtils.loadAnimation(this , R.anim.move_left)
        val animRight = AnimationUtils.loadAnimation(this , R.anim.move_right)
        val animBottom = AnimationUtils.loadAnimation(this , R.anim.move_bottom)

        logo_splash.animation = animZoom
        welcome_splash.animation = animLeft
        check_muscle.animation = animRight
        designByAbbas.animation = animBottom

        Handler().postDelayed({
            val intent = Intent(this , MainPage::class.java)
            startActivity(intent)
            finish()

        },7000)
    }
}
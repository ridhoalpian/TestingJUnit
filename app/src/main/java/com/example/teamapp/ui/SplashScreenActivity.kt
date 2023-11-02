package com.example.teamapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.activity.ComponentActivity
import com.example.teamapp.R
import com.example.teamapp.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : ComponentActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.root.startAnimation(fadeIn)

        val sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        Handler().postDelayed({
            val fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
            binding.root.startAnimation(fadeOut)
                if (isLoggedIn) {
                    // Pengguna telah login, arahkan ke MainActivity
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    // Pengguna belum login, arahkan ke LoginActivity
                    startActivity(Intent(this, OnBoarding::class.java))
                }
                finish()
        }, 3000)
    }
}

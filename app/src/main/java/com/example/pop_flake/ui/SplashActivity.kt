package com.example.pop_flake.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pop_flake.MainActivity
import com.example.pop_flake.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIMEOUT = 2000L // Splash screen timeout in milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        // Launch a coroutine on the Main dispatcher
        CoroutineScope(Dispatchers.Main).launch {
            delay(SPLASH_TIMEOUT) // Delay for the specified time
            navigateToMainActivity() // Navigate to the main activity
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

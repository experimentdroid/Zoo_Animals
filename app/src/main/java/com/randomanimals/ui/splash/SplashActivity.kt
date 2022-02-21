package com.randomanimals.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.randomanimals.MainActivity
import com.randomanimals.databinding.ActivitySplashBinding
import java.util.*


class SplashActivity : AppCompatActivity() {

    companion object {
        private val TAG = SplashActivity::class.java.name
        private const val TIMER = 2_000L
        const val ZOO_PREFS = "ZOO_PREFS"
        const val SHOW_SPLASH = "SHOW_SPLASH"
    }

    private lateinit var binding: ActivitySplashBinding
    private var timer = Timer()
    private lateinit var preferences: SharedPreferences
    private var shouldShowSplash = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPreferences()
        if (shouldShowSplash) {
            binding = ActivitySplashBinding.inflate(layoutInflater)
            setContentView(binding.root)
            scheduleTask()
        } else {
            goToMainActivity()
        }
    }

    private fun checkPreferences() { //check preferences to decide whether show or not splash
        preferences = applicationContext.getSharedPreferences(ZOO_PREFS, 0)
        shouldShowSplash = preferences.getBoolean(SHOW_SPLASH, true)
        if (shouldShowSplash) {
            preferences.edit(true) {
                putBoolean(SHOW_SPLASH, false)
            }
        }
    }

    private fun scheduleTask() {
        val task = object : TimerTask() {
            override fun run() {
                goToMainActivity()
            }
        }
        timer.schedule(task, TIMER)
    }

    private fun goToMainActivity() {
        Intent(this@SplashActivity, MainActivity::class.java).apply {
            startActivity(this)
        }
        finish()
    }
}

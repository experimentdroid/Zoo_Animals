package com.randomanimals

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.randomanimals.databinding.ActivityMainBinding
import com.randomanimals.ui.home.HomeFragment


class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.name
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolBar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        toolBar = binding.toolbar
        setSupportActionBar(toolBar)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment())
                .commitNow()
        }
    }
}

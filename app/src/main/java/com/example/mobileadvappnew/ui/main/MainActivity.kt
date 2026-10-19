package com.example.mobileadvappnew.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileadvappnew.databinding.ActivityMainBinding
import com.example.mobileadvappnew.ui.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var counter = 0
    private val TAG = "LifecycleApp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt("KEY_COUNTER", 0)
            binding.tvCounter.text = counter.toString()
        }

        binding.btnIncrement.setOnClickListener {
            counter++
            binding.tvCounter.text = counter.toString()
        }

        // Explicit Intent dengan Transfer Data
        binding.btnKirim.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("EXTRA_NAMA", binding.etNama.text.toString())
                putExtra("EXTRA_NIM", binding.etNim.text.toString())
                putExtra("EXTRA_PRODI", binding.etProdi.text.toString())
                putExtra("EXTRA_SCORE", counter)
            }
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("KEY_COUNTER", counter)
    }
}
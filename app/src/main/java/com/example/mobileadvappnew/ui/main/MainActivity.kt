package com.example.mobileadvappnew.ui.main

import android.os.Bundle
import android.widget.Toast
import com.example.mobileadvappnew.databinding.ActivityMainBinding

class MainActivity : androidx.activity.ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menginisialisasi ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Event listener saat tombol diklik
        binding.btnCheckConfig.setOnClickListener {
            binding.tvStatus.text = "ViewBinding Berhasil Dilakukan!"
            Toast.makeText(
                this,
                "Konfigurasi Project & ViewBinding Berhasil!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
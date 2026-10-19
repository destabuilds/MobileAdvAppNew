package com.example.mobileadvappnew

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menampilkan layout activity_main yang berisi NavHostFragment
        setContentView(R.layout.activity_main)
    }
}
package com.example.mobileadvappnew.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.mobileadvappnew.MainActivity
import com.example.mobileadvappnew.R
import com.example.mobileadvappnew.data.local.SettingPreferences
import com.example.mobileadvappnew.data.local.dataStore
import com.example.mobileadvappnew.ui.main.SettingViewModel
import com.example.mobileadvappnew.ui.main.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val pref = SettingPreferences.getInstance(applicationContext.dataStore)
        viewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]

        // Terapkan Tema yang tersimpan
        viewModel.getThemeSettings().observe(this) { isDarkMode ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Cek Auto Login (Tugas 1 Point 3)
        viewModel.getUserSession().observe(this) { session ->
            val token = session.first
            if (token.isNotEmpty()) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val dummyToken = "token_abc123xyz"
                viewModel.saveUserSession(dummyToken, username)
                Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Isi username dan password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
package com.example.mobileadvappnew

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mobileadvappnew.data.local.AppDatabase
import com.example.mobileadvappnew.data.local.Course
import com.example.mobileadvappnew.data.local.SettingPreferences
import com.example.mobileadvappnew.data.local.dataStore
import com.example.mobileadvappnew.ui.LoginActivity
import com.example.mobileadvappnew.ui.main.SettingViewModel
import com.example.mobileadvappnew.ui.main.SettingsActivity
import com.example.mobileadvappnew.ui.main.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getDatabase(applicationContext)
            db.courseDao().insertCourse(
                Course(
                    courseCode = "IF123",
                    courseName = "Pemrograman Mobile Lanjut",
                    credits = 4,
                    lecturerName = "Tim Dosen"
                )
            )
        }

        val pref = SettingPreferences.getInstance(applicationContext.dataStore)
        viewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val tvToken = findViewById<TextView>(R.id.tvToken)
        val btnSettings = findViewById<Button>(R.id.btnSettings)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        viewModel.getUserSession().observe(this) { session ->
            val token = session.first
            val name = session.second

            if (token.isEmpty()) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                tvWelcome.text = "Selamat Datang, $name!"
                tvToken.text = "Token Sesi: $token"
            }
        }

        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Feature Logout (Tugas 3)
        btnLogout.setOnClickListener {
            viewModel.clearUserSession()
        }
    }
}
package com.example.mobileadvappnew.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileadvappnew.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menerima data dari MainActivity
        val nama = intent.getStringExtra("EXTRA_NAMA") ?: ""
        val nim = intent.getStringExtra("EXTRA_NIM") ?: ""
        val prodi = intent.getStringExtra("EXTRA_PRODI") ?: ""
        val score = intent.getIntExtra("EXTRA_SCORE", 0)

        binding.tvDetailInfo.text = "Detail Data Mahasiswa:\n\nNama: $nama\nNIM: $nim\nProgram Studi: $prodi\nSkor: $score"

        // 1. Call Center Kampus (Dialer)
        binding.btnDialPhone.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:0274884201")
            }
            startActivity(dialIntent)
        }

        // 2. Lokasi Kampus (Google Maps)
        binding.btnOpenMap.setOnClickListener {
            val mapIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("geo:-7.7599,110.4083?q=Universitas+AMIKOM+Yogyakarta")
            }
            startActivity(mapIntent)
        }

        // 3. Website Kampus (Web Browser)
        binding.btnOpenWeb.setOnClickListener {
            val webIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://amikom.ac.id")
            }
            startActivity(webIntent)
        }
    }
}
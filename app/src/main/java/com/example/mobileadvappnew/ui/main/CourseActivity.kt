package com.example.mobileadvappnew.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mobileadvappnew.data.local.AppDatabase
import com.example.mobileadvappnew.databinding.ActivityCourseBinding
import kotlinx.coroutines.launch

class CourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseBinding

    // Inisialisasi ViewModel dengan Factory
    private val courseViewModel: CourseViewModel by viewModels {
        CourseViewModelFactory(AppDatabase.getDatabase(this).courseDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Mengamati aliran data StateFlow dari CourseViewModel secara reaktif
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                courseViewModel.allCourses.collect { courseList ->
                    // Update UI / RecyclerView Adapter di sini
                    // Contoh: courseAdapter.submitList(courseList)
                }
            }
        }

        // 2. Event Klik Tombol Simpan
        binding.btnSaveCourse.setOnClickListener {
            val code = binding.edtCourseCode.text.toString()
            val name = binding.edtCourseName.text.toString()
            val creditsText = binding.edtCourseCredits.text.toString()
            val lecturer = binding.edtCourseLecturer.text.toString()

            if (code.isNotEmpty() && name.isNotEmpty() && creditsText.isNotEmpty() && lecturer.isNotEmpty()) {
                val credits = creditsText.toIntOrNull() ?: 0

                // Panggil fungsi insert pada ViewModel
                courseViewModel.insertCourse(code, name, credits, lecturer)

                // Clear input form
                binding.edtCourseCode.text?.clear()
                binding.edtCourseName.text?.clear()
                binding.edtCourseCredits.text?.clear()
                binding.edtCourseLecturer.text?.clear()

                Toast.makeText(this, "Mata Kuliah Berhasil Disimpan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Harap isi semua kolom!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
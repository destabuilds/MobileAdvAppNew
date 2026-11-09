package com.example.mobileadvappnew.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobileadvappnew.data.local.AppDatabase
import com.example.mobileadvappnew.data.local.CourseDao
import com.example.mobileadvappnew.databinding.ActivityCourseBinding
import com.example.mobileadvappnew.ui.CourseAdapter
import com.example.mobileadvappnew.ui.CourseViewModel
import kotlinx.coroutines.launch

class CourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseBinding
    private lateinit var courseAdapter: CourseAdapter

    private val courseViewModel: CourseViewModel by viewModels {
        CourseViewModelFactory(AppDatabase.getDatabase(this).courseDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Setup RecyclerView & Adapter
        courseAdapter = CourseAdapter()
        binding.rvCourse.apply {
            adapter = courseAdapter
            layoutManager = LinearLayoutManager(this@CourseActivity)
        }

        // 2. Mengamati data StateFlow secara reaktif dari Room via ViewModel
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                courseViewModel.allCourses.collect { courseList ->
                    // Mengirimkan list data mata kuliah terbaru ke Adapter
                    courseAdapter.submitList(courseList)
                }
            }
        }

        // 3. Tombol Simpan
        binding.btnSaveCourse.setOnClickListener {
            val code = binding.edtCourseCode.text.toString()
            val name = binding.edtCourseName.text.toString()
            val creditsText = binding.edtCourseCredits.text.toString()
            val lecturer = binding.edtCourseLecturer.text.toString()

            if (code.isNotEmpty() && name.isNotEmpty() && creditsText.isNotEmpty() && lecturer.isNotEmpty()) {
                val credits = creditsText.toIntOrNull() ?: 0

                // Tambahkan data ke DB via ViewModel
                courseViewModel.insertCourse(code, name, credits, lecturer)

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

class CourseViewModelFactory(private val courseDao: CourseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CourseViewModel(courseDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
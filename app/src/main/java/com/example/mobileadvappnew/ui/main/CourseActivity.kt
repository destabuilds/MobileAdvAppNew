package com.example.mobileadvappnew.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobileadvapp.ui.CourseAdapter
import com.example.mobileadvappnew.data.local.Course
import com.example.mobileadvappnew.databinding.ActivityCourseBinding
import com.example.mobileadvappnew.ui.CourseViewModel
import kotlinx.coroutines.launch

class CourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseBinding
    private lateinit var courseAdapter: CourseAdapter
    private val courseViewModel: CourseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
        observeCourseList()
    }

    private fun setupRecyclerView() {
        // Init Adapter dengan Click Listener (Tugas 2)
        courseAdapter = CourseAdapter { course ->
            Toast.makeText(
                this,
                "Detail MK: ${course.courseName} (${course.credits} SKS)",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.rvCourses.apply {
            layoutManager = LinearLayoutManager(this@CourseActivity)
            adapter = courseAdapter
        }
    }

    private fun setupListeners() {
        binding.btnSave.setOnClickListener {
            val code = binding.etCourseCode.text.toString().trim()
            val name = binding.etCourseName.text.toString().trim()
            val creditsStr = binding.etCredits.text.toString().trim()
            val lecturer = binding.etLecturerName.text.toString().trim()

            if (code.isNotEmpty() && name.isNotEmpty() && creditsStr.isNotEmpty() && lecturer.isNotEmpty()) {
                // Ubah toIntOrDefault menjadi toIntOrNull() ?: 0
                val creditsInt = creditsStr.toIntOrNull() ?: 0

                val newCourse = Course(
                    courseCode = code,
                    courseName = name,
                    credits = creditsInt,
                    lecturerName = lecturer
                )

                // Perbaikan di sini: langsung masukkan newCourse tanpa 'code ='
                courseViewModel.insertCourse(code, name, creditsInt, lecturer)

                // Clear input field
                binding.etCourseCode.text?.clear()
                binding.etCourseName.text?.clear()
                binding.etCredits.text?.clear()
                binding.etLecturerName.text?.clear()
            } else {
                Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Observasi Flow dari Room secara real-time (Tugas 3)
    private fun observeCourseList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                courseViewModel.allCourses.collect { courseList ->
                    courseAdapter.submitList(courseList)
                }
            }
        }
    }
}
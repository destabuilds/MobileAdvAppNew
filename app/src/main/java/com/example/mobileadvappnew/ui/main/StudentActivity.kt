package com.example.mobileadvappnew.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobileadvapp.ui.StudentAdapter
import com.example.mobileadvappnew.databinding.ActivityStudentBinding
import com.example.mobileadvappnew.ui.StudentViewModel
import kotlinx.coroutines.launch

class StudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentBinding
    private lateinit var studentAdapter: StudentAdapter
    private val studentViewModel: StudentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
        observeStudentList()
    }

    private fun setupRecyclerView() {
        studentAdapter = StudentAdapter { student ->
            Toast.makeText(
                this,
                "Diklik: ${student.name} (${student.nim})",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.rvStudents.apply {
            layoutManager = LinearLayoutManager(this@StudentActivity)
            adapter = studentAdapter
        }
    }

    private fun setupListeners() {
        binding.btnSaveStudent.setOnClickListener {
            val nim = binding.etNim.text.toString().trim()
            val name = binding.etName.text.toString().trim()
            val prodi = binding.etStudyProgram.text.toString().trim()

            if (nim.isNotEmpty() && name.isNotEmpty() && prodi.isNotEmpty()) {
                // Panggil langsung variabel string-nya (bukan memasukkan objek Student)
                studentViewModel.insertStudent(name, nim, prodi)

                // Bersihkan input
                binding.etNim.text?.clear()
                binding.etName.text?.clear()
                binding.etStudyProgram.text?.clear()
            } else {
                Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeStudentList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                studentViewModel.allStudents.collect { studentList ->
                    studentAdapter.submitList(studentList)
                }
            }
        }
    }
}
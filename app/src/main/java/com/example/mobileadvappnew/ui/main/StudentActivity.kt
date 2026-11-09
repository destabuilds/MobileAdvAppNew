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
import com.example.mobileadvappnew.data.local.StudentDao
import com.example.mobileadvappnew.databinding.ActivityStudentBinding
import com.example.mobileadvappnew.ui.StudentAdapter
import com.example.mobileadvappnew.ui.StudentViewModel
import kotlinx.coroutines.launch

class StudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentBinding
    private lateinit var studentAdapter: StudentAdapter

    private val studentViewModel: StudentViewModel by viewModels {
        StudentViewModelFactory(AppDatabase.getDatabase(this).studentDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        studentAdapter = StudentAdapter()
        binding.rvStudent.apply {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@StudentActivity)
        }

        // Mengamati StateFlow secara reaktif dari StudentViewModel
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                studentViewModel.allStudents.collect { studentList ->
                    studentAdapter.submitList(studentList)
                }
            }
        }

        // Action Klik Tombol Simpan
        binding.btnSave.setOnClickListener {
            val name = binding.edtName.text.toString()
            val nim = binding.edtNim.text.toString()
            val program = binding.edtProgram.text.toString()

            if (name.isNotEmpty() && nim.isNotEmpty() && program.isNotEmpty()) {
                studentViewModel.insertStudent(name, nim, program)

                binding.edtName.text?.clear()
                binding.edtNim.text?.clear()
                binding.edtProgram.text?.clear()

                Toast.makeText(this, "Data Mahasiswa Berhasil Disimpan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Harap isi semua inputan!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

class StudentViewModelFactory(private val studentDao: StudentDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentViewModel(studentDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
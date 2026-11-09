package com.example.mobileadvappnew.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileadvappnew.data.local.Student
import com.example.mobileadvappnew.data.local.StudentDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StudentViewModel(private val studentDao: StudentDao) : ViewModel() {

    // Aliran data StateFlow dari Room Database ke UI
    val allStudents: StateFlow<List<Student>> = studentDao.getAllStudents()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Fungsi untuk menambah data mahasiswa
    fun insertStudent(name: String, nim: String, studyProgram: String) {
        viewModelScope.launch {
            val student = Student(
                name = name,
                nim = nim,
                studyProgram = studyProgram
            )
            studentDao.insertStudent(student)
        }
    }
}
package com.example.mobileadvappnew.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileadvappnew.data.local.Course
import com.example.mobileadvappnew.data.local.CourseDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CourseViewModel(private val courseDao: CourseDao) : ViewModel() {

    // 1. Properti StateFlow untuk mengambil data mata kuliah
    val allCourses: StateFlow<List<Course>> = courseDao.getAllCourses()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // 2. Fungsi untuk menambah data mata kuliah baru ke Room Database
    fun insertCourse(code: String, name: String, credits: Int, lecturer: String) {
        viewModelScope.launch {
            val course = Course(
                courseCode = code,
                courseName = name,
                credits = credits,
                lecturerName = lecturer
            )
            courseDao.insertCourse(course)
        }
    }
}
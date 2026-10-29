package com.example.mobileadvappnew

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mobileadvappnew.data.local.AppDatabase
import com.example.mobileadvappnew.data.local.CourseDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CourseDaoTest {
    private lateinit var courseDao: CourseDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        courseDao = db.courseDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetAllCourses() = runBlocking {
        val course1 = Course(
            courseCode = "CS101",
            courseName = "Pemrograman Mobile",
            credits = 3,
            lecturerName = "Dosen A"
        )
        val course2 = Course(
            courseCode = "CS102",
            courseName = "Algoritma",
            credits = 4,
            lecturerName = "Dosen B"
        )

        courseDao.insertCourse(course1)
        courseDao.insertCourse(course2)

        val allCourses = courseDao.getAllCourses().first()
        assertEquals(2, allCourses.size)
        assertEquals("Algoritma", allCourses[0].courseName)
    }

    @Test
    @Throws(Exception::class)
    fun getCoursesByCredits() = runBlocking {
        val course1 = Course(
            courseCode = "CS101",
            courseName = "Pemrograman Mobile",
            credits = 3,
            lecturerName = "Dosen A"
        )
        val course2 = Course(
            courseCode = "CS102",
            courseName = "Basis Data",
            credits = 2,
            lecturerName = "Dosen B"
        )

        courseDao.insertCourse(course1)
        courseDao.insertCourse(course2)

        val filteredCourses = courseDao.getCoursesByCredits(3).first()
        assertEquals(1, filteredCourses.size)
        assertEquals("Pemrograman Mobile", filteredCourses[0].courseName)
    }
}
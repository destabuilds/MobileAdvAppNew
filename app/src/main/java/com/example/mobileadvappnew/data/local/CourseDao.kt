package com.example.mobileadvappnew.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCourse(course: Course)

    // Mengambil semua data dikelompokkan/diurutkan berdasarkan nama mata kuliah (ASC)
    @Query("SELECT * FROM courses ORDER BY course_name ASC")
    fun getAllCourses(): Flow<List<Course>>

    // Mengambil data mata kuliah dengan SKS >= minCredits
    @Query("SELECT * FROM courses WHERE credits >= :minCredits")
    fun getCoursesByCredits(minCredits: Int): Flow<List<Course>>
}
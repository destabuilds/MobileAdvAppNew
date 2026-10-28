package com.example.mobileadvappnew.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "course_code")
    val courseCode: String,

    @ColumnInfo(name = "course_name")
    val courseName: String,

    @ColumnInfo(name = "credits")
    val credits: Int,

    @ColumnInfo(name = "lecturer_name")
    val lecturerName: String
)
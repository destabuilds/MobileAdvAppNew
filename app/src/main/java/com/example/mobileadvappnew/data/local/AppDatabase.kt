package com.example.mobileadvappnew.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// 1. Tambahkan Course::class ke dalam array entities
// 2. Naikkan versi database dari 1 menjadi 2
@Database(entities = [Student::class, Course::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    // 3. Deklarasikan fungsi abstrak untuk CourseDao
    abstract fun courseDao(): CourseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration() // Membantu migrasi saat versi dinaikkan dalam tahap pengembangan
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
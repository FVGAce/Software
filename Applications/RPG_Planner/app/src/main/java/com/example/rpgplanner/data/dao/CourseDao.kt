package com.example.rpgplanner.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.rpgplanner.data.model.Course

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCourse(course: Course)

    @Update
    suspend fun updateCourse(course: Course)

    @Delete
    suspend fun deleteCourse(course: Course)

    @Query("DELETE FROM course_table")
    suspend fun deleteAllData()

    @Query("SELECT * FROM course_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Course>>
}
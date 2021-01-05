package com.example.rpgplanner.data.repository

import androidx.lifecycle.LiveData
import com.example.rpgplanner.data.dao.CourseDao
import com.example.rpgplanner.data.model.Course

class CourseRepository(private val courseDao: CourseDao) {
    val readAllData: LiveData<List<Course>> = courseDao.readAllData()

    suspend fun addCourse(course: Course){
        courseDao.addCourse(course)
    }

    suspend fun updateCourse(course: Course){
        courseDao.updateCourse(course)
    }

    suspend fun deleteCourse(course: Course){
        courseDao.deleteCourse(course)
    }

    suspend fun deleteAllData(){
        courseDao.deleteAllData()
    }
}
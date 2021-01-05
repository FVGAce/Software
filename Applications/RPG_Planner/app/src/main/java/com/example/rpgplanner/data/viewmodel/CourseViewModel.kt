package com.example.rpgplanner.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.rpgplanner.data.PlannerDB
import com.example.rpgplanner.data.model.Course
import com.example.rpgplanner.data.repository.CourseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CourseViewModel(application: Application): AndroidViewModel(application){
    private val repository: CourseRepository
    val readAllData: LiveData<List<Course>>

    init{
        val courseDao = PlannerDB.getDatabase(application).courseDao()
        repository = CourseRepository(courseDao)
        readAllData = repository.readAllData
    }

    fun addCourse(course: Course){
        viewModelScope.launch(Dispatchers.IO){
            repository.addCourse(course)
        }
    }

    fun updateCourse(course: Course){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateCourse(course)
        }
    }

    fun deleteCourse(course: Course){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteCourse(course)
        }
    }

    fun deleteAllData(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllData()
        }
    }
}
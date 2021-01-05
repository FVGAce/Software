package com.example.rpgplanner.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.rpgplanner.data.PlannerDB
import com.example.rpgplanner.data.model.Assignment
import com.example.rpgplanner.data.repository.AssignmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AssignmentViewModel(application: Application): AndroidViewModel(application) {
    private val repository: AssignmentRepository
    val readAllData: LiveData<List<Assignment>>

    init{
        val assignmentDao = PlannerDB.getDatabase(application).assignmentDao()
        repository = AssignmentRepository(assignmentDao)
        readAllData = repository.readAllData
    }

    fun readCourseAssignments(cId: Int): LiveData<List<Assignment>>{
        return repository.readCourseAssignments(cId)
    }

    fun addAssignment(assignment: Assignment){
        viewModelScope.launch(Dispatchers.IO){
            repository.addAssignment(assignment)
        }
    }

    fun updateAssignment(assignment: Assignment){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateAssignment(assignment)
        }
    }

    fun deleteAssignment(assignment: Assignment){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAssignment(assignment)
        }
    }

    fun deleteAssignments(cId: Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAssignments(cId)
        }
    }

    fun deleteAllData(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllData()
        }
    }
}
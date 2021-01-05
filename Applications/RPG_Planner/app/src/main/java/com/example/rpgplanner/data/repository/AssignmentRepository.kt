package com.example.rpgplanner.data.repository

import androidx.lifecycle.LiveData
import com.example.rpgplanner.data.dao.AssignmentDao
import com.example.rpgplanner.data.model.Assignment

class AssignmentRepository(private val assignmentDao: AssignmentDao) {
    val readAllData: LiveData<List<Assignment>> = assignmentDao.readAllData()

    fun readCourseAssignments(cId: Int): LiveData<List<Assignment>> {
        return assignmentDao.readCourseAssignments(cId)
    }

    suspend fun addAssignment(assignment: Assignment){
        assignmentDao.addAssignment(assignment)
    }

    suspend fun updateAssignment(assignment: Assignment){
        assignmentDao.updateAssignment(assignment)
    }

    suspend fun deleteAssignment(assignment: Assignment){
        assignmentDao.deleteAssignment(assignment)
    }

    suspend fun deleteAssignments(cId: Int){
        assignmentDao.deleteAssignments(cId)
    }

    suspend fun deleteAllData(){
        assignmentDao.deleteAllData()
    }
}
package com.example.rpgplanner.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.rpgplanner.data.model.Assignment

@Dao
interface AssignmentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAssignment(assignment: Assignment)

    @Update
    suspend fun updateAssignment(assignment: Assignment)

    @Delete
    suspend fun deleteAssignment(assignment: Assignment)

    @Query("DELETE FROM assignment_table WHERE courseId == :cId")
    suspend fun deleteAssignments(cId: Int)

    @Query("DELETE FROM assignment_table")
    suspend fun deleteAllData()

    @Query("SELECT * FROM assignment_table WHERE courseId == :cId")
    fun readCourseAssignments(cId: Int): LiveData<List<Assignment>>

    @Query("SELECT * FROM assignment_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Assignment>>
}
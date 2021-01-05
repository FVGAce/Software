package com.example.rpgplanner.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "assignment_table")
data class Assignment (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val courseId: Int,
    val assignmentName: String,
    @Embedded
    val dueDate: DateTime
        ): Parcelable

@Parcelize
data class DateTime(
    val date: String
    //val year: String,
    //val month: String,
    //val day: String
    //val hour: Int,
    //val minute: Int
): Parcelable
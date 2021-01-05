package com.example.rpgplanner.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "course_table")
data class Course (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val courseName: String,
    val totalAssignments: Int,
    val pendingAssignments: Int
    ): Parcelable
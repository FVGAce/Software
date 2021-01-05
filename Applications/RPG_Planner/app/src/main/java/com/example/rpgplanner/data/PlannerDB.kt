package com.example.rpgplanner.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rpgplanner.data.dao.AssignmentDao
import com.example.rpgplanner.data.dao.CourseDao
import com.example.rpgplanner.data.dao.UserDao
import com.example.rpgplanner.data.model.Assignment
import com.example.rpgplanner.data.model.Course
import com.example.rpgplanner.data.model.User

@Database(entities = [User::class, Course::class, Assignment::class], version = 1, exportSchema = false)
abstract class PlannerDB : RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun courseDao(): CourseDao
    abstract fun assignmentDao(): AssignmentDao

    companion object{
        @Volatile
        private var INSTANCE: PlannerDB? = null

        fun getDatabase(context: Context): PlannerDB{
            val tempInstance = INSTANCE

            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlannerDB::class.java,
                    "planner_database"
                ).build()

                INSTANCE = instance

                return instance
            }
        }
    }
}
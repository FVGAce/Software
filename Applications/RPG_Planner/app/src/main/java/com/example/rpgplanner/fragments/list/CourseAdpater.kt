package com.example.rpgplanner.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.rpgplanner.R
import com.example.rpgplanner.data.model.Course

class CourseAdpater: RecyclerView.Adapter<CourseAdpater.MyViewHolder>() {
    private var courseList = emptyList<Course>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.course_cell, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = courseList[position]

        holder.itemView.findViewById<TextView>(R.id.courseName_txt).text = currentItem.courseName
        holder.itemView.findViewById<TextView>(R.id.completed_txt).text =
            (currentItem.totalAssignments-currentItem.pendingAssignments).toString()
        holder.itemView.findViewById<TextView>(R.id.pending_txt).text =
            currentItem.pendingAssignments.toString()

        holder.itemView.findViewById<ConstraintLayout>(R.id.course_cell).setOnClickListener{
            val action = CourseFragmentDirections
                .actionCourseFragmentToCourseAssignmentsFragment(currentItem, "${currentItem.courseName}")
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    fun setData(course: List<Course>){
        this.courseList = course
        notifyDataSetChanged()
    }
}
package com.example.rpgplanner.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.rpgplanner.R
import com.example.rpgplanner.data.model.Assignment
import com.example.rpgplanner.data.model.Course

class CourseAssignmentsAdpater: RecyclerView.Adapter<CourseAssignmentsAdpater.MyViewHolder>() {
    private var courseAssignmentList = emptyList<Assignment>()
    private lateinit var course: Course

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.assignment_cell, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = courseAssignmentList[position]

        holder.itemView.findViewById<TextView>(R.id.assignment_txt).text = currentItem.assignmentName
        holder.itemView.findViewById<TextView>(R.id.date_txt).text = currentItem.dueDate.date

        holder.itemView.findViewById<ConstraintLayout>(R.id.assignment_cell).setOnClickListener {
            val action = CourseAssignmentsFragmentDirections
                .actionCourseAssignmentsFragmentToUpdateCourseAssignmentFragment(currentItem, course)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return courseAssignmentList.size
    }

    fun setData(assignment: List<Assignment>, course: Course){
        this.course = course
        this.courseAssignmentList = assignment
        notifyDataSetChanged()
    }
}
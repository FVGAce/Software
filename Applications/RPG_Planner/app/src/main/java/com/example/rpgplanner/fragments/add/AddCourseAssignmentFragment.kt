package com.example.rpgplanner.fragments.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.rpgplanner.R
import com.example.rpgplanner.data.model.Assignment
import com.example.rpgplanner.data.model.Course
import com.example.rpgplanner.data.model.DateTime
import com.example.rpgplanner.data.viewmodel.AssignmentViewModel
import com.example.rpgplanner.data.viewmodel.CourseViewModel
import java.util.*

class AddCourseAssignmentFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private val args by navArgs<AddCourseAssignmentFragmentArgs>()
    private lateinit var mAssignmentViewModel: AssignmentViewModel
    private lateinit var mCourseViewModel: CourseViewModel
    private val cal = Calendar.getInstance()
    private lateinit var date: TextView

    private var savedDate = cal.get(Calendar.MONTH).toString() + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_course_assignment, container, false)

        mAssignmentViewModel = ViewModelProvider(this)  .get(AssignmentViewModel::class.java)
        mCourseViewModel = ViewModelProvider(this).get(CourseViewModel::class.java)

        view.findViewById<TextView>(R.id.assignmentCourseName_txt).text = args.currentCourse.courseName
        date = view.findViewById(R.id.courseAssignmentDate_et)
        date.text = savedDate

        view.findViewById<TextView>(R.id.courseAssignmentDate_et).setOnClickListener {
            DatePickerDialog(requireContext(), this, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }


        view.findViewById<Button>(R.id.addCourseAssignment_button).setOnClickListener{
            insertDatatoDatabase(view)
        }

        return view
    }

    private fun insertDatatoDatabase(view: View){
        val assignmentName = view.findViewById<EditText>(R.id.courseAssignmentName_et).text.toString()
        val assignmentDate = view.findViewById<TextView>(R.id.courseAssignmentDate_et).text.toString()

        if(checkIfEmpty(assignmentName, assignmentDate)){
            val date = DateTime(assignmentDate)
            val assignment = Assignment(0, args.currentCourse.id, assignmentName, date)
            val course = Course(args.currentCourse.id, args.currentCourse.courseName,
                (args.currentCourse.totalAssignments + 1), (args.currentCourse.pendingAssignments + 1))

            mAssignmentViewModel.addAssignment(assignment)
            mCourseViewModel.updateCourse(course)

            Toast.makeText(requireContext(), "Succesfully Added!", Toast.LENGTH_LONG).show()
            val action = AddCourseAssignmentFragmentDirections
                .actionAddCourseAssignmentFragmentToCourseAssignmentsFragment(course,
                "${course.courseName}")
            findNavController().navigate(action)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkIfEmpty(assignmentName: String, assignmentDate: String): Boolean{
        return !TextUtils.isEmpty(assignmentName) && !TextUtils.isEmpty(assignmentDate)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDate = month.toString() + "/" + dayOfMonth.toString() + "/" + year.toString()
        date.text = savedDate
    }
}
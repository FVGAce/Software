package com.example.rpgplanner.fragments.update

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
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

class UpdateCourseAssignmentFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private val args by navArgs<UpdateCourseAssignmentFragmentArgs>()
    private lateinit var mAssignmentViewModel: AssignmentViewModel
    private lateinit var mCourseViewModel: CourseViewModel
    private val cal = Calendar.getInstance()
    private lateinit var date: TextView
    private lateinit var savedDate: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_course_assignment, container, false)

        mAssignmentViewModel = ViewModelProvider(this).get(AssignmentViewModel::class.java)
        mCourseViewModel = ViewModelProvider(this).get(CourseViewModel::class.java)

        date = view.findViewById(R.id.update_courseAssignmentDate_et)

        view.findViewById<TextView>(R.id.update_assignmentCourseName_txt).text = args.currentAssignment.courseId.toString()
        view.findViewById<EditText>(R.id.update_courseAssignmentName_et).setText(args.currentAssignment.assignmentName)
        date.text = args.currentAssignment.dueDate.date

        date.setOnClickListener {
            DatePickerDialog(requireContext(), this, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        view.findViewById<Button>(R.id.updateCourseAssignment_button).setOnClickListener{
            updateAssignment(view)
        }

        view.findViewById<Button>(R.id.completedAssignment_button).setOnClickListener{
            completeAssignment()
        }

        setHasOptionsMenu(true)

        return view
    }

    private fun updateAssignment(view: View){
        val assignmentName = view.findViewById<EditText>(R.id.update_courseAssignmentName_et).text.toString()
        val assignmentDate = date.text.toString()

        if(checkIfEmpty(assignmentName, assignmentDate)){
            val date = DateTime(assignmentDate)
            val assignment = Assignment(args.currentAssignment.id, args.currentCourse.id, assignmentName, date)

            mAssignmentViewModel.updateAssignment(assignment)

            Toast.makeText(requireContext(), "Update Successful", Toast.LENGTH_LONG).show()
            val action = UpdateCourseAssignmentFragmentDirections
                .actionUpdateCourseAssignmentFragmentToCourseAssignmentsFragment(args.currentCourse,
                "${args.currentCourse.courseName}")
            findNavController().navigate(action)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkIfEmpty(assignmentName: String, assignmentDate: String): Boolean{
        return !TextUtils.isEmpty(assignmentName) && !TextUtils.isEmpty(assignmentDate)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_menu){
            deleteAssignment()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun completeAssignment(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            val course = Course(args.currentCourse.id, args.currentCourse.courseName,
                args.currentCourse.totalAssignments, (args.currentCourse.pendingAssignments - 1))

            mAssignmentViewModel.deleteAssignment(args.currentAssignment)
            mCourseViewModel.updateCourse(course)

            Toast.makeText(requireContext(), "Completed ${args.currentAssignment.assignmentName}", Toast.LENGTH_LONG).show()
            val action = UpdateCourseAssignmentFragmentDirections
                .actionUpdateCourseAssignmentFragmentToCourseAssignmentsFragment(args.currentCourse,
                "${args.currentCourse.courseName}")
            findNavController().navigate(action)
        }
        builder.setNegativeButton("No"){_,_ -> }
        builder.setTitle("Complete ${args.currentAssignment.assignmentName}")
        builder.setMessage("Have you completed ${args.currentAssignment.assignmentName}?")
        builder.create().show()
    }

    private fun deleteAssignment(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            val course = Course(args.currentCourse.id, args.currentCourse.courseName,
                (args.currentCourse.totalAssignments - 1), (args.currentCourse.pendingAssignments - 1))

            mAssignmentViewModel.deleteAssignment(args.currentAssignment)
            mCourseViewModel.updateCourse(course)

            Toast.makeText(requireContext(), "Deleted Assignment ${args.currentAssignment.assignmentName}", Toast.LENGTH_LONG).show()
            val action = UpdateCourseAssignmentFragmentDirections
                .actionUpdateCourseAssignmentFragmentToCourseAssignmentsFragment(args.currentCourse,
                "${args.currentCourse.courseName}")
            findNavController().navigate(action)
        }
        builder.setNegativeButton("No"){_,_ -> }
        builder.setTitle("Delete Assignment ${args.currentAssignment.assignmentName}")
        builder.setMessage("Do you want to delete ${args.currentAssignment.assignmentName}")
        builder.create().show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDate = month.toString() + "/" + dayOfMonth.toString() + "/" + year.toString()
        date.text = savedDate
    }
}
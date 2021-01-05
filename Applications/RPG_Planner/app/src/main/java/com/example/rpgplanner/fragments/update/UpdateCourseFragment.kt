package com.example.rpgplanner.fragments.update

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.rpgplanner.R
import com.example.rpgplanner.data.model.Course
import com.example.rpgplanner.data.viewmodel.CourseViewModel

class UpdateCourseFragment : Fragment() {
    private val args by navArgs<UpdateCourseFragmentArgs>()
    private lateinit var mCourseViewModel: CourseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_course, container, false)

        mCourseViewModel = ViewModelProvider(this).get(CourseViewModel::class.java)

        view.findViewById<EditText>(R.id.update_courseName).setText(args.currentCourse.courseName)

        view.findViewById<Button>(R.id.update_button).setOnClickListener{
            updateItem(view)
        }

        return view
    }

    private fun updateItem(view: View){
        val courseName = view.findViewById<EditText>(R.id.update_courseName).text.toString()

        if(!TextUtils.isEmpty(courseName)){
            val updatedCourse = Course(args.currentCourse.id, courseName,
                args.currentCourse.totalAssignments, args.currentCourse.pendingAssignments)
            mCourseViewModel.updateCourse(updatedCourse)

            Toast.makeText(requireContext(), "Update Successful", Toast.LENGTH_LONG).show()

            val action = UpdateCourseFragmentDirections
                .actionUpdateCourseFragmentToCourseAssignmentsFragment(updatedCourse,
                "${updatedCourse.courseName}")
            findNavController().navigate(action)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_LONG).show()
        }
    }
}
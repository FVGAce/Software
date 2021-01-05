package com.example.rpgplanner.fragments.add

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
import com.example.rpgplanner.R
import com.example.rpgplanner.data.model.Course
import com.example.rpgplanner.data.viewmodel.CourseViewModel

class AddCourseFragment : Fragment() {
    private lateinit var mCourseViewModel: CourseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_course, container, false)

        mCourseViewModel = ViewModelProvider(this).get(CourseViewModel::class.java)

        view.findViewById<Button>(R.id.add_button).setOnClickListener{
            insertDataToDatabase(view)
        }

        return view
    }

    private fun insertDataToDatabase(view: View){
        val courseName = view.findViewById<EditText>(R.id.courseName_et).text.toString()

        if(!TextUtils.isEmpty(courseName)){
            val course = Course(0, courseName, 0, 0)
            mCourseViewModel.addCourse(course)
            Toast.makeText(requireContext(), "Succesfully Added!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addCourseFragment_to_courseFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }
}
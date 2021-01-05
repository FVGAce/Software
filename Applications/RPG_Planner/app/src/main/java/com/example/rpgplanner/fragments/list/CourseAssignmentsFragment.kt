package com.example.rpgplanner.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rpgplanner.R
import com.example.rpgplanner.data.viewmodel.AssignmentViewModel
import com.example.rpgplanner.data.viewmodel.CourseViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CourseAssignmentsFragment : Fragment() {
    private val args by navArgs<CourseAssignmentsFragmentArgs>()
    private lateinit var mCourseViewModel: CourseViewModel
    private lateinit var mAssignmentViewModel: AssignmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_course_assignments, container, false)

        val adpater = CourseAssignmentsAdpater()
        val recyclerView = view.findViewById<RecyclerView>(R.id.courseAssignments_recycler_view)
        recyclerView.adapter = adpater
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mAssignmentViewModel = ViewModelProvider(this).get(AssignmentViewModel::class.java)
        mCourseViewModel = ViewModelProvider(this).get(CourseViewModel::class.java)

        mAssignmentViewModel.readCourseAssignments(args.currentCourse.id)
            .observe(viewLifecycleOwner, { assignment -> adpater.setData(assignment, args.currentCourse)})

        view.findViewById<FloatingActionButton>(R.id.add_courseAssignment).setOnClickListener{
            val action = CourseAssignmentsFragmentDirections
                .actionCourseAssignmentsFragmentToAddCourseAssignmentFragment(args.currentCourse)
            findNavController().navigate(action)
        }

        view.findViewById<FloatingActionButton>(R.id.update_course).setOnClickListener{
            val action = CourseAssignmentsFragmentDirections
                .actionCourseAssignmentsFragmentToUpdateCourseFragment(args.currentCourse)
            findNavController().navigate(action)
        }

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_menu){
            deleteCourse()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteCourse(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            mCourseViewModel.deleteCourse(args.currentCourse)
            mAssignmentViewModel.deleteAssignments(args.currentCourse.id)
            Toast.makeText(requireContext(),
                "Succesfully Removed All ${args.currentCourse.courseName} data", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_courseAssignmentsFragment_to_courseFragment)
        }
        builder.setNegativeButton("No"){_,_ -> }
        builder.setTitle("Delete All ${args.currentCourse.courseName} Data")
        builder.setMessage("Are you sure you want to delete all ${args.currentCourse.courseName} data?")
        builder.create().show()
    }
}
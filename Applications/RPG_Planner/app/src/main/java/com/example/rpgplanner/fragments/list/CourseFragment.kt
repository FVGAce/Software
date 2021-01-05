package com.example.rpgplanner.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rpgplanner.R
import com.example.rpgplanner.data.viewmodel.AssignmentViewModel
import com.example.rpgplanner.data.viewmodel.CourseViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CourseFragment : Fragment() {
    private lateinit var mCourseViewModel: CourseViewModel
    private lateinit var mAssignmentViewModel: AssignmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_course, container, false)

        val adapter = CourseAdpater()
        val recyclerView = view.findViewById<RecyclerView>(R.id.course_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

            mCourseViewModel = ViewModelProvider(this).get(CourseViewModel::class.java)
        mAssignmentViewModel = ViewModelProvider(this).get(AssignmentViewModel::class.java)

        mCourseViewModel.readAllData.observe(viewLifecycleOwner, { course ->
            adapter.setData(course)
        })

        view.findViewById<FloatingActionButton>(R.id.add_course).setOnClickListener{
            findNavController().navigate(R.id.action_courseFragment_to_addCourseFragment)
        }

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_menu){
            deleteAllData()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllData(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            mCourseViewModel.deleteAllData()
            mAssignmentViewModel.deleteAllData()
            Toast.makeText(requireContext(), "Successfully Removed All courses and Data", Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("No"){_,_ -> }
        builder.setTitle("Delete All Users")
        builder.setMessage("Are you sure you want to delete all courses and assignments?")
        builder.create().show()
    }
}
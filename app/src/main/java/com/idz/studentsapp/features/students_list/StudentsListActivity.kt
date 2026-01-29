package com.idz.studentsapp.features.students_list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.idz.studentsapp.R
import com.idz.studentsapp.base.Constants
import com.idz.studentsapp.dao.StudentsRepository
import com.idz.studentsapp.features.add_student.AddStudentActivity
import com.idz.studentsapp.features.student_details.StudentDetailsActivity
import com.idz.studentsapp.utilis.StudentAdapter

class StudentsListActivity : AppCompatActivity() {
    
    private lateinit var adapter: StudentAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAddStudent: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students_list)
        
        // Setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        
        recyclerView = findViewById(R.id.recyclerViewStudents)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        fabAddStudent = findViewById(R.id.fabAddStudent)
        fabAddStudent.setOnClickListener {
            startActivity(Intent(this, AddStudentActivity::class.java))
        }
        
        setupAdapter()
    }
    
    private fun setupAdapter() {
        val students = StudentsRepository.getStudents()
        adapter = StudentAdapter(
            students,
            onItemClick = { student ->
                val intent = Intent(this, StudentDetailsActivity::class.java)
                intent.putExtra(Constants.STUDENT_ID_KEY, student.id)
                startActivity(intent)
            },
            onCheckChanged = { student, isChecked ->
                StudentsRepository.updateStudentCheckStatus(student.id, isChecked)
            }
        )
        recyclerView.adapter = adapter
    }
    
    override fun onResume() {
        super.onResume()
        val students = StudentsRepository.getStudents()
        adapter.updateStudents(students)
    }
}

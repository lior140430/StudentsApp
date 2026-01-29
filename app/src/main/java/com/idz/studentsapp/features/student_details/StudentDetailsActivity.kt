package com.idz.studentsapp.features.student_details

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.idz.studentsapp.R
import com.idz.studentsapp.base.Constants
import com.idz.studentsapp.dao.StudentsRepository
import com.idz.studentsapp.features.edit_student.EditStudentActivity

class StudentDetailsActivity : AppCompatActivity() {
    
    private var studentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)
        
        // Setup toolbar with back button
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        studentId = intent.getStringExtra(Constants.STUDENT_ID_KEY)
        
        val btnEdit = findViewById<Button>(R.id.btnEdit)
        
        btnEdit.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra(Constants.STUDENT_ID_KEY, studentId)
            startActivity(intent)
        }
        
        displayStudentDetails()
    }
    
    override fun onResume() {
        super.onResume()
        // Check if student exists before displaying
        val student = studentId?.let { StudentsRepository.getStudentById(it) }
        if (student == null) {
            finish()
        } else {
            displayStudentDetails()
        }
    }
    
    private fun displayStudentDetails() {
        studentId?.let { id ->
            val student = StudentsRepository.getStudentById(id)
            student?.let {
                findViewById<TextView>(R.id.tvName).text = it.name
                findViewById<TextView>(R.id.tvId).text = it.id
                findViewById<TextView>(R.id.tvPhone).text = it.phone
                findViewById<TextView>(R.id.tvAddress).text = it.address
                findViewById<CheckBox>(R.id.cbChecked).isChecked = it.isChecked
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

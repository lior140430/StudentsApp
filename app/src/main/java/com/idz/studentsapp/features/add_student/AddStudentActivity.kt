package com.idz.studentsapp.features.add_student

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.idz.studentsapp.R
import com.idz.studentsapp.dao.StudentsRepository
import com.idz.studentsapp.models.Student

class AddStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        
        // Setup toolbar with back button
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        val etName = findViewById<EditText>(R.id.etName)
        val etId = findViewById<EditText>(R.id.etId)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etAddress = findViewById<EditText>(R.id.etAddress)
        val cbChecked = findViewById<CheckBox>(R.id.cbChecked)
        
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnCancel = findViewById<Button>(R.id.btnCancel)
        
        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val id = etId.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val address = etAddress.text.toString().trim()
            val isChecked = cbChecked.isChecked
            
            if (name.isEmpty() || id.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (StudentsRepository.getStudentById(id) != null) {
                Toast.makeText(this, "Student ID already exists", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            val student = Student(id, name, phone, address, isChecked)
            StudentsRepository.addStudent(student)
            
            Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show()
            finish()
        }
        
        btnCancel.setOnClickListener {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

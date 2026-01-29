package com.idz.studentsapp.features.edit_student

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import com.idz.studentsapp.R
import com.idz.studentsapp.base.Constants
import com.idz.studentsapp.dao.StudentsRepository
import com.idz.studentsapp.models.Student
import com.idz.studentsapp.features.students_list.StudentsListActivity

class EditStudentActivity : AppCompatActivity() {
    
    private var studentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)
        
        // Setup toolbar with back button
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        studentId = intent.getStringExtra(Constants.STUDENT_ID_KEY)
        
        val etName = findViewById<EditText>(R.id.etName)
        val etId = findViewById<EditText>(R.id.etId)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etAddress = findViewById<EditText>(R.id.etAddress)
        val cbChecked = findViewById<CheckBox>(R.id.cbChecked)
        
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnCancel = findViewById<Button>(R.id.btnCancel)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        
        loadStudentData(etName, etId, etPhone, etAddress, cbChecked)
        
        btnCancel.setOnClickListener {
            finish()
        }
        
        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val newId = etId.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val address = etAddress.text.toString().trim()
            val isChecked = cbChecked.isChecked

            if (name.isEmpty() || newId.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (studentId != newId && StudentsRepository.getStudentById(newId) != null) {
                Toast.makeText(this, "Another student with this ID already exists", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedStudent = Student(newId, name, phone, address, isChecked)
            studentId?.let { originalId ->
                StudentsRepository.updateStudent(originalId, updatedStudent)
            }

            Toast.makeText(this, "Student updated successfully", Toast.LENGTH_SHORT).show()
            
            val intent = Intent(this, StudentsListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
        
        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete Student")
                .setMessage("Are you sure you want to delete this student?")
                .setPositiveButton("Yes") { _, _ ->
                    studentId?.let {
                        StudentsRepository.deleteStudent(it)
                        Toast.makeText(this, "Student deleted successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, StudentsListActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }
                }
                .setNegativeButton("No", null)
                .show()
        }
    }
    
    private fun loadStudentData(etName: EditText, etId: EditText, etPhone: EditText, etAddress: EditText, cbChecked: CheckBox) {
        studentId?.let { id ->
            val student = StudentsRepository.getStudentById(id)
            student?.let {
                etName.setText(it.name)
                etId.setText(it.id)
                etPhone.setText(it.phone)
                etAddress.setText(it.address)
                cbChecked.isChecked = it.isChecked
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

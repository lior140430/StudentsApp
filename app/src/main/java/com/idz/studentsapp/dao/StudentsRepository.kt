package com.idz.studentsapp.dao

import com.idz.studentsapp.models.Student

object StudentsRepository {
    private val students = mutableListOf<Student>(

    )

    fun getStudents(): List<Student> = students

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun updateStudent(oldId: String, updatedStudent: Student) {
        val index = students.indexOfFirst { it.id == oldId }
        if (index >= 0) {
            students[index] = updatedStudent
        }
    }

    fun deleteStudent(id: String) {
        students.removeIf { it.id == id }
    }

    fun getStudentById(id: String): Student? {
        return students.find { it.id == id }
    }

    fun updateStudentCheckStatus(id: String, isChecked: Boolean) {
        val student = getStudentById(id)
        student?.let {
            it.isChecked = isChecked
        }
    }
}

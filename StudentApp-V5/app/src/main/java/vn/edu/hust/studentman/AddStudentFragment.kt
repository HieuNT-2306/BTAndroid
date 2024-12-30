package vn.edu.hust.studentman

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class AddStudentFragment : Fragment(R.layout.fragment_add_student) {

    private lateinit var nameEditText: EditText
    private lateinit var idEditText: EditText
    private lateinit var addButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views with their specific types
        nameEditText = view.findViewById<EditText>(R.id.edit_student_name)
        idEditText = view.findViewById<EditText>(R.id.edit_student_id)
        addButton = view.findViewById<Button>(R.id.btn_add_student)

        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val studentid = idEditText.text.toString()
            if (name.isNotBlank() && studentid.isNotBlank()) {
                val newStudent = StudentModel(studentName = name, studentId =  studentid)
                (activity as MainActivity).addStudent(newStudent)
                fragmentManager?.popBackStack()
            }
        }
    }
}


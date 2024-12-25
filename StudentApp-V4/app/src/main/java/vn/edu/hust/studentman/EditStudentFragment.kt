package vn.edu.hust.studentman

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class EditStudentFragment : Fragment(R.layout.fragment_edit_student) {
    private lateinit var nameEditText: EditText
    private lateinit var idEditText: EditText
    private lateinit var updateButton: Button

    companion object {
        const val ARG_STUDENT = "arg_student"
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameEditText = view.findViewById(R.id.edit_student_name)
        idEditText = view.findViewById(R.id.edit_student_id)
        updateButton = view.findViewById(R.id.btn_update_student)

        val student = arguments?.getSerializable(ARG_STUDENT) as StudentModel
        nameEditText.setText(student.studentName)
        idEditText.setText(student.studentId)

        updateButton.setOnClickListener {
            student.studentName = nameEditText.text.toString()
            student.studentId = idEditText.text.toString()

            (activity as MainActivity).updateStudent(student)

            fragmentManager?.popBackStack()
        }
    }
}
private fun Any.notifyDataSetChanged() {
    TODO("Not yet implemented")
}

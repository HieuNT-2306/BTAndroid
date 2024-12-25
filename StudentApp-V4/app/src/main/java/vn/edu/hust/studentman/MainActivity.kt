package vn.edu.hust.studentman

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

  private lateinit var databaseHelper: DatabaseHelper
  private val students = mutableListOf<StudentModel>()
  private lateinit var studentAdapter: StudentAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Initialize the database helper
    databaseHelper = DatabaseHelper(this)

    // Load students from the database
    students.clear()
    students.addAll(databaseHelper.getAllStudents())

    // Set up the adapter for the ListView
    studentAdapter = StudentAdapter(
      this,
      students,
      onEditClick = { student ->
        openEditStudentFragment(student)
      },
      onRemoveClick = { student ->
        databaseHelper.deleteStudent(student.studentId)
        students.remove(student)
        studentAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Removed ${student.studentName}", Toast.LENGTH_SHORT).show()
      }
    )

    // Set up ListView
    val listView = findViewById<ListView>(R.id.list_view_students)
    listView.adapter = studentAdapter

    // Set up Toolbar
    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)

    // List item click listener
    listView.setOnItemClickListener { _, _, position, _ ->
      val student = students[position]
      openEditStudentFragment(student)
    }

    // Button to add student (if needed)

  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu) // Inflate your main_menu
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_add -> {
        openAddStudentFragment() // Open Add Student Fragment
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  fun addStudent(newStudent: StudentModel) {
    databaseHelper.addStudent(newStudent)
    students.add(newStudent)
    studentAdapter.notifyDataSetChanged()
  }

  fun updateStudent(student: StudentModel) {
    databaseHelper.updateStudent(student)
    studentAdapter.notifyDataSetChanged()
  }

  fun openEditStudentFragment(student: StudentModel) {
    val fragment = EditStudentFragment().apply {
      arguments = Bundle().apply {
        putSerializable(EditStudentFragment.ARG_STUDENT, student)
      }
    }
    supportFragmentManager.beginTransaction()
      .replace(R.id.container, fragment)
      .addToBackStack(null) // Allows back navigation
      .commit()
  }

  fun openAddStudentFragment() {
    val fragment = AddStudentFragment()
    supportFragmentManager.beginTransaction()
      .replace(R.id.container, fragment)
      .addToBackStack(null) // Allows back navigation
      .commit()
  }
}
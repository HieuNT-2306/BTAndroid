package vn.edu.hust.studentman

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

  private lateinit var studentDao: StudentDao
  private val students = mutableListOf<StudentModel>()
  private lateinit var studentAdapter: StudentAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Initialize Room database and DAO
    val database = AppDatabase.getDatabase(this)
    studentDao = database.studentDao()

    // Load students from the database
    lifecycleScope.launch {
      students.clear()
      students.addAll(studentDao.getAllStudents())
      setupAdapter()
    }

    // Set up Toolbar
    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)
  }

  private fun setupAdapter() {
    // Set up the adapter for the ListView
    studentAdapter = StudentAdapter(
      this,
      students,
      onEditClick = { student ->
        openEditStudentFragment(student)
      },
      onRemoveClick = { student ->
        lifecycleScope.launch {
          studentDao.deleteStudent(student)
          students.remove(student)
          studentAdapter.notifyDataSetChanged()
          Toast.makeText(this@MainActivity, "Removed ${student.studentName}", Toast.LENGTH_SHORT).show()
        }
      }
    )

    // Set up ListView
    val listView = findViewById<ListView>(R.id.list_view_students)
    listView.adapter = studentAdapter

    // List item click listener
    listView.setOnItemClickListener { _, _, position, _ ->
      val student = students[position]
      openEditStudentFragment(student)
    }
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
    lifecycleScope.launch {
      studentDao.addStudent(newStudent)
      students.add(newStudent)
      studentAdapter.notifyDataSetChanged()
    }
  }

  fun updateStudent(student: StudentModel) {
    lifecycleScope.launch {
      studentDao.updateStudent(student)
      studentAdapter.notifyDataSetChanged()
    }
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

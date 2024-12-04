package vn.edu.hust.studentman

import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

  val students = mutableListOf(
    StudentModel("Nguyễn Văn An", "SV001"),
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Nguyễn Trung Hiếu", "20215578"),
  )

  lateinit var studentAdapter: StudentAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Set up the toolbar
    val toolbar: Toolbar = findViewById(R.id.toolbar)
    setSupportActionBar(toolbar)

    studentAdapter = StudentAdapter(
      this,
      students,
      onEditClick = { student ->
        openEditStudentFragment(student)
      },
      onRemoveClick = { student ->
        students.remove(student)
        studentAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Removed ${student.studentName}", Toast.LENGTH_SHORT).show()
      }
    )

    val listView = findViewById<ListView>(R.id.list_view_students)
    listView.adapter = studentAdapter

    registerForContextMenu(listView)

    listView.setOnItemClickListener { _, _, position, _ ->
      val student = students[position]
      openEditStudentFragment(student)
    }
  }

  private fun openAddStudentFragment() {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.container, AddStudentFragment())
    transaction.addToBackStack(null)
    transaction.commit()
  }

  private fun openEditStudentFragment(student: StudentModel) {
    val transaction = supportFragmentManager.beginTransaction()
    val fragment = EditStudentFragment.newInstance(student)
    transaction.replace(R.id.container, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }

  fun addStudent(newStudent: StudentModel) {
    students.add(newStudent)
    studentAdapter.notifyDataSetChanged() // Refresh ListView
  }

  override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menu.setHeaderTitle("Options")
    menu.add(0, 0, 0, "Edit")
    menu.add(0, 1, 1, "Remove")
  }

  override fun onContextItemSelected(item: MenuItem): Boolean {
    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
    val student = students[info.position]

    when (item.itemId) {
      0 -> {
        openEditStudentFragment(student)
        return true
      }
      1 -> {
        students.remove(student)
        studentAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Removed ${student.studentName}", Toast.LENGTH_SHORT).show()
        return true
      }
    }
    return super.onContextItemSelected(item)
  }

  // Inflate the menu for the toolbar
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  // Handle menu item clicks
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_add -> {
        openAddStudentFragment()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}

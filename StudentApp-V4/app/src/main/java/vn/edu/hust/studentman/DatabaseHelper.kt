package vn.edu.hust.studentman

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// SQLiteHelper for managing the database
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "students.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "students"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_STUDENT_ID = "student_id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_STUDENT_ID TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addStudent(student: StudentModel): Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, student.studentName)
        values.put(COLUMN_STUDENT_ID, student.studentId)
        return db.insert(TABLE_NAME, null, values)
    }

    fun getAllStudents(): MutableList<StudentModel> {
        val students = mutableListOf<StudentModel>()
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val studentId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_ID))
            students.add(StudentModel(name, studentId))
        }
        cursor.close()
        return students
    }

    fun updateStudent(student: StudentModel): Int {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, student.studentName)
        values.put(COLUMN_STUDENT_ID, student.studentId)
        return db.update(TABLE_NAME, values, "$COLUMN_STUDENT_ID = ?", arrayOf(student.studentId))
    }

    fun deleteStudent(studentId: String): Int {
        val db = writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_STUDENT_ID = ?", arrayOf(studentId))
    }
}

package vn.edu.hust.studentman

import androidx.room.*

@Dao
interface StudentDao {
    @Insert
    suspend fun addStudent(student: StudentModel)

    @Query("SELECT * FROM students")
    suspend fun getAllStudents(): List<StudentModel>

    @Update
    suspend fun updateStudent(student: StudentModel)

    @Delete
    suspend fun deleteStudent(student: StudentModel)
}

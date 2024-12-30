package vn.edu.hust.studentman

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "students")
data class StudentModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var studentName: String,
    var studentId: String
) : Serializable

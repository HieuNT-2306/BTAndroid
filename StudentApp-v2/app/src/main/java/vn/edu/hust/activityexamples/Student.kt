package vn.edu.hust.activityexamples

data class Student(val fullName: String, val studentId: String) {
    override fun toString(): String = "$fullName \nID: $studentId"
}

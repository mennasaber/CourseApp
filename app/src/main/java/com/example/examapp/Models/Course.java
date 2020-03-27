package com.example.examapp.Models;

public class Course {
    String courseName;
    String courseID;
    String teacherID;

    public Course() {
    }

    public Course(String courseName, String courseID, String teacherID) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.teacherID = teacherID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }
}

package com.example.examapp.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.examapp.Models.Course;
import com.example.examapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AddCourseActivity extends AppCompatActivity implements View.OnClickListener {
    EditText courseNameET;
    Button addCourseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        courseNameET = findViewById(R.id.courseNameET);
        addCourseButton = findViewById(R.id.addCourseButton);
        addCourseButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addCourseButton && !courseNameET.getText().toString().trim().equals("")) {
            String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
            Course course = new Course();
            course.setTeacherID(userID);
            DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("Courses");
            course.setCourseName(courseNameET.getText().toString().trim());
            String courseID = mDatabaseReference.push().getKey();
            course.setCourseID(courseID);
            assert courseID != null;
            mDatabaseReference.child(courseID).setValue(course);
            Toast.makeText(this, course.getCourseName() + " is Added Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
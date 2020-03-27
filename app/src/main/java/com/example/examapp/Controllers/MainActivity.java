package com.example.examapp.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.examapp.Adapters.CoursesAdapter;
import com.example.examapp.Models.Course;
import com.example.examapp.Models.User;
import com.example.examapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    static User user;
    ArrayList<Course> courseArrayList;
    CoursesAdapter coursesAdapter;
    RecyclerView courseRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = new User();
        courseArrayList = new ArrayList<>();
        coursesAdapter = new CoursesAdapter(this, courseArrayList);

        String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        user.setId(userID);

        // Set RecyclerView Adapter
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        courseRecyclerView = findViewById(R.id.coursesRecyclerview);
        courseRecyclerView.setLayoutManager(mLayoutManager);
        courseRecyclerView.setItemAnimator(new DefaultItemAnimator());
        courseRecyclerView.setAdapter(coursesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signOut:
                signOut();
                break;
            case R.id.addCourse:
                AddCourse();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void AddCourse() {
        Intent intent = new Intent(MainActivity.this, AddCourseActivity.class);
        startActivity(intent);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        getUserData();
    }

    private void getTeacherCourses() {
        // Get teacher courses
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("Courses");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courseArrayList.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Course course = d.getValue(Course.class);
                    assert course != null;
                    if (course.getTeacherID().equals(user.getId())) {
                        courseArrayList.add(course);
                    }
                }
                coursesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getUserData() {
        // Get User Data
        DatabaseReference mDatabaseReferenceUser = FirebaseDatabase.getInstance().getReference("Users").child(user.getId());
        mDatabaseReferenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                assert user != null;
                if (user.isTeacher())
                    getTeacherCourses();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
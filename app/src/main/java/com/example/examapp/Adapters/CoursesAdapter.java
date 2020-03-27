package com.example.examapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examapp.Models.Course;
import com.example.examapp.R;

import java.util.ArrayList;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder> {

    Context context;
    ArrayList<Course> courseArrayList;

    public CoursesAdapter(Context context, ArrayList<Course> courseArrayList) {
        this.context = context;
        this.courseArrayList = courseArrayList;
    }

    @NonNull
    @Override
    public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);
        return new CoursesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesViewHolder holder, int position) {
        Course currentCourse = courseArrayList.get(position);
        holder.courseNameTV.setText(currentCourse.getCourseName());
    }

    @Override
    public int getItemCount() {
        return courseArrayList.size();
    }

    public static class CoursesViewHolder extends RecyclerView.ViewHolder {
        TextView courseNameTV;

        public CoursesViewHolder(@NonNull View itemView) {
            super(itemView);
            courseNameTV = itemView.findViewById(R.id.courseNameTV);
        }
    }
}

package com.example.examapp.Models;

public class User {
    String username , id , image , email ;
    boolean isTeacher ;

    public User() {}

    public User(String username, String id, String image, String email, boolean isTeacher) {
        this.username = username;
        this.id = id;
        this.image = image;
        this.email = email;
        this.isTeacher = isTeacher;
    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

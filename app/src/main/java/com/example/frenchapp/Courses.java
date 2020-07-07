package com.example.frenchapp;

public class Courses {

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public Courses(String courseId, String courseTitle, String courseType) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseType = courseType;
    }

    private String courseId;
    private String courseTitle;
    private String courseType;
}

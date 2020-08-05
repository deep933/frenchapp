package com.example.frenchapp;

public class LessonsList {

    public String getLessontitle() {
        return Lessontitle;
    }

    public void setLessontitle(String lessontitle) {
        Lessontitle = lessontitle;
    }

    public String getLessonUrl() {
        return LessonUrl;
    }

    public void setLessonUrl(String lessonUrl) {
        LessonUrl = lessonUrl;
    }

    public LessonsList() {
    }

    public LessonsList(String lessontitle, String lessonUrl) {
        Lessontitle = lessontitle;
        LessonUrl = lessonUrl;
    }

    private String Lessontitle;
    private String LessonUrl;
}

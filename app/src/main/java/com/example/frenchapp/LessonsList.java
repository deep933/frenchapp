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

    public LessonsList(String lessontitle, String lessonUrl,String lessontype) {
        Lessontitle = lessontitle;
        LessonUrl = lessonUrl;
        type = lessontype;
    }

    private String Lessontitle;
    private String LessonUrl;

    public String getType() {
        return type;
    }

    public void setType(String lessontype) {
        type = lessontype;
    }

    private String type;
}

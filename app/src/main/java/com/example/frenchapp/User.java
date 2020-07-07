package com.example.frenchapp;

public class User {

    public User(){

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }



    private String name;
    private String email;
    private String user_id;

    public User(String name, String email, String user_id, String sub_type) {
        this.name = name;
        this.email = email;
        this.user_id = user_id;
        this.sub_type = sub_type;
    }

    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    private String sub_type;




}

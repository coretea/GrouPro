package com.example.groupro;

public class User {
    private String name;
    private String email;
    private String password;

    public User(){
    // empty constructor
    }

    public User(String email, String name, String pass) {
        this.email = email;
        this.name = name;
        this.password = pass;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPassword(String pass){
        this.password = pass;
    }

    public String getEmail(){
        return this.email;
    }

    public String getName(){
        return this.name;
    }

    public String getPassword(){
        return this.password;
    }
}

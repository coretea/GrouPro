package com.example.groupro;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {
    private String name;
    private String email;
    private String uid;


    public User(){
    // empty constructor
    }

    public User(String email, String name, String uid) {
        this.email = email;
        this.name = name;
        this.uid = uid;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return this.email;
    }

    public String getName(){
        return this.name;
    }

    public String getUid() { return this.uid; }

    public void addUserToDB(User user)
    {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.child(user.getUid() ).setValue(user);
    }
}

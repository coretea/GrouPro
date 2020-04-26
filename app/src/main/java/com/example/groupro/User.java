package com.example.groupro;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class User{
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

    public void setUid(String uid) {
        this.uid = uid;
    }


    public void addUserToDB(User user)
    {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.child(user.getUid() ).setValue(user);
    }

   static public ArrayList<User> getUserList()
    {
        final ArrayList<User> userlist = new ArrayList<User>();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        userlist.add(singleSnapshot.getValue(User.class));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return userlist;
    }
}

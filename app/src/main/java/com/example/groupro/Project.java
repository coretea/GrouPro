package com.example.groupro;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Project implements Serializable {

    private String name;
    private  String manager;
    private int budget;
    ArrayList<String> users = new ArrayList<String>();
    private String creationDate;
    private ArrayList<Assignment> assignments;
    private String status;
    private String description;

    public Project()
    {
        //empty constructor
    }

    public Project(String name,String manager, int budget, ArrayList<String> users, String status, String description)
    {
        this.status = status;
        Calendar calendar = Calendar.getInstance();
        this.creationDate =  DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        this.name = name;
        this.manager = manager;
        this.budget = budget;
        this.users = users;
        assignments = new ArrayList<Assignment>();
        Assignment asgmt = new Assignment("ExampleUser", "Example", "ExampleDate");
        assignments.add(asgmt);
        this.description = description;
    }

    // getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getManager() {
        return manager;
    }
    public void setManager(String manager) {
        this.manager = manager;
    }
    public double getBudget() {
        return budget;
    }
    public void setBudget(int budget) {
        this.budget = budget;
    }
    public ArrayList<String> getUsers() {
        return users;
    }
    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }
    public String getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }
    public void setAssignments(ArrayList<Assignment> assignments) {
        this.assignments = assignments;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    // adds project to database
    public void addProjectToDB(Project proj)
    {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("projects");
        mDatabase.child(proj.getName()).setValue(proj);
    }

    // add an assignments
    public void addAssigment(Assignment asm)
    {
        assignments.add(asm);
    }
    //remove assigment
    public void removeAssigment(Assignment asm)
    {
        assignments.remove(asm);
    }

}



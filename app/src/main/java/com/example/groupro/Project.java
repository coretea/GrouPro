package com.example.groupro;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Project {

    private String name;
    private  String manager;
    private double budget;
    ArrayList<String> users = new ArrayList<String>();
    public Calendar calendar = Calendar.getInstance();
    private String creationDate;
    private ArrayList<Assignment> assignments;


    public Project(String name,String manager, double budget, ArrayList<String> users)
    {
        this.creationDate =  DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        this.name = name;
        this.manager = manager;
        this.budget = budget;
        this.users = users;
        assignments = new ArrayList<Assignment>();
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
    public void setBudget(double budget) {
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

package com.example.groupro;

public class Assignment
{
    String asignee;
    double budget;
    String content;
    String due;

    public Assignment()
    {
        //empty constructor
    }

    public Assignment(String asignee, double budget, String content, String due) {
        this.asignee = asignee;
        this.budget = budget;
        this.content = content;
        this.due = due;
    }

    public String getAsignee() {
        return asignee;
    }

    public void setAsignee(String asignee) {
        this.asignee = asignee;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }



}

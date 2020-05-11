package com.groupro.groupro;

import java.io.Serializable;

/**
 * this class represents an Assignment inside a project.
 *  Each project has a List of Assignment
 */
public class Assignment implements Serializable
{
    String asignee;
    String content;
    String due;

    public Assignment()
    {
        //empty constructor
    }

    /**
     * A constructor to Assignments
     * @param asignee
     * @param content
     * @param due
     */
    public Assignment(String asignee, String content, String due) {
        this.asignee = asignee;
        this.content = content;
        this.due = due;
    }

    // getters and setters
    
    public String getAsignee() {
        return asignee;
    }

    public void setAsignee(String asignee) {
        this.asignee = asignee;
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

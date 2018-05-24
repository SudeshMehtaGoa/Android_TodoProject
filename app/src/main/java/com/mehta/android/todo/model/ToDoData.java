package com.mehta.android.todo.model;

public class ToDoData  {

    private int toDoID;
    private String toDoDate;
    private String toDoName;
    private String toDoDescription;
    private int toDoStatus;

    public ToDoData()
    {

    }
    public ToDoData(String toDoDate, String toDoName, String toDoDescription , int toDoStatus) {

        this.toDoDate = toDoDate;
        this.toDoName = toDoName;
        this.toDoDescription = toDoDescription;
        this.toDoStatus = toDoStatus;
    }

    public int getToDoID() {
        return toDoID;
    }

    public void setToDoID(int toDoID) {
        this.toDoID = toDoID;
    }

    public String getToDoDate() {
        return toDoDate;
    }

    public void setToDoDate(String toDoDate) {
        this.toDoDate = toDoDate;
    }

    public String getToDoName() {
        return toDoName;
    }

    public void setToDoName(String toDoName) {
        this.toDoName = toDoName;
    }

    public String getToDoDescription() {
        return toDoDescription;
    }

    public void setToDoDescription(String toDoDescription) {
        this.toDoDescription = toDoDescription;
    }

    public int getToDoStatus() {
        return toDoStatus;
    }

    public void setToDoStatus(int toDoStatus) {
        this.toDoStatus = toDoStatus;
    }
}

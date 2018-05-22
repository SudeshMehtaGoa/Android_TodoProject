package com.mehta.android.todo;

public class RowItem {

	private String todo_Date;
	private String todo_Name;
	private String todo_Description;
	private int todo_Status;

	public RowItem(String todo_Date, String todo_Name, String todo_Description , int todo_Status) {

		this.todo_Date = todo_Date;
		this.todo_Name = todo_Name;
		this.todo_Description = todo_Description;
		this.todo_Status = todo_Status;
	}

	public String gettodo_Date() {
		return todo_Date;
	}

	public void settodo_Date(String todo_Date) {
		this.todo_Date = todo_Date;
	}

	public String gettodo_Name() {
		return todo_Name;
	}

	public void settodo_Name(String todo_Name) {
		this.todo_Name = todo_Name;
	}

	public String gettodo_Description() {
		return todo_Description;
	}

	public void settodo_Description(String todo_Description) {
		this.todo_Description = todo_Description;
	}

	public int gettodo_Status() {
		return todo_Status;
	}

	public void settodo_Status(int todo_Status) {
		this.todo_Status = todo_Status;
	}



}
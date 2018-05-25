package com.mehta.android.todo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

// Dialog for new tasks or to modify tasks.

public class todoDiaglog extends AppCompatDialogFragment {

    private EditText editToDoName;
    private EditText editTodoDescription;
    private CalendarView editToDoDate;
    private todoDiaglogListner todoListner;
    private String strDateCalender;
    private View view;

    public static todoDiaglog newInstance(String strDialogTitle, int intToDoID, String strToDoName, String strToDoDescription, String strToDoDate){
        todoDiaglog dlg = new todoDiaglog();
        Bundle args = new Bundle();
        args.putString("strDialogTitle",strDialogTitle);
        args.putInt("intToDoID",intToDoID);
        args.putString("strToDoName",strToDoName);
        args.putString("strToDoDescription",strToDoDescription);
        args.putString("strToDoDate",strToDoDate);
        dlg.setArguments(args);
        return dlg;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        view = inflater.inflate(R.layout.diaglog_newtodo,null);

        builder.setView(view)
                .setTitle(getArguments().getString("strDialogTitle"))
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strToDoAddName = editToDoName.getText().toString();
                        String strToDoAddDescription = editTodoDescription.getText().toString();

                        if(strToDoAddName.length()>=5 && strToDoAddDescription.length()>=5) {
                            todoListner.insertOrUpdateTodo(getArguments().getInt("intToDoID"), strToDoAddName, strToDoAddDescription,
                                    strDateCalender);
                        }

                    }
                });


        // task name edit box with validation
        editToDoName = view.findViewById(R.id.toDoAddName);
        editToDoName.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(editToDoName.getText().length()<5){
                    editToDoName.setError("Task Name minimum legth is 5 Characters.");
                }
            }
        });

        // task description edit box with validation
        editTodoDescription = view.findViewById(R.id.toDoAddDescription);
        editTodoDescription.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(editTodoDescription.getText().length()<5){
                    editTodoDescription.setError("Task Description minimum legth is 5 Characters.");
                }
            }
        });

        // calender control - using simple dateformat to convert date from string to long and vice versa
        editToDoDate = view.findViewById(R.id.toDoAddDate);

        editToDoDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month=month+1;
                strDateCalender = year + "-" + (((month < 10) ? "0" : "") + month) + "-" + (((dayOfMonth < 10) ? "0" : "") + dayOfMonth);
            }
        });

        editToDoName.setText(getArguments().getString("strToDoName"));
        editTodoDescription.setText(getArguments().getString("strToDoDescription"));

        long taskDate=0;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            strDateCalender = getArguments().getString("strToDoDate");
            Date date = sdf.parse(strDateCalender);
            taskDate = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        editToDoDate.setDate(taskDate);

        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            todoListner = (todoDiaglogListner) context;
        } catch (ClassCastException e) {
           throw new ClassCastException(context.toString() + " must implement todoDiaglogListner"  );
        }

    }

    // listner to caller so that caller can execute db operations.
    public interface todoDiaglogListner {

        void insertOrUpdateTodo(int intToDoID, String strToDoAddName, String strToDoAddDescription , String strToDoAddDate);
    }
}

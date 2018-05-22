package com.mehta.android.todo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


public class todoDiaglog extends AppCompatDialogFragment {

    private EditText editToDoName;
    private EditText editTodoDescription;
    private todoDiaglogListner todoListner;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.diaglog_newtodo,null);

        builder.setView(view)
                .setTitle("New ToDo")
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
                        todoListner.addNewTodo(strToDoAddName,strToDoAddDescription);
                    }
                });


        editToDoName = view.findViewById(R.id.toDoAddName);
        editTodoDescription = view.findViewById(R.id.toDoAddDescription);

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

    public interface todoDiaglogListner {

        void addNewTodo(String strToDoAddName, String strToDoAddDescription);
    }
}

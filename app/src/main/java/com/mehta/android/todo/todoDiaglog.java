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

    public static todoDiaglog newInstance(String strDialogTitle, int intToDoID, String strToDoName, String strToDoDescription){
        todoDiaglog dlg = new todoDiaglog();
        Bundle args = new Bundle();
        args.putString("strDialogTitle",strDialogTitle);
        args.putInt("intToDoID",intToDoID);
        args.putString("strToDoName",strToDoName);
        args.putString("strToDoDescription",strToDoDescription);
        dlg.setArguments(args);
        return dlg;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.diaglog_newtodo,null);

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
                        todoListner.insertOrUpdateTodo(getArguments().getInt("intToDoID"),strToDoAddName,strToDoAddDescription);
                    }
                });


        editToDoName = view.findViewById(R.id.toDoAddName);
        editTodoDescription = view.findViewById(R.id.toDoAddDescription);

        editToDoName.setText(getArguments().getString("strToDoName"));
        editTodoDescription.setText(getArguments().getString("strToDoDescription"));

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

        void insertOrUpdateTodo(int intToDoID, String strToDoAddName, String strToDoAddDescription);
    }
}

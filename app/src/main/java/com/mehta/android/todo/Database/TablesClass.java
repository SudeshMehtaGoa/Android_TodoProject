package com.mehta.android.todo.Database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mehta.android.todo.utils.Constants;

public class TablesClass extends SQLiteOpenHelper {
    Context context;

    /**
     * Write all create table statements here in this class on oncreate method
     * If any changes in table structure go for onUpgrade method
     */

    public TablesClass(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String ToDoTable = "CREATE TABLE IF NOT EXISTS " + Constants.ToDo_Table + " ("
                + Constants.ToDo_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constants.ToDo_Name + " TEXT, "
                + Constants.ToDo_Description + " TEXT, "
                + Constants.ToDo_DATE + " TEXT, "
                + Constants.ToDo_Status + " INTEGER) ";

        db.execSQL(ToDoTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        context.deleteDatabase(Constants.DATABASE_NAME);
        onCreate(db);
    }
}

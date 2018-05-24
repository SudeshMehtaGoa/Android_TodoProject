package com.mehta.android.todo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Debug;
import android.widget.Toast;

import com.mehta.android.todo.model.ToDoData;
import com.mehta.android.todo.utils.Constants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
//import com.mehta.android.todo.utils.Constants;

public class DBHelper {

    private SQLiteDatabase db;
    private final Context context;
    private final TablesClass dbHelper;
    public static int no;
    public static DBHelper db_helper = null;

    /*
     * set context of the class and initialize AppData Object
     */

    public DBHelper(Context c) {
        context = c;
        dbHelper = new TablesClass(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    public static DBHelper getInstance(Context context){
        try{
            if(db_helper == null){
                db_helper = new DBHelper(context);
                db_helper.open();
            }
        }catch(IllegalStateException e){
            //db_helper already open
        }
        return db_helper;
    }

    /*
     * close databse.
     */
    public void close() {
        if (db.isOpen()) {
            db.close();
        }
    }

    public boolean dbOpenCheck() {
        try{
            return db.isOpen();
        }catch(Exception e){
            return false;
        }
    }

    /*
     * open database
     */
    public void open() throws SQLiteException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            // Log.v("open database Exception", "error==" + e.getMessage());
            db = dbHelper.getReadableDatabase();
        }
    }

    public long insertContentVals(String tableName, ContentValues content){
        long id=0;
        try{
            id = db.insert(tableName, null, content);
        }catch(Exception e){
            e.printStackTrace();
        }
        return id;
    }

    public Cursor getTableRecords(String tablename, String[] columns, String where, String orderby){
        Cursor cursor =  db.query(false, tablename, columns,where, null, null, null, orderby, null);
        return cursor;
    }

    /*
     * Get count of all the records in a table as per the condition
     */

    public int getFullCount(String table, String where) {
        Cursor cursor = db.query(false, table, null, where, null, null, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
                no = cursor.getCount();
                cursor.close();
            }

        }
        finally {
            cursor.close();
        }
        return no;
    }

    public void deleteRecords(String table, String whereClause, String[] whereArgs){
        db.delete(table, whereClause, whereArgs);
    }

    /*
     * Get value of any table as per the condition.
     */

    public String getValue(String table, String column, String where) {
        Cursor result = db.query(false, table, new String[] { column }, where,
                null, null, null, Constants.ToDo_ID, null);
        String value = "";
        try {
            if (result.moveToFirst()) {
                value = result.getString(0);
            } else {
                return null;
            }
        } finally {
            result.close();
        }
        return value;
    }

    /*
     * Get Multiple Values from column of any specified table.
     */

    public String[] getValues(boolean b, String table, String column,
                              String where, String orderBy) {
        ArrayList<String> savedAns = new ArrayList<String>();
        Cursor result = null;
        String[] y;
        try {
            result = db.query(b, table, new String[] { column }, where, null,
                    null, null, orderBy, null);
            if (result.moveToFirst()) {
                do {
                    savedAns.add(result.getString(result.getColumnIndex(column)));
                } while (result.moveToNext());
            } else {
                return null;
            }
            y = savedAns.toArray(new String[result.getCount()]);
        } finally {
            result.close();
        }
        return y;
    }

    public int updateRecords(String table, ContentValues values,
                             String whereClause, String[] whereArgs) {
        int a = db.update(table, values, whereClause, whereArgs);
        return a;
    }

    public List<ToDoData> getAllToDoRecords() {
        List<ToDoData> ToDoAllData = new LinkedList<ToDoData>();

        // select ToDo query
        String query = "SELECT  * FROM " + Constants.ToDo_Table;

        // get reference of the ToDo database
        Cursor cursor = db.rawQuery(query, null);
        //Cursor c= db.rawQuery(query,null );

        // parse all results
        ToDoData todo_record = null;
        if (cursor.moveToFirst()) {
            do {
                todo_record = new ToDoData();
                todo_record.setToDoID((cursor.getInt(0)));
                todo_record.setToDoName(cursor.getString(1));
                todo_record.setToDoDescription(cursor.getString(2));
                todo_record.setToDoDate(cursor.getString(3));
                todo_record.setToDoStatus(cursor.getInt(4));

                // Add book to books
                ToDoAllData.add(todo_record);
            } while (cursor.moveToNext());
        }
        return ToDoAllData;
    }
}


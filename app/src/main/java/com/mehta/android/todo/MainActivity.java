package com.mehta.android.todo;


import android.content.ContentValues;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.mehta.android.todo.Database.DBHelper;
import com.mehta.android.todo.model.ToDoData;
import com.mehta.android.todo.utils.CommonUtilities;
import com.mehta.android.todo.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements  AdapterView.OnItemClickListener ,
        AdapterView.OnItemLongClickListener,
        todoDiaglog.todoDiaglogListner {
    //NavigationView.OnNavigationItemSelectedListener ,


    ////////////////////////////////////////////////////////////////////
    List<ToDoData> ToDoAllData;
    DBHelper dbHelper;

    String[] todo_Date;
    String[] todo_Name;
    String[] todo_Description;
    int[] todo_Status;
    //TypedArray todo_Status;

    ListView ToDoListView;
    Menu ToDoMenu;

    boolean blnCompleteFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Add Toolbar
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);



            todo_Date = getResources().getStringArray(R.array.todo_Date);
            todo_Name = getResources().getStringArray(R.array.todo_Name);
            todo_Description = getResources().getStringArray(R.array.todo_Description);
            todo_Status = getResources().getIntArray(R.array.todo_Status);
            //      todo_Status = getResources().obtainTypedArray(R.array.todo_Status);

            dbHelper = CommonUtilities.getDBObject(this);

            int recordCount = dbHelper.getFullCount(Constants.ToDo_Table,null);

            // insert dummy records on first run
            if(recordCount==0){
                insertToDoRecords();
            }

            ToDoListView = findViewById(R.id.todo_List);
            ToDoListView.setOnItemClickListener(this);
            ToDoListView.setOnItemLongClickListener(this);

            ToDoAllData = dbHelper.getAllToDoRecords(blnCompleteFlag);
            CustomAdapter adapter = new CustomAdapter(this, ToDoAllData);
            ToDoListView.setAdapter(adapter);

        }
        catch(Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();

        }
  }

    // insert operation
    private void insertToDoRecords(){
        try {
            for(int i=0; i<todo_Date.length; i++) {
                ContentValues vals = new ContentValues();
                vals.put(Constants.ToDo_DATE, todo_Date[i]);
                vals.put(Constants.ToDo_Name, todo_Name[i]);
                vals.put(Constants.ToDo_Description, todo_Description[i]);
                vals.put(Constants.ToDo_Status,todo_Status[i]);
                dbHelper.insertContentVals(Constants.ToDo_Table, vals);
            }
        }
        catch(Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.ToDoMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_AddNew ) {
            // Add New Diaglog box
            OpenDiaglog();
            return true;
        }
        else if (id == R.id.action_TaskList ) {

            // show list of complete task or incomplete task and toggle between complete and incomplete task phase
            if (blnCompleteFlag)       {
                blnCompleteFlag = false;
                this.ToDoMenu.getItem(1).setIcon(R.drawable.complete);
                ToDoAllData = dbHelper.getAllToDoRecords(false);
                CustomAdapter adapter = new CustomAdapter(this, ToDoAllData);
                ToDoListView.setAdapter(adapter);
            }
            else{
                blnCompleteFlag = true;
                this.ToDoMenu.getItem(1).setIcon(R.drawable.incomplete);
                ToDoAllData = dbHelper.getAllToDoRecords(true);
                CustomAdapter adapter = new CustomAdapter(this, ToDoAllData);
                ToDoListView.setAdapter(adapter);
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void OpenDiaglog() {

        // Insert operation of new task

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");

        todoDiaglog todo_Dialog = todoDiaglog.newInstance("New ToDo", -1,"","",mdformat.format(calendar.getTime()));
        todo_Dialog.show(getSupportFragmentManager(),"ToDo Diaglog");

    }

    public void OpenDiaglogToModify(int position) {

        try{

            // update operation of existing task

            todoDiaglog todo_Dialog = todoDiaglog.newInstance("Update ToDo", ToDoAllData.get(position).getToDoID(),
                    ToDoAllData.get(position).getToDoName(),ToDoAllData.get(position).getToDoDescription(), ToDoAllData.get(position).getToDoDate());
            todo_Dialog.show(getSupportFragmentManager(),"ToDo Diaglog");

        }
        catch(Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        OpenDiaglogToModify(position);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        try {


            if(blnCompleteFlag){
                // When Complete tasks are visible then delete record on long click and refresh list
                dbHelper.deleteRecords(Constants.ToDo_Table,Constants.ToDo_ID + " = " + ToDoAllData.get(position).getToDoID(),null);
                ToDoAllData = dbHelper.getAllToDoRecords(blnCompleteFlag);
                CustomAdapter adapter = new CustomAdapter(this, ToDoAllData);
                ToDoListView.setAdapter(adapter);
                return true;
            }
            else{
                // When inComplete tasks are visible then change status as complete on long click and refresh list
                ContentValues vals = new ContentValues();
                vals.put(Constants.ToDo_Status,1);
                dbHelper.updateRecords(Constants.ToDo_Table, vals,Constants.ToDo_ID + " = " + ToDoAllData.get(position).getToDoID(), null);
                ToDoAllData = dbHelper.getAllToDoRecords(blnCompleteFlag);
                CustomAdapter adapter = new CustomAdapter(this, ToDoAllData);
                ToDoListView.setAdapter(adapter);
                return true;
            }
        }
        catch(Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // event raised from custom diaglog box of add/modify dialog
    @Override
    public void insertOrUpdateTodo(int intToDoID, String strToDoAddName, String strToDoAddDescription , String strToDoAddDate) {
        try{

            if(intToDoID==-1) {
                // Add New record
                ContentValues vals = new ContentValues();
                vals.put(Constants.ToDo_DATE, strToDoAddDate);
                vals.put(Constants.ToDo_Name, strToDoAddName);
                vals.put(Constants.ToDo_Description, strToDoAddDescription);
                vals.put(Constants.ToDo_Status, 0);
                dbHelper.insertContentVals(Constants.ToDo_Table, vals);

                // whenever new record is added show list of incomplete task so that even new record is visible
                blnCompleteFlag = false;
                this.ToDoMenu.getItem(1).setIcon(R.drawable.complete);
                ToDoAllData = dbHelper.getAllToDoRecords(false);
                CustomAdapter adapter = new CustomAdapter(this, ToDoAllData);
                ToDoListView.setAdapter(adapter);
            }
            else {
                ContentValues vals = new ContentValues();
                vals.put(Constants.ToDo_DATE, strToDoAddDate);
                vals.put(Constants.ToDo_Name,strToDoAddName);
                vals.put(Constants.ToDo_Description,strToDoAddDescription);
                dbHelper.updateRecords(Constants.ToDo_Table, vals,Constants.ToDo_ID + " = " + intToDoID, null);
                ToDoAllData = dbHelper.getAllToDoRecords(blnCompleteFlag);
                CustomAdapter adapter = new CustomAdapter(this, ToDoAllData);
                ToDoListView.setAdapter(adapter);
            }
        }
        catch(Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}

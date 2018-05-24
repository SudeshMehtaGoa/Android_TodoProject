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

import com.mehta.android.todo.Database.DBHelper;
import com.mehta.android.todo.model.ToDoData;
import com.mehta.android.todo.utils.CommonUtilities;
import com.mehta.android.todo.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements  AdapterView.OnItemClickListener , todoDiaglog.todoDiaglogListner {
    //NavigationView.OnNavigationItemSelectedListener ,


    ////////////////////////////////////////////////////////////////////
    List<ToDoData> ToDoAllData;
    DBHelper dbHelper;

    String[] todo_Date;
    String[] todo_Name;
    String[] todo_Description;
    TypedArray todo_Status;

    ListView ToDoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {

            todo_Date = getResources().getStringArray(R.array.todo_Date);
            todo_Name = getResources().getStringArray(R.array.todo_Name);
            todo_Description = getResources().getStringArray(R.array.todo_Description);
            todo_Status = getResources().obtainTypedArray(R.array.todo_Status);

            dbHelper = CommonUtilities.getDBObject(this);

            int recordCount = dbHelper.getFullCount(Constants.ToDo_Table,null);

            if(recordCount==0){
                insertToDoRecords();
            }

            ToDoListView = findViewById(R.id.todo_List);
            ToDoListView.setOnItemClickListener(this);

            ToDoAllData = dbHelper.getAllToDoRecords();

            CustomAdapter adapter = new CustomAdapter(this, ToDoAllData);
            ToDoListView.setAdapter(adapter);

        }
        catch(Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();

        }
  }

    private void insertToDoRecords(){
        for(int i=0; i<todo_Date.length; i++) {
            ContentValues vals = new ContentValues();
            vals.put(Constants.ToDo_DATE, todo_Date[i]);
            vals.put(Constants.ToDo_Name, todo_Name[i]);
            vals.put(Constants.ToDo_Description, todo_Description[i]);
            vals.put(Constants.ToDo_Status,todo_Status.getResourceId(i, -1));
            dbHelper.insertContentVals(Constants.ToDo_Table, vals);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            Toast.makeText(this, "Add New", Toast.LENGTH_SHORT).show();
            OpenDiaglog();
            return true;
        }

        if (id == R.id.action_TaskComplete ) {
            Toast.makeText(this, "Task Complete", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void OpenDiaglog() {
        todoDiaglog todo_Dialog = new todoDiaglog();
        todo_Dialog.show(getSupportFragmentManager(),"ToDo Diaglog");

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        String member_name = ToDoAllData.get(position).getToDoDate();
        Toast.makeText(getApplicationContext(), "" + member_name,
                Toast.LENGTH_SHORT).show();
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

    @Override
    public void addNewTodo(String strToDoAddName, String strToDoAddDescription) {

        Toast.makeText(this, strToDoAddName + strToDoAddDescription , Toast.LENGTH_SHORT).show();

    }


}

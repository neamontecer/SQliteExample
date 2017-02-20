package com.example.maygalang.sqliteexample;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GoalsPage extends AppCompatActivity {
    DatabaseHelper mydb;
    SQLiteDatabase db;
    private ListView listViewGoalsList;
    Cursor data;
    Cursor detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goals_page);
        openDatabase();
        mydb = new DatabaseHelper(this);

        listViewGoalsList = (ListView)findViewById(R.id.listViewGoalsList);
        showGoalsList();
    }

    protected void openDatabase() {
        db = openOrCreateDatabase("THRIFTY.db", Context.MODE_PRIVATE, null);
    }

    public void showGoalsList(){
       final ArrayList<String> theList = new ArrayList<>();

            data = db.rawQuery("SELECT GoalRank FROM GOALS WHERE GoalAccomplished = 1 ORDER BY GoalRank ASC",null);
            while(data.moveToNext()) {
                theList.add(data.getString(0));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                listViewGoalsList.setAdapter(listAdapter);

                listViewGoalsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //int result = (int)parent.getItemIdAtPosition(position);
                        //String result = (String)parent.getItemAtPosition(position);
                        data.moveToPosition(position);
                        String result = data.getString(data.getColumnIndex("GoalRank"));
                        detail = db.rawQuery("SELECT * FROM GOALS WHERE GoalAccomplished = 1 AND GoalRank = " + result, null);
                        detail.moveToFirst();
                        final String goalName = detail.getString(1);
                        if(goalName == null){
                            Intent intent = new Intent(GoalsPage.this, AddGoal.class);
                            intent.putExtra("goalRank", result);
                            startActivity(intent);
                        }else{
                            Intent intent2 = new Intent(GoalsPage.this, ViewGoalDetails.class);
                            intent2.putExtra("goalRank",result);
                            startActivity(intent2);
                        }
                    }
                });
            }
        Log.i("View", "CategoryList");
    }

}


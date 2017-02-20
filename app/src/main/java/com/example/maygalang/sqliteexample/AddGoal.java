package com.example.maygalang.sqliteexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddGoal extends Activity{
    DatabaseHelper mydb;
    private EditText editTextGoalName;
    private Button btnSaveGoal;
    private EditText editTextGoalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_goal);
        mydb = new DatabaseHelper(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*.5));
        
        editTextGoalName = (EditText)findViewById(R.id.editTextGoalName);
        editTextGoalCost = (EditText)findViewById(R.id.editTextGoalCost);
        btnSaveGoal = (Button)findViewById(R.id.btnSaveGoal);
        insertGoal();
    }

    private void insertGoal() {
        btnSaveGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timestamp = new SimpleDateFormat("MMMM dd, yyyy").format(new Date());
                Bundle bundle = getIntent().getExtras();
                String GoalRank = bundle.getString("goalRank");
                mydb.insertGoal(editTextGoalName.getText().toString(),editTextGoalCost.getText().toString(),timestamp,GoalRank);
                Log.i("insert", "Goal Inserted");
                Intent intent = new Intent(AddGoal.this,GoalsPage.class);
                startActivity(intent);
            }
        });
    }
}

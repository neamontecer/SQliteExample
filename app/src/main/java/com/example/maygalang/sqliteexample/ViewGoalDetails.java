package com.example.maygalang.sqliteexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by May Galang on 2/2/2017.
 */
public class ViewGoalDetails extends Activity {
    DatabaseHelper mydb;
    SQLiteDatabase db;
    private TextView textViewGoalRank_;
    private Button buttonForecastGoal;
    private Button buttonUpdateGoal;
    private EditText editTextGoalName_;
    private EditText editTextGoalCost_;
    Cursor data;
    private TextView textViewMoneySaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_goal_details);
        openDatabase();
        mydb = new DatabaseHelper(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .9));

        textViewGoalRank_ = (TextView)findViewById(R.id.textViewGoalRank_);
        editTextGoalName_ = (EditText)findViewById(R.id.editTextGoalName_);
        editTextGoalCost_ = (EditText)findViewById(R.id.editTextGoalCost_);
        buttonUpdateGoal = (Button)findViewById(R.id.buttonUpdateGoal);
        buttonForecastGoal = (Button)findViewById(R.id.buttonForecastGoal);
        textViewMoneySaved = (TextView)findViewById(R.id.textViewMoneySaved);

        viewGoalDetails();
    }

    protected void openDatabase() {
        db = openOrCreateDatabase("THRIFTY.db", Context.MODE_PRIVATE, null);
    }

    public void viewGoalDetails() {
        Bundle bundle = getIntent().getExtras();
        final String GoalRank = bundle.getString("goalRank");

        data = db.rawQuery("SELECT * FROM GOALS WHERE GoalAccomplished = 1 AND GoalRank = " + GoalRank, null);
        data.moveToFirst();
        String textGoalRank = data.getString(4);
        String textGoalName = data.getString(1);
        String textGoalCost = data.getString(2);
        String textMoneySaved = data.getString(7);

        textViewGoalRank_.setText(textGoalRank);
        textViewMoneySaved.setText(textMoneySaved);
        editTextGoalName_.setText(textGoalName);
        editTextGoalCost_.setText(textGoalCost);

        buttonUpdateGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb.updateGoal(editTextGoalName_.getText().toString(), editTextGoalCost_.getText().toString(), GoalRank);
            }
        });

        buttonForecastGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ViewGoalDetails.this, ViewGoal.class);
                intent2.putExtra("goalRank", GoalRank);
                startActivity(intent2);
            }
        });
    }
}

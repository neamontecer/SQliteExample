package com.example.maygalang.sqliteexample;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewSavings extends AppCompatActivity {
    DatabaseHelper mydb;
    SQLiteDatabase db;
    private Button buttonIncreaseSavings;
    private Button buttonUseSavings;
    private TextView textViewTotalSavings;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_savings);
        openDatabase();
        mydb = new DatabaseHelper(this);

        buttonIncreaseSavings = (Button)findViewById(R.id.buttonIncreaseSavings);
        textViewTotalSavings = (TextView)findViewById(R.id.textViewTotalSavings);
        buttonUseSavings = (Button)findViewById(R.id.buttonUseSavings);


        Cursor data = db.rawQuery("SELECT SavingsAmount FROM SAVINGS", null);
        data.moveToFirst();
        final String SavingsAmount = data.getString(0);
        textViewTotalSavings.setText(SavingsAmount);

        cursor = db.rawQuery("SELECT IncomeAmount FROM INCOME WHERE ACTIVE = 1", null);
        cursor.moveToFirst();
        final String IncomeAmount = cursor.getString(0);

        buttonIncreaseSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Float.valueOf(IncomeAmount) > 0) {
                    Intent intent = new Intent(ViewSavings.this, AddSavings.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ViewSavings.this, "Income not enough", Toast.LENGTH_SHORT).show();
                }
            }
        });


        buttonUseSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Float.valueOf(SavingsAmount) > 0) {
                    Intent intent = new Intent(ViewSavings.this, UseSavings.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(ViewSavings.this, "Savings not enough", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    protected void openDatabase() {
        db = openOrCreateDatabase("THRIFTY.db", Context.MODE_PRIVATE, null);
    }
}

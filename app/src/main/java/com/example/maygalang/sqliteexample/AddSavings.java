package com.example.maygalang.sqliteexample;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddSavings extends AppCompatActivity {
    DatabaseHelper mydb;
    SQLiteDatabase db;
    Cursor cursor;
    private EditText editTextSavingsAmount;
    private Button btnAddSavingsAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_savings);
        openDatabase();
        mydb = new DatabaseHelper(this);

        btnAddSavingsAmount = (Button)findViewById(R.id.btnAddSavings);
        editTextSavingsAmount = (EditText)findViewById(R.id.editTextSavingsAmount);

        cursor = db.rawQuery("SELECT IncomeAmount FROM INCOME WHERE ACTIVE = 1", null);
        cursor.moveToFirst();
        UpdateSavingsAmount();
    }

    protected void openDatabase() {
        db = openOrCreateDatabase("THRIFTY.db", Context.MODE_PRIVATE, null);
    }

    public void UpdateSavingsAmount(){
        btnAddSavingsAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float totalIncomeAmount = Float.valueOf(cursor.getString(0));
                if (totalIncomeAmount > 0) {
                    float savingsAmount = Float.valueOf(editTextSavingsAmount.getText().toString());
                    if(savingsAmount <= totalIncomeAmount) {
                        mydb.updateSavings(editTextSavingsAmount.getText().toString());
                        mydb.calculateIncome(savingsAmount);
                        Intent intent = new Intent(AddSavings.this, ViewSavings.class);
                        startActivity(intent);
                    }else{ Toast.makeText(AddSavings.this, "Income not enough", Toast.LENGTH_SHORT).show(); }
                } else {
                    Toast.makeText(AddSavings.this, "Income not enough", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

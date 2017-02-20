package com.example.maygalang.sqliteexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by May Galang on 1/20/2017.
 */
public class AddExpense extends Activity{
    SQLiteDatabase db;
    DatabaseHelper mydb;
    private EditText editTextExpenseAmount;
    private Button btnAddExpenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense);
        openDatabase();
        mydb = new DatabaseHelper(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*.5));


        editTextExpenseAmount = (EditText)findViewById(R.id.editTextExpenseAmount);
        btnAddExpenses = (Button)findViewById(R.id.btnAddExpenses);
        InsertExpenses();
    }

    protected void openDatabase() {
        db = openOrCreateDatabase("THRIFTY.db", Context.MODE_PRIVATE, null);
    }

    public void InsertExpenses() {
        btnAddExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String timestamp = new SimpleDateFormat("MMMM dd, yyyy").format(new Date());
                String timestamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                Bundle bundle = getIntent().getExtras();
                String categoryName = bundle.getString("categoryName");
                String categoryID = bundle.getString("categoryID");

                Cursor cursor = db.rawQuery("SELECT count(*) FROM EXPENSE WHERE ExpenseDate = '" + timestamp + "' " +
                        "AND CategoryName = '"+ categoryName +"' AND ACTIVE = 1",null);
                cursor.moveToFirst();
                int count = cursor.getInt(0);
                if(count > 0){
                    mydb.UpdateExpenseAmount(editTextExpenseAmount.getText().toString(), timestamp, categoryName, categoryID);
                    Log.i("Expense Insert", "Expense Updated");
                }else {
                    mydb.AddExpense(editTextExpenseAmount.getText().toString(), timestamp, categoryName, categoryID);
                    Log.i("Expense Insert", "Expense Inserted");
                }
                    Intent intent = new Intent(AddExpense.this,ViewExpense.class);
                    startActivity(intent);
            }
        });
    }
}

package com.example.maygalang.sqliteexample;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ViewBudget extends AppCompatActivity {

    DatabaseHelper mydb;
    SQLiteDatabase db;
    private ListView listViewCategoryWithExpense;
    SimpleCursorAdapter adapter;
    Cursor data;
    private Button btnCheckBudget;
    private EditText categoryAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_reports);
        mydb = new DatabaseHelper(this);
        openDatabase();

        listViewCategoryWithExpense = (ListView) findViewById(R.id.listViewCategoryWithExpense);
        btnCheckBudget = (Button) findViewById(R.id.btnCheckBudget);
        categoryAmount = (EditText)findViewById(R.id.firstCategoryAmount);
        ShowCategoryList();
    }
    protected void openDatabase() {
        db = openOrCreateDatabase("THRIFTY.db", Context.MODE_PRIVATE, null);
    }

    public void ShowCategoryList(){
        data = db.rawQuery("SELECT ID AS _id, * FROM CATEGORY WHERE ACTIVE = 1", null);

        String[] columns = new String[]{DatabaseHelper.KEY_TASK,DatabaseHelper.KEY_TASK2};
        int[] to = new int[]{R.id.firstCategoryName, R.id.firstCategoryAmount};

        adapter = new SimpleCursorAdapter(ViewBudget.this,R.layout.view_budget,data,columns,to,0);
        listViewCategoryWithExpense.setAdapter(adapter);

        btnCheckBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Cursor cursor = db.rawQuery("SELECT IncomeAmount FROM INCOME WHERE ACTIVE = 1",null);
                cursor.moveToFirst();
                float incomeAmount = Float.valueOf(cursor.getString(0));
                if(R.id.CategoryAmount > incomeAmount) {
                    Toast.makeText(ViewBudget.this, "Income not enough", Toast.LENGTH_SHORT).show();
                }else{*/
                    mydb.updateBudget(categoryAmount.getText().toString());
                    Toast.makeText(ViewBudget.this, "Budget Updated", Toast.LENGTH_SHORT).show();
                   /* Intent intent = new Intent(ViewBudget.this, MenuButtons.class);
                    startActivity(intent);*/
           //     }
            }
        });
    }
}

package com.example.maygalang.sqliteexample;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;


public class ViewReports extends AppCompatActivity {
    DatabaseHelper mydb;
    SQLiteDatabase db;
    private ListView listViewCategoryWithExpense;
    private Button btnGraphShow;
    private Button btnForecastExp;
    SimpleCursorAdapter adapter;
    Cursor data;
    private Button btnCheckBudget;
    private LayoutInflater1 vi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_reports);
        mydb = new DatabaseHelper(this);
        openDatabase();

        listViewCategoryWithExpense = (ListView)findViewById(R.id.listViewCategoryWithExpense);
        btnGraphShow = (Button)findViewById(R.id.btnGraphShow);
        btnForecastExp = (Button)findViewById(R.id.btnForecastExp);
        btnCheckBudget = (Button)findViewById(R.id.btnCheckBudget);

        ShowCategoryExpenseList();
        NextExpenseForecast();
    }

    protected void openDatabase() {
        db = openOrCreateDatabase("THRIFTY.db", Context.MODE_PRIVATE, null);
    }

    private void NextExpenseForecast() {
        btnForecastExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewReports.this, ForecastBudget.class);
                startActivity(intent);
            }
        });
    }

    public void ShowCategoryExpenseList(){

        data = mydb.getListExpenses();
        String[] columns = new String[]{DatabaseHelper.KEY_TASK,DatabaseHelper.KEY_TASK3};
        int[] to = new int[]{R.id.CategoryName, R.id.CategoryAmount};//Expenseamount dapat sa .xml

        //String categoryID = data.getString(data.getColumnIndex("ID"));
        //adapter = new SimpleCursorAdapter(ViewReports.this);
        ArrayAdapter<String> listadapter = new ArrayAdapter<String>(this, R.layout.row, columns);
        final ArrayList<String> theList = new ArrayList<>();


        //adapter = new SimpleCursorAdapter(ViewReports.this,R.layout.row,data,columns,to,0);
        listViewCategoryWithExpense.setAdapter(adapter);

    }


}


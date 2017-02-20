package com.example.maygalang.sqliteexample;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class ForecastBudget_Month extends AppCompatActivity {
    DatabaseHelper myDb;
    SQLiteDatabase db;
    private ListView listViewForecastMonth;
    Cursor data;
    SimpleCursorAdapter adapter;
    SimpleDateFormat df = new SimpleDateFormat("MMMM dd, yyyy");
    private Button buttonForecastNextWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecast_budget__month);
        openDatabase();

        listViewForecastMonth = (ListView)findViewById(R.id.listViewForecastMonth);
        buttonForecastNextWeek = (Button)findViewById(R.id.buttonForecastNextWeek);

        showForecastNextMonth();
        showForecastNextWeek();
    }

    public void showForecastNextMonth(){
        Cursor cur = db.rawQuery("SELECT *, EXPENSE.CategoryName, " +
                "INCOME.ID AS _id, " +
                "EXPENSE.ID AS _id, " +
                "(SUM(EXPENSE.ExpenseAmount)/(JulianDay('now') - JulianDay(MIN(EXPENSE.ExpenseDate)))) * 7 AS ExpenseForecast " +
                "FROM INCOME, EXPENSE WHERE EXPENSE.ACTIVE = 1 AND INCOME.ACTIVE = 1 " +
                "AND EXPENSE.ExpenseDate BETWEEN INCOME.IncomeDateTo AND INCOME.IncomeDateFrom " +
                "GROUP BY EXPENSE.CategoryName ORDER BY EXPENSE.ExpenseDate DESC", null);

        String[] columns = new String[]{"CategoryName", "ExpenseForecast"};
        int[] to = new int[]{R.id.textViewExpense, R.id.textViewExpenseAmountForecast};

        adapter = new SimpleCursorAdapter(ForecastBudget_Month.this, R.layout.forecast_budget_row, cur, columns, to, 0);
        listViewForecastMonth.setAdapter(adapter);
    }

    public void showForecastNextWeek(){
        buttonForecastNextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForecastBudget_Month.this, ForecastBudget.class);
                startActivity(intent);
            }
        });
    }

    protected void openDatabase() {
        db = openOrCreateDatabase("THRIFTY.db", Context.MODE_PRIVATE, null);
    }
}

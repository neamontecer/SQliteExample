package com.example.maygalang.sqliteexample;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ForecastBudget extends AppCompatActivity {
    DatabaseHelper myDb;
    SQLiteDatabase db;
    private ListView listViewForecastList;
    Cursor data;
    SimpleCursorAdapter adapter;
    SimpleDateFormat df = new SimpleDateFormat("MMMM dd, yyyy");
    private TextView textView12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecast_budget);
        openDatabase();

        listViewForecastList = (ListView)findViewById(R.id.listViewForecastList);

        showForecastExpense();
    }

    protected void openDatabase() {
        db = openOrCreateDatabase("THRIFTY.db", Context.MODE_PRIVATE, null);
    }

    public void showForecastExpense() {
                Cursor cur = db.rawQuery("SELECT *, EXPENSE.CategoryName, " +
                        "INCOME.ID AS _id, " +
                        "EXPENSE.ID AS _id, " +
                        "(SUM(EXPENSE.ExpenseAmount)/(JulianDay('now') - JulianDay(MIN(EXPENSE.ExpenseDate)))) * 7 AS ExpenseForecast " +
                        "FROM INCOME, EXPENSE WHERE EXPENSE.ACTIVE = 1 AND INCOME.ACTIVE = 1 " +
                       "AND EXPENSE.ExpenseDate BETWEEN INCOME.IncomeDateTo AND INCOME.IncomeDateFrom " +
                        "GROUP BY EXPENSE.CategoryName ORDER BY EXPENSE.ExpenseDate DESC", null);


                //SELECT ProductName, Price, FORMAT(Now(),'YYYY-MM-DD') AS PerDate FROM Products;
                //SELECT * FROM mytalbe WHERE DATE_FORMAT(date,'%Y-%m-%d') BETWEEN '2014-10-09' AND '2014-10-10'
                //SELECT * FROM test WHERE strftime('%Y-%m-%d', date) BETWEEN "11-01-2011" AND "11-08-2011"
                //float expenseForecast = ((Float.valueOf(sumExpense) / diffDays) * 7);

        /*
        SELECT 10 AS my_num,
       (SELECT my_num) * 5 AS another_number
        FROM table

        SELECT QtyContracted1,ContractPrice, Amount*2
FROM
(select *, (QtyContracted1*ContractPrice) AS Amount from TCA20007 ) a

SELECT SalesOrderID, OrderDate,
  DATEPART(weekday, DATEADD(day, @@DATEFIRST - 7, OrderDate)) AS Week_Day
FROM Sales.SalesOrderHeader
WHERE DATEPART(weekday, DATEADD(day, @@DATEFIRST - 7, OrderDate)) NOT IN (1, 7);

SELECT SUM(t.price) AS subtotal,
       SUM(t.price) * 3.0 AS fees,
       SUM(t.price + fees) AS total
  FROM CART t

  SELECT c.d AS a, c.d + 2 AS b
FROM
  (SELECT 1+1 AS d) c


  SELECT DATEDIFF('2014-11-30','2014-11-29') AS DiffDate

  SELECT rdate,tdate,
       cast(rdate AS NUMERIC), cast(tdate AS NUMERIC),
       rdate - tdate
FROM table ;


Select Cast ((
    JulianDay(ToDate) - JulianDay(FromDate)
) As Integer)    ==== diff in days daw to eh

strftime('%Y %m %d','now')
--------------------------
2014 10 31

SELECT * FROM test WHERE strftime('%Y-%m-%d', date) BETWEEN "11-01-2011" AND "11-08-2011"
         */

                String[] columns = new String[]{"CategoryName", "ExpenseForecast"};
                int[] to = new int[]{R.id.textViewExpense, R.id.textViewExpenseAmountForecast};

                adapter = new SimpleCursorAdapter(ForecastBudget.this, R.layout.forecast_budget_row, cur, columns, to, 0);
        listViewForecastList.setAdapter(adapter);

    }
}

/*
        Expense Forecast for the Next Week = ((Total Expense recorded / (Date today – Date of first Expense)) * 7)
        1 week = 7 days
        Total Expense = 875; Date Today = November 14, 2016;
        Date of 1st Expense = November 01, 2016

        Expense Forecast for the Next Week = ((875 / (14 - 1)) * 7)
        Expense Forecast for the Next Week = 471.15
        The user average expense for the next week is 471.15.
     */


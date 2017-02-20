package com.example.maygalang.sqliteexample;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    SQLiteDatabase db;
    EditText txtIncomeAmount;
    Button btnSetBudget;
    private DatePicker datePickerTo;
    private DatePicker datePickerFrom;
    Cursor c;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        myDb = new DatabaseHelper(this);
        openDatabase();

        txtIncomeAmount = (EditText)findViewById(R.id.editTextIncomeAmount);
        btnSetBudget = (Button)findViewById(R.id.btnSubmit);
        datePickerTo = (DatePicker) findViewById(R.id.datePickerTo);
        datePickerFrom = (DatePicker)findViewById(R.id.datePickerFrom);

        //Checking if there is Active Income
        c = db.rawQuery("SELECT count(*) FROM Income WHERE ACTIVE = 1", null);
        c.moveToFirst();
        int count = c.getInt(0);
        if(count > 0){
            //Checking if the set EndDate for Income is equal on the current date, so that Income not use will be savings already
            Cursor checkEndate = db.rawQuery("SELECT * FROM INCOME WHERE ACTIVE = 1", null);
            checkEndate.moveToFirst();
            String IncomeAmount = checkEndate.getString(1);
            String IncomeDateTo = checkEndate.getString(2);
/*
            try{
                Date d = df.parse(IncomeDateTo);
               // String timestamp = df.parse(df.format(new Date()));
               String timestamp = df.format(new Date());
                Date today = df.parse(timestamp);
                if(today.after(d)){ //after ng DATE PARSE!
                    if(Float.valueOf(IncomeAmount) > 0) {
                        myDb.excessIncomeBudget(IncomeAmount);
                    }else{
                        myDb.deActivateIncome();
                    }
                }
                /*long endDateMs = d.getTime();
                long currentDate = new java.util.Date().getTime();
                if(currentDate > endDateMs){
                    if(Float.valueOf(IncomeAmount) > 0) {
                        myDb.excessIncomeBudget(IncomeAmount);
                    }else{
                        myDb.deActivateIncome();
                    }
                }*/
            /*}catch (ParseException e){e.printStackTrace();}*/

            Intent intent = new Intent(MainActivity.this,MenuButtons.class);
            startActivity(intent);
        }else{
            AddData();
        }
    }

    protected void openDatabase() {
        db = openOrCreateDatabase("THRIFTY.db", Context.MODE_PRIVATE, null);
    }

    public void AddData(){
        btnSetBudget.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            String startDate = df.format(new Date(datePickerTo.getYear() - 1900, datePickerTo.getMonth(), datePickerTo.getDayOfMonth()));
                            String endDate = df.format(new Date(datePickerFrom.getYear() - 1900, datePickerFrom.getMonth(), datePickerFrom.getDayOfMonth()));
                            if (df.parse(startDate).before(df.parse(endDate))){
                                if(txtIncomeAmount.length() == 0)  {
                                    Toast.makeText(MainActivity.this,"Fill up the field",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(MainActivity.this, "Start Date After End Date", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                if(txtIncomeAmount.length() > 0)  {
                                    myDb.insertData(txtIncomeAmount.getText().toString(), endDate, startDate);
                                    Log.i("insert", "Data Inserted");
                                    Intent intent = new Intent(MainActivity.this,SetSavings.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(MainActivity.this,"Fill up the field",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }catch (ParseException e) {e.printStackTrace();}
                    }
                }
        );

    }


}
/*

     String str1 = "12/10/2013";
     Date date1 = formatter.parse(str1);

     String str2 = "13/10/2013";
     Date date2 = formatter.parse(str2);

     if (date1.compareTo(date2)<0)
      {
         System.out.println("date2 is Greater than my date1");
      }
 */
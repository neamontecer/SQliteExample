package com.example.maygalang.sqliteexample;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.text.ParseException;

/**
 * Created by Nea Montecer on 1/26/2017.
 */
public class ViewGoal extends Activity{
    DatabaseHelper mydb;
    SQLiteDatabase db;
    private TextView textViewGoalRank;
    private TextView textViewGoalDateForecast;
    private DatePicker datePickerGoal;
    private TextView textViewEstimatedCost;
    private Button buttonGoToViewGoal;
    private TextView textViewNameGoal;
    private TextView textViewCostOfGoal;
    Cursor data;
    Cursor cursor;
    private Button buttonCalculate;
    String textGoalCost;
    SimpleDateFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_goal);
        openDatabase();
        mydb = new DatabaseHelper(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .9));

        textViewGoalRank = (TextView)findViewById(R.id.textViewGoalRank);
        textViewGoalDateForecast = (TextView)findViewById(R.id.textViewGoalDateForecast);
        textViewNameGoal = (TextView)findViewById(R.id.textViewNameGoal);
        textViewCostOfGoal = (TextView)findViewById(R.id.textViewCostOfGoal);
        datePickerGoal = (DatePicker)findViewById(R.id.datePickerGoal);
        textViewEstimatedCost = (TextView)findViewById(R.id.textViewEstimatedCost);
        buttonGoToViewGoal = (Button)findViewById(R.id.buttonGoToViewGoal);
        buttonCalculate = (Button)findViewById(R.id.buttonCalculate);
        /*Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showGoalDetails();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();*/
        showGoalDetails();
    }

    protected void openDatabase() {
        db = openOrCreateDatabase("THRIFTY.db", Context.MODE_PRIVATE, null);
    }

    public void showGoalDetails() {
        Bundle bundle = getIntent().getExtras();
        String GoalRank = bundle.getString("goalRank");

        data = db.rawQuery("SELECT * FROM GOALS WHERE GoalAccomplished = 1 AND GoalRank = " + GoalRank, null);
        data.moveToFirst();
        String textGoalRank = data.getString(4);
        String textGoalName = data.getString(1);
        textGoalCost = data.getString(2);
        String moneySaved = data.getString(7);

        textViewGoalRank.setText(textGoalRank);
        textViewNameGoal.setText(textGoalName);
        textViewCostOfGoal.setText(textGoalCost);

        /*cursor = db.rawQuery("SELECT SavingsAmount FROM SAVINGS", null);
        cursor.moveToFirst();
        String savingsAmount = cursor.getString(0);*/
        if(Float.valueOf(moneySaved) > Float.valueOf(textGoalCost)){ //error here
            textViewGoalDateForecast.setText("You can achieve it today");
        }else {
            float numberOfMonths = Float.valueOf(textGoalCost) / Float.valueOf(moneySaved);
            //In this part I convert month into days to get the exact date or day for the forecasting 1month=30.44 days
            //The starting date for the forecast is the current date, then it will compute the result of forecastData or the no. of days
            float forecastData = Math.round((float) 30.44 * numberOfMonths);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, (int)forecastData);
            Date forecastGoalDate = cal.getTime();
            df = new SimpleDateFormat("MMMM dd, yyyy");
            String dateForecast = df.format(forecastGoalDate);

            textViewGoalDateForecast.setText(dateForecast);

           buttonCalculate.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   String dateGoal = df.format(new Date(datePickerGoal.getYear() - 1900, datePickerGoal.getMonth(), datePickerGoal.getDayOfMonth()));

                   try {
                       Date d = df.parse(dateGoal);
                       long dateWantToAchieveMs = d.getTime(); //Goal date you set
                       long currentDate = new java.util.Date().getTime(); //Current Date
                       if (dateWantToAchieveMs >= currentDate) {
                           long difference = Math.abs(dateWantToAchieveMs - currentDate); //Number of days to achieve the goal
                           long diffDays = difference / (24 * 60 * 60 * 1000) + 1; //Converting into days(from parse Date to real Days)

                           float convertDayToMonth = Math.round(Float.valueOf(diffDays) / (float) 30.44); //Converting Days to Month
                           float needSavings = Float.valueOf(textGoalCost) / convertDayToMonth; //Calculating the Savings Needed per month to achieve the goal on the date you want

                           int fourMonths = Math.round(convertDayToMonth / 4);
                           if (fourMonths >= 12) {
                               int yearConvert = (fourMonths / 12); //to know the year
                               int remainingMonth = fourMonths % 12; //get the remainder to know the remaining months
                               float suggestSavings = Float.valueOf(textGoalCost) / (convertDayToMonth - fourMonths); //Getting the suggested savings per month
                               float diffOldNewSavings_ = suggestSavings - needSavings;  //Difference between the suggestSavings and needSavings

                               textViewEstimatedCost.setText(String.valueOf(needSavings) + "/month \n" +
                                       "You could reach your goal " + String.valueOf(yearConvert)+ " year "+ String.valueOf(remainingMonth) + " months sooner by increasing your savings amount to " +
                                       String.valueOf(diffOldNewSavings_) + "/month or a total of " + String.valueOf(suggestSavings) + "/month");

                           } else {
                               float suggestSavings_ = Float.valueOf(textGoalCost) / (convertDayToMonth - fourMonths);
                               float diffOldNewSavings = suggestSavings_ - needSavings;
                               textViewEstimatedCost.setText(String.valueOf(needSavings) + "/month \n" +
                                       "You could reach your goal " + String.valueOf(fourMonths) + " months sooner by increasing your savings amount to " +
                                       String.valueOf(diffOldNewSavings) + "/month or a total of " + String.valueOf(suggestSavings_) + "/month");
                               /*textViewEstimatedCost.setText("%d/%f/month \n You could reach your goal %d sooner by increasing your savings amount to %f/month",
                                       diffDays,needSavings,fourMonths,suggestSavings_);*/
                           }

                           /*
                           Representation:
                            4 months = 1 month sooner
                            4 years = 1 year sooner
                            Computation:
                            1 year = 12 months
                            12	months / 4 months = 3 months sooner
                            */
                       } else {
                           Toast.makeText(ViewGoal.this, "Date should be greater than current date", Toast.LENGTH_LONG).show();
                       }
                   } catch (ParseException e) {
                       e.printStackTrace();
                   }
               }
           });
            Log.i("view", "Date Forecast");
        }
    }
}
package com.example.maygalang.sqliteexample;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by May Galang on 2/13/2017.
 */
public class UseSavings extends AppCompatActivity {
    DatabaseHelper mydb;
    SQLiteDatabase db;
    private EditText editTextUseSavingsAmount;
    private Spinner spinner;
    private Button buttonSubmitUseSavings;
    Cursor c;
    String spinnerID;
    float cost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_savings);
        openDatabase();
        mydb = new DatabaseHelper(this);

        editTextUseSavingsAmount = (EditText) findViewById(R.id.editTextUseSavingsAmount);
        buttonSubmitUseSavings = (Button)findViewById(R.id.buttonSubmitUseSavings);
        spinner = (Spinner)findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("Income");
        c = db.rawQuery("SELECT GoalRank, GoalCost FROM GOALS WHERE GoalAccomplished = 1 AND GoalName IS NOT NULL ORDER BY GoalRank ASC",null);

        while(c.moveToNext()){
            list.add("Goal " + c.getString(0));
            cost = Float.valueOf(c.getString(1));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        // Spinner item selection Listener
       // addListenerOnSpinnerItemSelection();

        useSavingsMethod();
        goalAccomplished();
    }

    //GET THE CHOICE IN SPINNER
    /*
    public void addListenerOnSpinnerItemSelection(){

        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }*/

    private void useSavingsMethod() {
        buttonSubmitUseSavings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = db.rawQuery("SELECT SavingsAmount FROM SAVINGS", null);
                data.moveToFirst();
                final float SavingsAmount = Float.valueOf(data.getString(0));

                if(editTextUseSavingsAmount.length() == 0){
                    Toast.makeText(UseSavings.this,"Fill up the field",Toast.LENGTH_SHORT).show();
                }
                else{
                    float amountAlloted = Float.valueOf(editTextUseSavingsAmount.getText().toString());
                    int position = Integer.valueOf(spinner.getSelectedItemPosition());
                    String goalRank = String.valueOf(spinner.getSelectedItem());
                    String s1 = goalRank;
                    String[] sp = s1.split(" ");
                    Cursor sql = db.rawQuery("SELECT GoalCost FROM GOALS WHERE GoalAccomplished = 1 AND GoalRank = " + sp[1], null);
                    sql.moveToFirst();
                    float costGoal = Float.valueOf(sql.getString(0));
                    if (amountAlloted > SavingsAmount) {
                        Toast.makeText(UseSavings.this, "Savings not enough", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UseSavings.this, UseSavings.class);
                        startActivity(intent);
                    } else {
                        if (position == 0) {
                            mydb.useSavingsAddIncome(editTextUseSavingsAmount.getText().toString());
                            mydb.AddIncome(editTextUseSavingsAmount.getText().toString());
                            Toast.makeText(UseSavings.this, "Savings added to Income", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(UseSavings.this, UseSavings.class);
                            startActivity(intent);
                        } else {
                            if(amountAlloted < costGoal || amountAlloted == costGoal) {
                                mydb.useSavingsAmount(editTextUseSavingsAmount.getText().toString(), sp[1]);
                                Toast.makeText(UseSavings.this, "Savings added to Goal", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(UseSavings.this, UseSavings.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(UseSavings.this, "Amount allotted is too big. \n" + s1 + " only cost "+ costGoal, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(UseSavings.this, UseSavings.class);
                                startActivity(intent);
                            }
                        }
                    }
                }
            }
        });

    }

    public void goalAccomplished(){
        Cursor cursor = db.rawQuery("SELECT * FROM GOALS WHERE GoalAccomplished = 1 AND MoneySaved = GoalCost", null);
        while(cursor.moveToNext()){
            String goalCost = cursor.getString(2);
            int goalRank = Integer.valueOf(cursor.getString(4));
            float goalPoints = Math.round(goalRank / 5);
            float moneySaved = Float.valueOf(cursor.getString(7));
            if (Float.valueOf(goalCost) == moneySaved) {
                String SQL2 = "UPDATE GOALS SET GoalAccomplished = 0, GoalPoints = " + goalPoints + " WHERE GoalRank = "+ goalRank;
                db.execSQL(SQL2);
                String SQL3 = "INSERT INTO GOALS (GoalRank, GoalAccomplished, MoneySaved) VALUES (" + goalRank + ", 1, 0)";
                db.execSQL(SQL3);
                Toast.makeText(UseSavings.this, "Congratulations! Goal " + goalRank + " Accomplished!", Toast.LENGTH_SHORT).show();
            }
        }

        //Magaadd ng badge
            /*
            yung badge nasa database na. tapos everytime na accomplsh magiging visible ung badge prng listbiew lng
            na inooutput pa horizontal
             */
    }

    protected void openDatabase() {
        db = openOrCreateDatabase("THRIFTY.db", Context.MODE_PRIVATE, null);
    }
}

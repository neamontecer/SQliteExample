package com.example.maygalang.sqliteexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SetSavings extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText txtSetSavings;
    Button btnSaving;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_savings);
        myDb = new DatabaseHelper(this);

        txtSetSavings = (EditText)findViewById(R.id.editTextSetSavings);
        btnSaving = (Button)findViewById(R.id.btnAddSaving);
        AddSavings();
    }

    public void AddSavings(){
        btnSaving.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String savingsAmount = txtSetSavings.getText().toString();
                        if(savingsAmount.length() == 0){
                            Toast.makeText(SetSavings.this, "Fill up the field", Toast.LENGTH_SHORT).show();
                        }else {
                            String timestamp = new SimpleDateFormat("MMMM dd, yyyy").format(new Date());
                            myDb.insertSavings(savingsAmount, timestamp);
                            Log.i("insert", "Data Inserted1");

                            float number = Float.parseFloat(savingsAmount);
                            myDb.calculateIncome(number);
                            Log.i("update", "Data Updated");

                            Intent intent = new Intent(SetSavings.this, SetBudget_first.class);
                            startActivity(intent);
                        }
                    }
                }
        );
    }
}
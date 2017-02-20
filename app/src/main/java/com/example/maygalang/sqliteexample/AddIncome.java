package com.example.maygalang.sqliteexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddIncome extends AppCompatActivity {
    DatabaseHelper myDb;
    private EditText editTxtAddIncome;
    private Button buttonAddIncome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_income);
        myDb = new DatabaseHelper(this);

        editTxtAddIncome = (EditText)findViewById(R.id.editTextAddIncome);
        buttonAddIncome = (Button) findViewById(R.id.btnAddIncome);
        AddIncomeAmount();
    }

    private void AddIncomeAmount() {
        buttonAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.AddIncome(editTxtAddIncome.getText().toString());
                Log.i("update", "Income Add/Updated");
            }
        });
    }
}

package com.example.maygalang.sqliteexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuButtons extends AppCompatActivity {

    private Button btnIncomePage;
    private Button btnExpensesPage;
    private Button btnReportsPage;
    private Button btnSavingPage;
    private Button btnGoalPage;
    private Button btnBudgetsPage;
    private Button btnCommunityPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_buttons);


        btnIncomePage =(Button)findViewById(R.id.btnIncomePage);
        btnExpensesPage = (Button)findViewById(R.id.btnExpensePage);
        btnReportsPage = (Button)findViewById(R.id.btnReportPage);
        btnSavingPage = (Button)findViewById(R.id.btnSavingsPage);
        btnGoalPage = (Button)findViewById(R.id.btnGoalsPage);
        btnCommunityPage = (Button)findViewById(R.id.btnCommunityPage);
        btnBudgetsPage = (Button)findViewById(R.id.btnBudgetPage);


        btnIncomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuButtons.this,AddIncome.class));
            }
        });

        btnExpensesPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuButtons.this,ViewExpense.class));
            }
        });

        btnReportsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuButtons.this,LayoutInflater1.class));
            }
        });

        btnBudgetsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuButtons.this,ViewBudget.class));
            }
        });

        btnGoalPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuButtons.this,GoalsPage.class));
            }
        });

        btnSavingPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuButtons.this,ViewSavings.class));
            }
        });

        btnCommunityPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuButtons.this,ForecastBudget.class));
            }
        });
    }
}

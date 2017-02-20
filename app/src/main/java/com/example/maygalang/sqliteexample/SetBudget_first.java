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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class SetBudget_first extends AppCompatActivity {
    SQLiteDatabase db;
    DatabaseHelper mydb;
    Button btnCheck;
    TextView txtTotalBudget;
    Button btnAddCategory;
    Cursor c;
    SimpleCursorAdapter adapter;
    Cursor data;

    private static final String SELECT_SQL = "SELECT IncomeAmount FROM Income WHERE ACTIVE = 1";
    private ListView listViewFirstCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setbudget_first);
        openDatabase();
        mydb = new DatabaseHelper(this);


        btnCheck = (Button) findViewById(R.id.btnSubmit);
        btnAddCategory = (Button)findViewById(R.id.btnInsertCategory);
        txtTotalBudget = (TextView)findViewById(R.id.txtViewTotalBudget);
        listViewFirstCategory = (ListView)findViewById(R.id.listViewFirstCategory);

        c = db.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        TotalBudget();

        AddCategory();
        ShowFirstCategory();
    }

    protected void openDatabase() {
        db = openOrCreateDatabase("THRIFTY.db", Context.MODE_PRIVATE, null);
    }

    public void TotalBudget(){
        String totalbudget = c.getString(0);
        txtTotalBudget.setText(totalbudget);
    }

    public void ShowFirstCategory(){
        data = mydb.getListContentsCategory();
        String[] columns = new String[]{DatabaseHelper.KEY_TASK,"Budget"};
        int[] to = new int[]{R.id.firstCategoryName, R.id.firstCategoryAmount};

        adapter = new SimpleCursorAdapter(SetBudget_first.this,R.layout.firstcategory_row,data,columns,to,0);
        listViewFirstCategory.setAdapter(adapter);

        AddBudget();
    }

    public void AddCategory(){
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(SetBudget_first.this, Main4Activity.class);
                startActivity(intent);*/
                startActivity(new Intent(SetBudget_first.this,AddCategory.class));
            }
        });
    }

    public void AddBudget() {
       btnCheck.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //mydb.insertBudgetOnCategory(txtWaterAmount.getText().toString(),
                       //txtElectricityAmount.getText().toString(), txtHouseRentAmount.getText().toString());
               mydb.insertBudgetOnCategory(String.valueOf(R.id.firstCategoryAmount), String.valueOf(R.id.firstCategoryName));
               Toast.makeText(SetBudget_first.this, "Budget Inserted", Toast.LENGTH_SHORT).show();
               Log.i("insert","Data insert expense");
               Intent intent = new Intent(SetBudget_first.this,ViewExpense.class);
               startActivity(intent);
           }
       });
    }
}

package com.example.maygalang.sqliteexample;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity {
    DatabaseHelper mydb;
    private ListView listViewCategory;
    private Button btnAddCategory;
    private Button btnCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        mydb = new DatabaseHelper(this);

        listViewCategory = (ListView)findViewById(R.id.listViewCategory);
        btnAddCategory = (Button)findViewById(R.id.btnInsertCategory);
        btnCheck = (Button)findViewById(R.id.btnNext);

        CategoryListView();
        ButtonNext();
        onClick_AddCategory();
    }

    public void ButtonNext(){
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main4Activity.this,ViewExpense.class);
                startActivity(intent);
            }
        });
    }

    public  void onClick_AddCategory(){
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main4Activity.this,AddCategory.class));
            }
        });
        CategoryListView();
    }

    public void CategoryListView(){
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = mydb.getListContentsCategory();
        if(data.getCount() == 0){
            Toast.makeText(Main4Activity.this,"No Category",Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){
                theList.add(data.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,theList);
                listViewCategory.setAdapter(listAdapter);
            }
        }
        Log.i("View","CategoryList");
    }
}

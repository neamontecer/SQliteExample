package com.example.maygalang.sqliteexample;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewExpense extends AppCompatActivity {
    SQLiteDatabase db;
    DatabaseHelper mydb;
    private ListView listViewCategoryExp;
    private Button btnGraph;
    Cursor data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_expense);
        openDatabase();
        mydb = new DatabaseHelper(this);

        listViewCategoryExp = (ListView)findViewById(R.id.listViewCategoryExp);
        btnGraph = (Button)findViewById(R.id.btnGraph);

       CategoryListView();
        ButtonNext();
    }

    protected void openDatabase() {
        db = openOrCreateDatabase("THRIFTY.db", Context.MODE_PRIVATE, null);
    }

    public void ButtonNext(){
        btnGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewExpense.this, ViewReports.class);
                startActivity(intent);
            }
        });
    }

    public void CategoryListView(){
        final ArrayList<String> theList = new ArrayList<>();
        data = mydb.ContentsCategory();
        if(data.getCount() == 0){
            Toast.makeText(ViewExpense.this, "No Category", Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){
                theList.add(data.getString(1));
                final String ID = data.getString(0);
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,theList);
                listViewCategoryExp.setAdapter(listAdapter);

                listViewCategoryExp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        data.moveToPosition(position);
                        //String result =    data.getString(data.getColumnIndex("ID"));
                        String result = data.getString(data.getColumnIndex("CategoryName"));
                        String categoryID = data.getString(data.getColumnIndex("ID"));

                        //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();

                        //Toast.makeText(ViewExpense.this,ID,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ViewExpense.this, AddExpense.class);
                        intent.putExtra("categoryName",result);
                        intent.putExtra("categoryID", categoryID);
                        startActivity(intent);
                    }
                });
            }
        }

        Log.i("View", "CategoryList");
    }
}
/*
Cursor cursor = null;
            cursor = (Cursor) parent.getItemAtPosition(position);
            Intent intent = new Intent(SendingData.this, ReceivingData.class);
            intent.putExtra("id", cursor.getInt(cursor.getColumnIndex(Database.columns[0]))); //assuming that the first column in your database table is the row_id
            intent.putExtra("date", cursor.getString(cursor.getColumnIndex(Database.columns[1]))); //assuming that the second column in your database table is the text1
            intent.putExtra("name", cursor.getString(cursor.getColumnIndex(Database.columns[2]))); //assuming that the third column in your database table is the text2
            startActivity(intent);
 */

/*
lv.setOnItemClickListener(new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
        {
      if(cursor!=null){
                    System.out.println("position===>"+position);
                    if(cursor.moveToFirst()){
                        cursor.moveToPosition(position);
                        String cardid =    cursor.getString(cursor.getColumnIndex("_id"));

                    }
                }
        }
    });
 */

/*
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        long id = titlesAdapter.getItems().get(i).getId();
    }
});
 */

/*
Toast.makeText(getBaseContext(), id + "", Toast.LENGTH_LONG).show();
 */

//HashMap<String,String> item = (HashMap<String,String>)listViewCategoryExp.getItemAtPosition(position);
//String selectedItem = item.get(ID);
//String result = (String)parent.getItemAtPosition(position);
//int result = (int)parent.getInt(position);
//listViewCategoryExp.getAdapter().getItemId(position);

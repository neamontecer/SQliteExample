package com.example.maygalang.sqliteexample;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by May Galang on 2/9/2017.
 */

public class LayoutInflater1 extends Activity {
    SQLiteDatabase db;
    DatabaseHelper mydb;
    Cursor cursor;
    private ListView mMessageListView;
    private ListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_reports);
        mydb = new DatabaseHelper(this);
        openDatabase();
        mMessageListView = (ListView) findViewById(R.id.listViewCategoryWithExpense);

        mMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parnet, View view, int position, long id) {
                // display an activiting show item details
            }
        });


        // Set list view adapter

        cursor = db.rawQuery("SELECT *, ID AS _id FROM EXPENSE WHERE ACTIVE = 1 GROUP BY ExpenseDate ORDER BY ExpenseDate DESC",null);  // parameters snipped
        mAdapter = new MessageAdapter(this, cursor);
        mMessageListView.setAdapter(mAdapter);
    }

    protected void openDatabase() {
        db = openOrCreateDatabase("THRIFTY.db", Context.MODE_PRIVATE, null);
    }

    private static final class MessageAdapter extends CursorAdapter {

        // We have two list item view types

        private static final int VIEW_TYPE_GROUP_START = 0;
        private static final int VIEW_TYPE_GROUP_CONT = 1;
        private static final int VIEW_TYPE_COUNT = 10;

        MessageAdapter(Context context, Cursor cursor) {
            super(context, cursor);

            // Get the layout inflater

            mInflater = LayoutInflater.from(context);

            // Get and cache column indices
           // while(cursor.moveToNext()) {
                mColSubject = cursor.getColumnIndex("CategoryName");
                mColFrom = cursor.getColumnIndex("ExpenseAmount");
                mColWhen = cursor.getColumnIndex("ExpenseDate");
           // }
        }

        @Override
        public int getViewTypeCount() {
            return VIEW_TYPE_COUNT;
        }

        @Override
        public int getItemViewType(int position) {
            // There is always a group header for the first data item

            if (position == 0) {
                return VIEW_TYPE_GROUP_START;
            }

            // For other items, decide based on current data

            Cursor cursor = getCursor();
            cursor.moveToPosition(position);
            boolean newGroup = isNewGroup(cursor, position);

            // Check item grouping

            if (newGroup) {
                return VIEW_TYPE_GROUP_START;
            } else {
                return VIEW_TYPE_GROUP_CONT;
            }
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {

            int position = cursor.getPosition();
            int nViewType;

            if (position == 0) {
                // Group header for position 0

                nViewType = VIEW_TYPE_GROUP_START;
            } else {
                // For other positions, decide based on data

                boolean newGroup = isNewGroup(cursor, position);

                if (newGroup) {
                    nViewType = VIEW_TYPE_GROUP_START;
                } else {
                    nViewType = VIEW_TYPE_GROUP_CONT;
                }
            }

            View v;

            if (nViewType == VIEW_TYPE_GROUP_START) {
                // Inflate a layout to start a new group

                //v = mInflater.inflate(R.layout.message_list_item_with_header, parent, false);
                //vi = inf.inflate(R.layout.list_header, null);
                v = mInflater.inflate(R.layout.list_header, parent, false);

                // Ignore clicks on the list header

                View vHeader = v.findViewById(R.id.list_header_title);
                vHeader.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            } else {
                // Inflate a layout for "regular" items

                v = mInflater.inflate(R.layout.list_header, parent, false);
            }
            return v;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView tv;
                tv = (TextView) view.findViewById(R.id.ExpenseCategoryName1);
                tv.setText(cursor.getString(mColSubject));

                tv = (TextView) view.findViewById(R.id.ExpenseCategoryAmount1);
                tv.setText(cursor.getString(mColFrom));

                tv = (TextView) view.findViewById(R.id.list_header_title);
                tv.setText(cursor.getString(mColWhen));

        }


        private boolean isNewGroup(Cursor cursor, int position) {
            // Get date values for current and previous data items

            String nWhenThis = cursor.getString(mColWhen);

            cursor.moveToPosition(position - 1);
            String nWhenPrev = cursor.getString(mColWhen);

            // Restore cursor position

            cursor.moveToPosition(position);

            // Compare date values, ignore time values

            return false;
        }

        LayoutInflater mInflater;

        private int mColSubject;
        private int mColFrom;
        private int mColWhen;


    }

    // End of MessageAdapter


}























/*
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class LayoutInflater1 extends AppCompatActivity {
    SQLiteDatabase db;
    DatabaseHelper mydb;
    ListView listView;
    ArrayList<SectionStructure> sectionList = new ArrayList<LayoutInflater1.SectionStructure>();
    SectionStructure str;
    LayoutInflater inf;
    Cursor data;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_reports);
        mydb = new DatabaseHelper(this);
        data = mydb.getListExpenses();
        String[] sectionHeader = new String[]{data.getString(2)};
        String[] values = new String[]{data.getString(3),data.getString(1)};

        inf = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        listView = (ListView)findViewById(R.id.listViewCategoryWithExpense);

        for(int i=0; i<sectionHeader.length; i++){
            for(int j=0; j<values.length+1; j++){
                str = new SectionStructure();
                if(j==0){
                    str.setSectionName(sectionHeader[i]);
                    str.setSectionValue("");
                    sectionList.add(str);
                }
                else{

                    if(i ==1 && j== 2){

                    }else{
                        str.setSectionName("");
                        str.setSectionValue(values[j-1]);
                        sectionList.add(str);
                    }

                }
            }
        }

        listView.setAdapter(new AdapterClass());
    }

    public class AdapterClass extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return sectionList.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {

            View vi = arg1;
            vi = inf.inflate(R.layout.list_header, null);

            TextView textView =(TextView)vi.findViewById(R.id.list_header_title);
            LinearLayout linearLayout = (LinearLayout)vi.findViewById(R.id.linerLayout);
            if(sectionList.get(arg0).getSectionValue() !=null && sectionList.get(arg0).getSectionValue().equalsIgnoreCase("")){
                textView.setText(sectionList.get(arg0).getSectionName());
                linearLayout.setBackgroundColor(Color.GRAY);

            }
            else{
                textView.setText(sectionList.get(arg0).getSectionValue());
                linearLayout.setBackgroundColor(Color.WHITE);
            }

            return vi;
        }

    }

    private class SectionStructure{

        public String sectionName;
        public String sectionValue;
        public String getSectionName() {
            return sectionName;
        }
        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }
        public String getSectionValue() {
            return sectionValue;
        }
        public void setSectionValue(String sectionValue) {
            this.sectionValue = sectionValue;
        }
    }
}
*/
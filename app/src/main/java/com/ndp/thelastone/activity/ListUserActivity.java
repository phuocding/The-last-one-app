package com.ndp.thelastone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.ndp.thelastone.DbHelper;
import com.ndp.thelastone.R;

public class ListUserActivity extends AppCompatActivity {

    private DbHelper dbHelper;
    private Cursor cursor;
    private SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        dbHelper = new DbHelper(this);

        ListView listView = findViewById(R.id.lvUser);

        cursor = dbHelper.getAllUser();

        cursorAdapter = new SimpleCursorAdapter(this,R.layout.user_detail, cursor, new String[]{
                DbHelper.ID, DbHelper.NAME, DbHelper.GENDER
        }, new int[]{R.id.tvId, R.id.tvName, R.id.tvGender}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        listView.setAdapter(cursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Cursor cursor = (Cursor) cursorAdapter.getItem(i);
                int _id = cursor.getInt(cursor.getColumnIndex(DbHelper.ID));
                String name = cursor.getString(cursor.getColumnIndex(DbHelper.NAME));
                String gender = cursor.getString(cursor.getColumnIndex(DbHelper.GENDER));
                String des = cursor.getString(cursor.getColumnIndex(DbHelper.DES));

                Intent intent = new Intent(ListUserActivity.this, ActivityUpdate.class);
                intent.putExtra(DbHelper.ID, _id);
                intent.putExtra(DbHelper.NAME, name);
                intent.putExtra(DbHelper.GENDER, gender);
                intent.putExtra(DbHelper.DES, des);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();
        //reload
        cursor = dbHelper.getAllUser();
        cursorAdapter.changeCursor(cursor);
        cursorAdapter.notifyDataSetChanged();
        dbHelper.close();
    }
}

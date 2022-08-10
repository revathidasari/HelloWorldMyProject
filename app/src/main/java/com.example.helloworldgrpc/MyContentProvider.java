package com.example.helloworldgrpc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.helloworldgrpc.UsersDataProvider;

//https://www.tutlane.com/tutorial/android/android-content-providers-with-examples
//https://data-flair.training/blogs/content-provider-in-android/
//https://www.tutorialspoint.com/android/android_content_providers.htm
public class MyContentProvider extends AppCompatActivity {

    EditText enterId, enterValue;
    Button insert, delete, update, showData;
    TextView displayData;
/*
    //direct insertion to DB
    private UsersDataProvider.DatabaseHelper databaseHelper;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_content_provider);

        enterId = findViewById(R.id.textEnterId);
        enterValue = findViewById(R.id.textEnterValue);
        insert = findViewById(R.id.btnInsert);
        delete = findViewById(R.id.btnDelete);
        update = findViewById(R.id.btnUpdate);
        showData = findViewById(R.id.btnShowAll);
        displayData = findViewById(R.id.textShowAllData);
/*
        databaseHelper = new UsersDataProvider.DatabaseHelper(MyContentProvider.this);*/


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                databaseHelper.addNewData(enterValue.getText().toString());*/

                ContentValues contentValues = new ContentValues();
                contentValues.put(UsersDataProvider.name, enterValue.getText().toString());
                getContentResolver().insert(UsersDataProvider.CONTENT_URI, contentValues);
                Log.d("revathi", String.valueOf(contentValues.get("name")));
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContentResolver().delete(UsersDataProvider.CONTENT_URI,"name=?", new String[]{enterValue.getText().toString()});
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(UsersDataProvider.name, enterValue.getText().toString());
                getContentResolver().update(UsersDataProvider.CONTENT_URI,contentValues,"id=?",
                        new String[]{enterId.getText().toString()});
            }
        });

        showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor = getContentResolver().query(Uri.parse("content://com.example.helloworldgrpc.DataProvider/users"),
                        null,null,null,null);
                Log.d("revathi","cursor : "+cursor.moveToFirst());
                if (cursor.moveToFirst()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    while (!cursor.isAfterLast()) {
                        stringBuilder.append("\n"+cursor.getString(cursor.getColumnIndex("id"))
                                +"-"+cursor.getString(cursor.getColumnIndex("name")));
                        cursor.moveToNext();
                    }
                    displayData.setText(stringBuilder);
                } else {
                    displayData.setText("No records found");
                }
            }
        });

    }
}
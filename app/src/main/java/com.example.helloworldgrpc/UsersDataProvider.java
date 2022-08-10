package com.example.helloworldgrpc;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

//content://contacts_info/users
//Here the string content:// is used to represent URI is a content URI, contacts_info string is the name of the provider’s authority and users string is the table’s path.
public class UsersDataProvider extends ContentProvider {
/*public class ArrayDemo1 {
    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 4};
        int[] arr2 = {5, 6, 7, 8};
        int[] arr3 = {9, 10, 11, 12};

    int[][] arrays = {arr1, arr2, arr3};

    for(int[] array: arrays) {
        for(int n: array) {
            System.out.print(n+" ");
        }
        System.out.println();
    }
  }
}
*/
    static final String PROVIDER_NAME = "com.example.helloworldgrpc.DataProvider";
    static final String URL = "content://"+PROVIDER_NAME+"/users";
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final String id = "id";
    static final String name = "name";
    static final int uriCode = 1;
    static UriMatcher uriMatcher;
    static HashMap<String, String> values;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME,"users",uriCode);
        uriMatcher.addURI(PROVIDER_NAME,"users/*",uriCode);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        try {
            db = databaseHelper.getWritableDatabase();
            if (db != null) {
                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(TABLE_NAME);
        try {
            if (uriMatcher.match(uri) == uriCode) {
                    sqLiteQueryBuilder.setProjectionMap(values);
            }
        } catch (Exception e){
            e.printStackTrace();
            Log.d("UsersDataProvider","query "+uri);
        }
         if (TextUtils.isEmpty(sortOrder)) {
             sortOrder = id;
         }
         Cursor c = sqLiteQueryBuilder.query(db,projection,selection,selectionArgs,null,null,sortOrder);
         c.setNotificationUri(getContext().getContentResolver(),uri);
        Log.d("revathi","path : "+uri.getPath()+uri.toString()+uri.getAuthority());
         return c;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        try {
            if (uriMatcher.match(uri) == uriCode) {
                return "vnd.android.cursor.dir/users";
            }
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            Log.d("revathi","getType "+uri);
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long rowID = db.insert(TABLE_NAME,"", contentValues);
        if(rowID > 0){
            Uri insertUri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(insertUri,null);
            return insertUri;
        }
        throw  new SQLiteException("Failed to add a record into "+uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        try {
            if (uriMatcher.match(uri) == uriCode) {
                count = db.delete(TABLE_NAME, selection, selectionArgs);
            }
        } catch (Exception e){
            e.printStackTrace();
            Log.d("revathi","delete "+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        try {
            if (uriMatcher.match(uri) == uriCode) {
                    count = db.update(TABLE_NAME, contentValues, selection, selectionArgs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("revathi","update "+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }




    /*
        private static SQLiteDatabase db;*/
    private SQLiteDatabase db;

    static final String DATABASE_NAME = "EmpDB";
    static final String TABLE_NAME = "Employees";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE = "CREATE TABLE "+TABLE_NAME+" (id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            " name TEXT NOT NULL);";



/*
    public static class DatabaseHelper extends SQLiteOpenHelper {*/
    private static class DatabaseHelper extends SQLiteOpenHelper {
        /*....TABLE path is '/data/data/<pkgName>/databases/<DataBaseName>'......*/
    public DatabaseHelper(Context context) {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
           // this.db = getWritableDatabase();
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(sqLiteDatabase);
        }

        public void addNewData(String personName) {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(name,personName);
            sqLiteDatabase.insert(TABLE_NAME,null,values);
            sqLiteDatabase.close();
        }

        public void removeData(String personName) {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            sqLiteDatabase.delete(TABLE_NAME,"name=?", new String[]{personName});
            sqLiteDatabase.close();
        }

        public void updateData(String id, String personName){
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(name, personName);
            sqLiteDatabase.update(TABLE_NAME,values,id,new String[]{id});
            sqLiteDatabase.close();
        }
    }
}
